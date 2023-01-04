package com.myblog.mangojuice.services;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.myblog.mangojuice.MainApplication;
import com.myblog.mangojuice.R;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Contentlist {
    static String SERVICE_URL = MainApplication.getInstance().getString(R.string.service_url)+"contentlist/page/";
    OkHttpClient client = new OkHttpClient();
    public void Page(int page)
    {
        //OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //TODO: OkHttp需要添加hostnameVerifier验证，使信认所有发证机构
        //https://www.jianshu.com/p/8a16ac7ee444
        Request request = new Request.Builder()
                //.get()
                //.header()
                .url(SERVICE_URL+ page)
                //.hostnameVerifier(new TrustAllHostnameVerifier())
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback(){
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("获取动态失败", e.getMessage());
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException
            {
                String resp = response.body().string();
                //runOnUiThread(() -> Log.d("获取动态成功", resp));   //runOnUiThread()只能在Active里调用
                Log.e("获取动态成功", resp);
                //Toast.makeText(MainApplication.getInstance(), "resp", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
