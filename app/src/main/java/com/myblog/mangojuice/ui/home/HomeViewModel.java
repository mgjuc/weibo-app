package com.myblog.mangojuice.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myblog.mangojuice.model.BlogContent;

import java.util.ArrayList;
import java.util.Date;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<ArrayList<BlogContent>> mContents;
    private final ArrayList<BlogContent> mContents1;

    public ArrayList<BlogContent> getmContents1() {
        return mContents1;
    }

    public HomeViewModel() {
        mContents = new MutableLiveData<ArrayList<BlogContent>>();
        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
        mContents1 = new ArrayList<BlogContent>( );
        mContents1.add(new BlogContent("jack",new Date().toString(), "helloworld"));
        mContents1.add(new BlogContent("jack",new Date().toString(), "helloworld!!"));
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<BlogContent>> getmContents() {
        return mContents;
    }
}