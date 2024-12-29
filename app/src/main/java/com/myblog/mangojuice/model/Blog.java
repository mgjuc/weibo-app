package com.myblog.mangojuice.model;

public class Blog {
    private String id;
    private String userid;
    private String auther;
    private String time;
    private String content;

    public Blog() {
    }

    public Blog(String auther, String time, String content) {
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
