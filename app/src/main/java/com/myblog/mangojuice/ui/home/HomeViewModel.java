package com.myblog.mangojuice.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myblog.mangojuice.model.BlogContent;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<BlogContent>> blogs;

    public MutableLiveData<List<BlogContent>> getBlogs() {
        if (blogs == null) {
            blogs = new MutableLiveData<List<BlogContent>>();
        }
        return blogs;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}