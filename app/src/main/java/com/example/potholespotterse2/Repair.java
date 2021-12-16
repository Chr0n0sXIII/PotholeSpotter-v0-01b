package com.example.potholespotterse2;

import android.location.Location;

import java.sql.Blob;

public class Repair implements Report {
    private Blob image;
    private String UID;
    private String User;
    private Location location;
    private int Condition;
    private int Type;

    public Repair(int Condition, int Type , Blob image, String UID, String User, Location location){
        this.image= image;
        this.UID = UID;
        this.User = User;
        this.location=location;
        this.Condition = Condition;
        this.Type=Type;
    }

    public int getType() {
        return Type;
    }

    public int getCondition() {
        return Condition;
    }

    public Blob getImage() {
        return  this.image;
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

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setUser(String user) {
        User = user;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setCondition(int condition) {
        Condition = condition;
    }

    public void setType(int type) {
        Type = type;
    }
}
