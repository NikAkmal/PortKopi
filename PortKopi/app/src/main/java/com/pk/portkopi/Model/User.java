package com.pk.portkopi.Model;

public class User {

    public String id, name, username, email;

    public User (){

    }

    public User (String name, String username, String email){
        this.name = name;
        this.username = username;
        this.email = email;
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

}
