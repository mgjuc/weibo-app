package com.myblog.mangojuice.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myblog.mangojuice.constants.SysConst;
import com.myblog.mangojuice.model.Blog;
import com.myblog.mangojuice.model.PageModel;
import com.myblog.mangojuice.model.ResModel;
import com.myblog.mangojuice.utils.RequestUtils;

import java.lang.reflect.Type;
import java.util.List;

public class BlogService<T> {

    public List<T> GetBlogPage(Integer pageindex){
        String ret = RequestUtils.getInstance().getEmpty(SysConst.PAGE + pageindex);
        if(ret == null){
            return null;
        }
        Type collectionType = new TypeToken<ResModel<PageModel<T>>>() {}.getType();
        ResModel<PageModel<T>> result = new Gson().fromJson(ret, collectionType);
        return result.getData().getList();
    }
}
