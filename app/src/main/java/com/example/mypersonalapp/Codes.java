package com.example.mypersonalapp;

public class Codes {
    public Codes(String codename, String code, String username) {
        this.codename = codename;
        this.code = code;
        this.username = username;
    }

    public Codes(String codename, String code){
        this.code = code;
        this.codename = codename;
    }

    private String codename;
    private String code;
    private String username;

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
