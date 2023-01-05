package com.myblog.mangojuice.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myblog.mangojuice.databinding.FragmentHomeBinding;
import com.myblog.mangojuice.services.Contentlist;
import com.myblog.mangojuice.utils.RequestUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSource;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //page
        binding.page.setOnClickListener(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
//        Contentlist content = new Contentlist();
//        content.Page(0);
        RequestUtils.getInstance().getEmpty(this.getContext(), "/api/contentlist/page/0", new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("RequestUtils failed", e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                BufferedSource source = response.body().source();   //response.body().source()只执行一次，后面就把内存释放了
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.getBuffer();
                String ret = buffer.clone().readString(Charset.forName("UTF-8"));
                Log.d("RequestUtils success", ret);
                getActivity().runOnUiThread(() -> binding.textHome.setText(ret.substring(0, 100)));
            }
        });
    }
}