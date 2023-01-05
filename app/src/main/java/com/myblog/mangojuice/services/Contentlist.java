package com.myblog.mangojuice.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.myblog.mangojuice.MyApplication;
import com.myblog.mangojuice.R;
import com.myblog.mangojuice.SysConst;
import com.myblog.mangojuice.utils.RequestUtils;
import com.myblog.mangojuice.utils.RxUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Contentlist {
    private static final int TIME_OUT = 3;
    String SERVICE_URL = "/api/contentlist/page/";

    public void Page(Context context, int page)
    {
        RequestUtils.getInstance().getEmpty(context, SERVICE_URL + page, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("request_failed", e.getMessage() );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }
}
