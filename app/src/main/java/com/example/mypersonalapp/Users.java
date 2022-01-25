package com.example.mypersonalapp;

public class Users {
    private int status;
    private String username;

    public Users(int status, String username) {
        this.status = status;
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
