package com.example.mypersonalapp;

public class Codes {
    public Codes(String codename, String code, String subname) {
        this.codename = codename;
        this.code = code;
        this.subname = subname;
    }

    public Codes(String codename, String code){
        this.code = code;
        this.codename = codename;
    }

    private String codename;
    private String code;

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    private String subname;

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

}
