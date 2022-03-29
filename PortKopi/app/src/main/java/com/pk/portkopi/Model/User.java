package com.pk.portkopi.Model;

public class User {

    public String id, name, username, email;

    public User (){

    }

    public User(String regname, String regusername, String email, String id) {
        this.name = regname;
        this.username = regusername;
        this.email = email;
        this.id = id;
    }

    //  add
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getImageurl() {
//        return imageurl;
//    }
//
//    public void setImageurl(String imageurl) {
//        this.imageurl = imageurl;
//    }

}
