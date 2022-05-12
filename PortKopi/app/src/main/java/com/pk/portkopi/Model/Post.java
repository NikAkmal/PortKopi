package com.pk.portkopi.Model;

public class Post {
    private String postid;
    private String image;
    private String description;
    private String id;
    private String location;

    public Post(String postid, String image, String description, String id, String location) {
        this.postid = postid;
        this.image = image;
        this.description = description;
        this.id = id;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getImage() {
        return image;
    }

    public void setPostimage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setPublisher(String id) {
        this.id = id;
    }
}
