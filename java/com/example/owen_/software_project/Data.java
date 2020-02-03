package com.example.owen_.software_project;
//Getter and setter method for variables that will be displayed with recycler view
public class Data {
    String username;
    String mediatype;

    public Data(){}

    public Data(String username, String mediatype) {
        this.username = username;
        this.mediatype = mediatype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }
}
