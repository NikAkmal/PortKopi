package com.pk.portkopi.Model;

public class Notification {
    private String userid;
    private String text;
    private String type;
    private String postid;
    private boolean ispost;

    public Notification(String userid, String text, String type, String postid, boolean ispost) {
        this.userid = userid;
        this.text = text;
        this.type = type;
        this.postid = postid;
        this.ispost = ispost;

    }

    public Notification() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public  String getType() {return type;}

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIspost() {
        return ispost;
    }

    public void setIspost(boolean ispost) {
        this.ispost = ispost;
    }
}
