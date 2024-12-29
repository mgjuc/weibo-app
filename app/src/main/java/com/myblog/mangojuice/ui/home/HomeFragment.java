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
        final ListView blogLists = binding.blogLists;

        //ListView.setEmptyView 设置为空时显示的页面
        EmptyListLayoutBinding emptyview = binding.emptyview;
        blogLists.setEmptyView(emptyview.getRoot());

        //ListView和Adapter绑定
        BlogAdapter adapter = new BlogAdapter(mBlogs, this.getActivity());
        blogLists.setAdapter(adapter);
//        updateAdapter(viewModel.getBlogs().getValue());

        //做数据绑定和监听
        viewModel.getBlogs().observe(this, new Observer<List<Blog>>() {
            @Override
            public void onChanged(List<Blog> blogs) {
                // update UI, data change
                if (ObjectUtils.allNotNull(blogs)) {
                    //mBlogs = blogs;  error =＞ 引用变了，adapter里的blogs还是空
                    mBlogs.clear();
                    mBlogs.addAll(blogs);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //默认加载首页
        viewModel.Page(0);

        //请求Blog按钮
        binding.pageRequest.setOnClickListener(this);

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
    public void updateAdapter(List<Blog> blogs) {
        if (viewModel.getBlogs().getValue() != null) {
            BlogAdapter adapter = (BlogAdapter) binding.blogLists.getAdapter();
            //更新适配器绑定项
            adapter.setContexts(blogs);
        }

    }
}