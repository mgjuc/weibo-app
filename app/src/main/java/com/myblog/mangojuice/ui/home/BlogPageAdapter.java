package com.myblog.mangojuice.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.myblog.mangojuice.R;
import com.myblog.mangojuice.model.Blog;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Blog Paging3 适配器，配合 RecyclerView
 */
public class BlogPageAdapter extends PagingDataAdapter<Blog, BlogPageAdapter.Holder> {


    public BlogPageAdapter(@NotNull DiffUtil.ItemCallback<Blog> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_content, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Blog data = getItem(position);
        holder.auther.setText(data.getAuther());
        holder.content.setText(data.getContent());
        holder.date.setText(FormatTimeFromUTC(data.getTime()));
    }

    private String FormatTimeFromUTC(String utcTime){
        String date = "";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date sourcetime = formatter.parse(utcTime);
            formatter.applyPattern("yyyy年MM月dd日 HH:mm:ss");
            date = formatter.format(sourcetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 内部类实现 viewHolder
     */
    class Holder extends RecyclerView.ViewHolder {

        TextView date, content, auther;

        public Holder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.blog_date);
            content = itemView.findViewById(R.id.blog_data);
            auther = itemView.findViewById(R.id.blog_auther);
        }
    }
}
