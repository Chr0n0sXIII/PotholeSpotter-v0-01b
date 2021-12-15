package com.example.potholespotterse2;
import android.location.Location;

import java.sql.Blob;

public class Report {

    private int type; //0 -pothole 1- repair
    private Blob image;
    private String UID;
    private String User;
    private Location location;

    public Report(int type, Blob image, String UID, String User, Location location){
        this.image= image;
        this.type = type;
        this.UID = UID;
        this.User = User;
        this.location=location;
    }

    public Blob getImage() {
        return  this.image;
    }

    public int getType() {
        return type;
    }

    public String getUID() {
        return UID;
    }

    public String getUser() {
        return User;
    }

    public Location getLocation() {
        return location;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setUser(String user) {
        User = user;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
