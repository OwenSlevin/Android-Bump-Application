package com.example.owen_.software_project;

//Setter and getter methods for our items
//class that contains an item and its ID
public class Items {

    private int id;
    private String androidID;
    private String mediaType;
    private String userName;
    private double latitude;
    private double longitude;
    private double radius;
    private String keyID;

    public Items()
    {

    }

    public Items(int id, String androidID, String mediaType, String userName, double latitude, double longitude, double radius) {
        this.id = id;
        this.androidID = androidID;
        this.mediaType = mediaType;
        this.userName = userName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.keyID = keyID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAndroidID() {
        return androidID;
    }

    public void setAndroidID(String androidID) {
        this.androidID = androidID;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

   // public String getKeyID () {return keyID; }

   // public void setKeyID (String keyID) {this.keyID = keyID;}
}