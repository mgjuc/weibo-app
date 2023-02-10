package com.myblog.mangojuice.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myblog.mangojuice.R;
import com.myblog.mangojuice.model.BlogContent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Blog ListView适配器
 * */
public class BlogAdapter extends BaseAdapter {

    private ArrayList<BlogContent> contexts;
    private Context mContext;

    public BlogAdapter(ArrayList<BlogContent> blogs, Context mContext) {
        this.contexts = blogs;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return contexts == null ? 0 : contexts.size();
    }

    @Override
    public Object getItem(int i) {
        return contexts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.blog_content,viewGroup,false);
        TextView data = convertView.findViewById(R.id.blog_data);
        TextView auther = convertView.findViewById(R.id.blog_auther);
        TextView date = convertView.findViewById(R.id.blog_date);
        data.setText(contexts.get(i).getContent());
        auther.setText(contexts.get(i).getAuther());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date sourcetime = formatter.parse(contexts.get(i).getTime());
            formatter.applyPattern("yyyy年MM月dd日 HH:mm:ss");
            date.setText(formatter.format(sourcetime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    /**
    * 更新Adapter内容
    */
    public void setContexts(ArrayList<BlogContent> data)
    {
        if(contexts == null)
        {
            contexts = new ArrayList<>();
        }
        this.contexts = data;
        notifyDataSetChanged(); //adapter内置方法
    }
}
