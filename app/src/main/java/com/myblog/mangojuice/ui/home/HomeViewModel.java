package com.myblog.mangojuice.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myblog.mangojuice.model.BlogContent;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<ArrayList<BlogContent>> blogs;

    public MutableLiveData<ArrayList<BlogContent>> getBlogs() {
        if (blogs == null) {
            blogs = new MutableLiveData<ArrayList<BlogContent>>();
        }
        return blogs;
    }
}