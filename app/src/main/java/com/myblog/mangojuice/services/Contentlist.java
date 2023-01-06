package com.myblog.mangojuice.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.myblog.mangojuice.R;
import com.myblog.mangojuice.utils.RequestUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class Contentlist {
    private static final int TIME_OUT = 3;
    private static final String SERVICE_URL = "/api/contentlist/page/";

    public void Page(Context context, int page)
    {
        RequestUtils.getInstance().getEmpty(context, SERVICE_URL + page, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("contentlist_page", e.getMessage() );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                BufferedSource source = response.body().source();   //response.body().source()只执行一次，后面就把内存释放了
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.getBuffer();
                String ret = buffer.clone().readString(Charset.forName("UTF-8"));
                Log.d("contentlist_page", ret.substring(0,100));
                Activity activity = (Activity) context;
                if(activity != null )
                {
                    activity.runOnUiThread(() -> {
                        TextView text = activity.findViewById(R.id.text_home);
                        text.setText(ret);
                    });
                }
            }
        });
    }
}
