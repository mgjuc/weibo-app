package com.myblog.mangojuice.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myblog.mangojuice.R;
import com.myblog.mangojuice.databinding.EmptyListLayoutBinding;
import com.myblog.mangojuice.databinding.FragmentHomeBinding;
import com.myblog.mangojuice.services.Contentlist;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;

    /*
    * Fragment核心方法：生成内部view
    */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final ListView blogLists = binding.blogLists;

//        EmptyListLayoutBinding emptylayout = EmptyListLayoutBinding.inflate(inflater, container, false);
//        blogLists.setEmptyView(emptylayout.getRoot());

        BlogAdapter adapter = new BlogAdapter(homeViewModel.getmContents1(), this.getActivity());
        //homeViewModel.getmContents().observe(getViewLifecycleOwner(), updateAdapter);
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
        if(view.getId() == R.id.pageRequest)
        {
            Contentlist content = new Contentlist();
            content.Page(getActivity(), 0); //把Activity传过去
        }
    }

    public void updateAdapter()
    {

    }
}