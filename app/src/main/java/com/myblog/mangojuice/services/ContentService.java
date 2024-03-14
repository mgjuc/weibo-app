package com.myblog.mangojuice.services;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.fragment.NavHostFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myblog.mangojuice.R;
import com.myblog.mangojuice.model.BlogContent;
import com.myblog.mangojuice.model.PageModel;
import com.myblog.mangojuice.model.ResModel;
import com.myblog.mangojuice.ui.home.HomeFragment;
import com.myblog.mangojuice.ui.home.HomeViewModel;
import com.myblog.mangojuice.utils.RequestUtils;

import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class ContentService {
    private HomeViewModel model;
    private static final String SERVICE_URL = "/api/contentlist/page/";

    public ContentService(ViewModel model) {
        this.model = (HomeViewModel) model;
    }

    //请求内容页
    public void Page(Context context, int page) {
        RequestUtils.getInstance().getEmpty(context, SERVICE_URL + page, new Callback() {
            //失败回调
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("contentlist_page", e.getMessage());
            }
            //成功回调
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                //region 获取Blog的Json值
                BufferedSource source = response.body().source();   //response.body().source()只执行一次，后面就把内存释放了
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.getBuffer();
                String ret = buffer.clone().readString(Charset.forName("UTF-8"));
                Log.e("contentlist_page", ret.substring(0, 100));
                Type collectionType = new TypeToken<ResModel<PageModel<BlogContent>>>() {}.getType();
                ResModel<PageModel<BlogContent>> result = new Gson().fromJson(ret, collectionType);
                //endregion
                List<BlogContent> blogs = result.getData().getList();
                if(!ObjectUtils.allNotNull(blogs)) return;

                //fixme 丑陋的代码
                FragmentActivity activity = (FragmentActivity) context;

                //这个model2里没有内容，不是单例
//                HomeViewModel model2 = new ViewModelProvider((ViewModelStoreOwner) context).get(HomeViewModel.class);
//                if (activity != null && activity instanceof FragmentActivity) {
//                    activity.runOnUiThread(() -> {
//                        Toast.makeText(context, model2 == null ? "null" : model2.getBlogs().getValue().get(0).getContent(), Toast.LENGTH_LONG).show();
//                     });
//                }

                //更新页面
                if (model != null) {
                    activity.runOnUiThread(() -> {
                        model.getBlogs().setValue(blogs);
                        NavHostFragment fragment = (NavHostFragment) activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
//                        Log.e("contentlist_page", fragment == null ? "null" : "not null");
                        HomeFragment homeFragment = (HomeFragment) fragment.getChildFragmentManager().getFragments().get(0);    //List<Fragment>顺序是加入的顺序
                        if (homeFragment != null) {
                            homeFragment.updateAdapter(blogs);  //更新页面
//                            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "Filed", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
