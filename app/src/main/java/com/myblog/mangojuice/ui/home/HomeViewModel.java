package com.myblog.mangojuice.ui.home;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myblog.mangojuice.constants.SysConst;
import com.myblog.mangojuice.model.Blog;
import com.myblog.mangojuice.model.PageModel;
import com.myblog.mangojuice.model.ResModel;
import com.myblog.mangojuice.services.PageService;
import com.myblog.mangojuice.services.PageDataSource;
import com.myblog.mangojuice.utils.RequestUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.Executors;

import kotlinx.coroutines.CoroutineScope;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Blog>> blogs;

    private static final String TAG = "HomeViewModel";

    //初始化配置,可以定义最大加载的数据量
    PagingConfig pagingConfig=new PagingConfig(15,15,false,15);

    public LiveData<PagingData<Blog>> getPaging(){
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
        PageService service = new PageService();
        PageDataSource dataSource = new PageDataSource<Blog>(service, executorService);
        //构造函数根据自己的需要来调整
        Pager<Integer, Blog> pager = new Pager<Integer, Blog>(pagingConfig, () -> dataSource);

        //CoroutineScope kotlin协程作用域
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }

    public MutableLiveData<List<Blog>> getBlogs() {
        if (blogs == null) {
            blogs = new MutableLiveData<List<Blog>>();
        }
        return blogs;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public void Page(Integer pageindex) {
        RequestUtils.getInstance().getEmpty(SysConst.PAGE + pageindex, new Callback() {
            //失败回调
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, e.getMessage());
            }

            //成功回调
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                //region 获取Blog的Json值
                BufferedSource source = response.body().source();   //response.body().source()只执行一次，后面就把内存释放了
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.getBuffer();
                String ret = buffer.clone().readString(Charset.forName("UTF-8"));
                Log.i(TAG, ret.substring(0, 100));
                Type collectionType = new TypeToken<ResModel<PageModel<Blog>>>() {
                }.getType();
                ResModel<PageModel<Blog>> result = new Gson().fromJson(ret, collectionType);
                //endregion
                List<Blog> blogs = result.getData().getList();

                getBlogs().postValue(blogs);
            }
        });
    }
}