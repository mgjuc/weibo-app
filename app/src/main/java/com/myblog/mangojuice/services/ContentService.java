package com.myblog.mangojuice.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myblog.mangojuice.MainActivity;
import com.myblog.mangojuice.R;
import com.myblog.mangojuice.model.BlogContent;
import com.myblog.mangojuice.ui.home.HomeFragment;
import com.myblog.mangojuice.ui.home.HomeViewModel;
import com.myblog.mangojuice.utils.RequestUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class ContentService {
    private static final String SERVICE_URL = "/api/contentlist/page/";
    public void Page(Context context, int page) {
        RequestUtils.getInstance().getEmpty(context, SERVICE_URL + page, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("contentlist_page", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                BufferedSource source = response.body().source();   //response.body().source()只执行一次，后面就把内存释放了
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.getBuffer();
                String ret = buffer.clone().readString(Charset.forName("UTF-8"));
                Log.d("contentlist_page", ret.substring(0, 100));
                Activity activity = (Activity) context;
                HomeViewModel model = new ViewModelProvider((ViewModelStoreOwner) activity).get(HomeViewModel.class);
//                if (activity != null && activity instanceof MainActivity) {
                    activity.runOnUiThread(() -> {
                        Type collectionType = new TypeToken<ArrayList<BlogContent>>(){}.getType();
                        ArrayList<BlogContent> blogs = model.getBlogs().getValue();
                        blogs = new Gson().fromJson(ret, collectionType);
                    });
//                }

            }
        });
    }
}
