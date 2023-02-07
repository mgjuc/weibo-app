package com.myblog.mangojuice.model;

public class BlogContent {
    private String auther;
    private String time;
    private String content;

    public BlogContent() {
    }

    public BlogContent(String auther, String time, String content) {
        this.auther = auther;
        this.time = time;
        this.content = content;
    }
    public String getAuther() {
        return auther;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
