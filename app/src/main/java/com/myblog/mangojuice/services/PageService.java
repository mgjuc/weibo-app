package com.myblog.mangojuice.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myblog.mangojuice.constants.SysConst;
import com.myblog.mangojuice.model.Blog;
import com.myblog.mangojuice.model.PageModel;
import com.myblog.mangojuice.model.ResModel;
import com.myblog.mangojuice.utils.RequestUtils;

import java.lang.reflect.Type;
import java.util.List;

public class PageService {

    //<T> 表示是泛型方法
    public <T> List<T> GetBlogPage(Integer pageindex, Class<T> clazz) {
        String ret = RequestUtils.getInstance().getEmpty(SysConst.PAGE + pageindex);
        if (ret == null) {
            return null;
        }
        //java 是假泛型，在运行时泛型会被擦除，需要传入clazz
        //Type collectionType = new TypeToken<ResModel<PageModel<T>>>(){}.getType();
        // getParameterized(上层，下层) 多层嵌套
        Type collectionType = TypeToken.getParameterized(ResModel.class,
                TypeToken.getParameterized(PageModel.class, clazz).getType()).getType();
        ResModel<PageModel<T>> result = new Gson().fromJson(ret, collectionType);
        List<T> data = result.getData().getList();
        Log.d("PageService", "第" + pageindex + "页加载完成！");
        return data;
    }
}
