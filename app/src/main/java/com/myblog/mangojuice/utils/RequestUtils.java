package com.myblog.mangojuice.utils;


import android.content.Context;

import com.myblog.mangojuice.SysConst;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RequestUtils {

    public static final MediaType FORM_CONTENT_TYPE = MediaType.parse("application/json;charset=utf-8");

    private static final byte[] LOCKER = new byte[0];
    private static final int TIME_OUT = 3;

    private static RequestUtils mInstance;

    /**
     * post请求不传递参数
     *
     * @param url      请求地址
     * @param callback 执行函数
     */
    public void postEmpty(Context context, String url, final Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(splicingUrl(url)).post(okhttp3.internal.Util.EMPTY_REQUEST)
                .addHeader("Cookie", getCookie(context))
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * get请求不传递参数
     *
     * @param url      请求地址
     * @param callback 执行函数
     */
    public void getEmpty(Context context, String url, final Callback callback) {
        OkHttpClient client = GetVerifiedClient();
        Request request = new Request.Builder()
                .url(splicingUrl(url)).get()
                .addHeader("Cookie", getCookie(context))
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 发送json数据
     *
     * @param url       请求地址
     * @param paramJson 发送json数据
     * @param callback  执行函数
     */
    public void postJson(String url, JSONObject paramJson, final Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(new Gson().toJson(paramJson), FORM_CONTENT_TYPE);
        Request request = new Request.Builder()
                .url(splicingUrl(url)).post(requestBody)
                .cacheControl(CacheControl.FORCE_NETWORK)   //FORCE_NETWORK == 不使用请求缓存
                .build();
        client.newCall(request).enqueue(callback);
    }


    /**
     * 发送表单数据
     *
     * @param url       请求地址
     * @param paramJson 请求参数
     * @param callback  执行函数
     */
    public void postForm(Context context, String url, JSONObject paramJson, final Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(new Gson().toJson(paramJson), FORM_CONTENT_TYPE);
        Request request = new Request.Builder()
                .url(splicingUrl(url)).post(requestBody)
                .addHeader("Cookie", getCookie(context))
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        client.newCall(request).enqueue(callback);
    }


    /**
     * 发送表单数据
     *
     * @param url       请求地址
     * @param paramJson 请求参数
     * @param callback  执行函数
     */
    public void postFormByLogin(Context context, String url, JSONObject paramJson, final Callback callback) {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(new Gson().toJson(paramJson), FORM_CONTENT_TYPE);
        Request request = new Request.Builder()
                .url(splicingUrl(url)).post(requestBody)
                .cacheControl(CacheControl.FORCE_NETWORK)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 单例模式获取RequestUtils
     *
     * @return RequestUtils
     */
    public static RequestUtils getInstance() {
        if (mInstance == null) {
            synchronized (LOCKER) {
                if (mInstance == null) {
                    mInstance = new RequestUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 组合请求地址
     *
     * @param url 映射地址
     * @return 请求地址
     */
    private String splicingUrl(String url) {
        return SysConst.REMOTE_URL + url;
    }

    /*
    * 配置okhttp的证书认证，信任所有
    * */
    private OkHttpClient GetVerifiedClient()
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        HttpLoggingInterceptor loggingInterceptor =
//                new HttpLoggingInterceptor((message) -> Log.i("okhttp", message));
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = builder
                //.addInterceptor(loggingInterceptor)
                //.addInterceptor(new DefaultHeaderInterceptor())
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .sslSocketFactory(RxUtils.createSSLSocketFactory())
                .hostnameVerifier(new RxUtils.TrustAllHostnameVerifier())
                .retryOnConnectionFailure(true).build();
        return client;
    }

    /**
     * 获取登录信息
     *
     * @param context 上下文对象
     * @return String
     */
    public String getCookie(Context context) {
        String sessionId = SharedPrefUtils.getInstance().getStrBykey(CacheKeys.LOGIN_SESSION_Id, StringUtils.EMPTY);
        if (sessionId.equals(StringUtils.EMPTY)) {
            return StringUtils.EMPTY;
        } else {
            return sessionId;
        }
    }

}
