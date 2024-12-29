package com.myblog.mangojuice.services;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.ListenableFuturePagingSource;
import androidx.paging.PagingState;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PageDataSource<T> extends ListenableFuturePagingSource<Integer, T> {
    @NonNull
    private BlogService<T> service;
    @NonNull
    private String mQuery;
    @NonNull
    private ExecutorService pool = Executors.newCachedThreadPool();

    private ListeningExecutorService listeningExecutor;
    public PageDataSource(
            @NonNull BlogService<T> service, @NonNull String query, @Nullable ListeningExecutorService listeningExecutor) {
        this.service = service;
        mQuery = query;
        this.listeningExecutor = listeningExecutor;
    }

    @NotNull
    @Override
    public ListenableFuture<LoadResult<Integer, T>> loadFuture(@NotNull LoadParams<Integer> params) {
        // Start refresh at page 0 if undefined.
        Integer nextPageNumber = params.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 0;
        }

        Integer finalNextPageNumber = nextPageNumber;

        ListenableFuture<LoadResult<Integer, T>> pageFuture = Futures.transform(listeningExecutor.submit(() ->
                                service.GetBlogPage(finalNextPageNumber)
                        ),
//                        /*(Function<List<T>, LoadResult<Integer, T>>) */input -> {
//                            input =  input == null ? new ArrayList<>() : input;
//                            return new LoadResult.Page<>(input, finalNextPageNumber == 0 ? 0 : finalNextPageNumber - 1,
//                            input.isEmpty() ? null : finalNextPageNumber + 1);
//                        },
                        input -> this.toLoadResult(input, finalNextPageNumber),
                pool);

        ListenableFuture<LoadResult<Integer, T>> partialLoadResultFuture = Futures.catching(pageFuture, Exception.class,
                        LoadResult.Error::new, pool);

        return Futures.catching(partialLoadResultFuture,
                IOException.class, LoadResult.Error::new, pool);
    }

    /**
     * 加载
     * @param input
     * @param currentPage
     * @return
     */
    private LoadResult<Integer, T> toLoadResult(List<T> input, Integer currentPage) {
        return new LoadResult.Page<>(input,
                null, // Only paging forward.
                currentPage + 1,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NotNull PagingState<Integer, T> state) {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        Integer anchorPosition = state.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, T> anchorPage = state.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }

        return null;
    }
}
