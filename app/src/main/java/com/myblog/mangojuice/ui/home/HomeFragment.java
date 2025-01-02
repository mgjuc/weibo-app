package com.myblog.mangojuice.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myblog.mangojuice.R;
import com.myblog.mangojuice.databinding.EmptyListLayoutBinding;
import com.myblog.mangojuice.databinding.FragmentHomeBinding;
import com.myblog.mangojuice.model.Blog;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;

    private HomeViewModel viewModel;

    private static final String TAG = "HomeFragment";

    private List<Blog> mBlogs = null;

    /*
     * Fragment核心方法：生成内部view
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");

        mBlogs = new ArrayList<Blog>();

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final RecyclerView blogLists = binding.blogLists;

        //region 添加悬浮返回顶部
        final FloatingActionButton returnTop = binding.topfab;
        blogLists.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull final RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获得recyclerView的线性布局管理器
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //获取到第一个item的显示的下标  不等于0表示第一个item处于不可见状态 说明列表没有滑动到顶部 显示回到顶部按钮
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 判断是否滚动超过一屏
                    if (firstVisibleItemPosition == 0) {
                        returnTop.hide();
                    } else {
                        //显示回到顶部按钮
                        returnTop.show();
                        returnTop.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recyclerView.scrollToPosition(0);
                                returnTop.hide();
                            }
                        });

                    }//获取RecyclerView滑动时候的状态
                }
//                else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {//拖动中
//                    returnTop.hide();
//                }
            }
        });
        //endregion

        //ListView.setEmptyView 设置为空时显示的页面
        EmptyListLayoutBinding emptyview = binding.emptyview;
//        blogLists.setEmptyView(emptyview.getRoot());

        //ListView和Adapter绑定
//        BlogAdapter adapter = new BlogAdapter(mBlogs, this.getActivity());
        BlogPageAdapter adapter = new BlogPageAdapter(new DiffUtil.ItemCallback<Blog>() {
            @Override
            public boolean areItemsTheSame(@NonNull Blog oldItem, @NonNull Blog newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Blog oldItem, @NonNull Blog newItem) {
                return oldItem.getId().equals(newItem.getId());
            }
        });
        //必须先设置LayoutManager
        blogLists.setLayoutManager(new LinearLayoutManager(this.getContext()));
        blogLists.setAdapter(adapter);

        viewModel.getPaging().observe(this, dataPagingData -> adapter.submitData(getLifecycle(), dataPagingData));

        //做数据绑定和监听
//        viewModel.getBlogs().observe(this, new Observer<List<Blog>>() {
//            @Override
//            public void onChanged(List<Blog> blogs) {
//                // update UI, data change
//                if (ObjectUtils.allNotNull(blogs)) {
//                    //mBlogs = blogs;  error =＞ 引用变了，adapter里的blogs还是空
//                    mBlogs.clear();
//                    mBlogs.addAll(blogs);
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        });

        //默认加载首页
//        viewModel.Page(0);

        //请求Blog按钮
//        binding.pageRequest.setOnClickListener(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView");
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        //请求按钮
        if (view.getId() == R.id.pageRequest) {
            viewModel.Page( 0); //把Activity传过去
        }
    }

    /**
     * 利用适配器更新内容
     */
//    public void updateAdapter(List<Blog> blogs) {
//        if (viewModel.getBlogs().getValue() != null) {
//            BlogAdapter adapter = (BlogAdapter) binding.blogLists.getAdapter();
//            //更新适配器绑定项
//            adapter.setContexts(blogs);
//        }
//
//    }
}