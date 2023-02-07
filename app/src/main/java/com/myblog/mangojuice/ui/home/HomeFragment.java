package com.myblog.mangojuice.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myblog.mangojuice.R;
import com.myblog.mangojuice.databinding.EmptyListLayoutBinding;
import com.myblog.mangojuice.databinding.FragmentHomeBinding;
import com.myblog.mangojuice.model.BlogContent;
import com.myblog.mangojuice.services.ContentService;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;

    private HomeViewModel model;

    /*
     * Fragment核心方法：生成内部view
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ListView blogLists = binding.blogLists;

        //ListView.setEmptyView 设置为空时显示的页面
        EmptyListLayoutBinding emptyview = binding.emptyview;
        blogLists.setEmptyView(emptyview.getRoot());

        //ListView和Adapter绑定
        BlogAdapter adapter = new BlogAdapter(model.getBlogs().getValue(), this.getActivity());
        blogLists.setAdapter(adapter);


        //请求Blog按钮
        binding.pageRequest.setOnClickListener(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.pageRequest) {
            ContentService content = new ContentService(model);
            content.Page(this.getActivity(), 0); //把Activity传过去
        }
    }

    public void updateAdapter() {
        if(model.getBlogs().getValue() != null)
        {
            BlogAdapter  adapter = (BlogAdapter) binding.blogLists.getAdapter();
            adapter.addRange(model.getBlogs().getValue());
        }

    }
}