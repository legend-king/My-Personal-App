package com.example.mypersonalapp;

public class Subs {
    public Subs(String subname) {
        this.subname = subname;
    }

    private String subname;
    private String username;

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
