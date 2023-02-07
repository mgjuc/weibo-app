package com.myblog.mangojuice.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.myblog.mangojuice.R;
import com.myblog.mangojuice.model.BlogContent;

import java.util.ArrayList;

/*
 * Blog ListView适配器
 * */
public class BlogAdapter extends BaseAdapter {

    private ArrayList<BlogContent> contexts;
    private Context mContext;

    public BlogAdapter(ArrayList<BlogContent> contexts, Context mContext) {
        this.contexts = contexts;
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
        TextView text = convertView.findViewById(R.id.blog);
        text.setText(contexts.get(i).getContent());
        return convertView;
    }

    public void addRange(ArrayList<BlogContent> data)
    {
        if(contexts == null)
        {
            contexts = new ArrayList<>();
        }
        contexts.addAll(data);
        notifyDataSetChanged(); //adapter内置方法
    }
}
