package com.example.potholespotterse2;
import android.location.Location;

import java.sql.Blob;

public class Pothole implements Report{


    private Blob image;
    private String UID;
    private String User;
    private Location location;
    private int Severity;
    private String Description;

    public Pothole(int Severity, Blob image, String UID, String User,String Description, Location location){
        this.image= image;
        this.UID = UID;
        this.User = User;
        this.location=location;
        this.Severity = Severity;
        this.Description= Description;
    }

    public String getDescription() {
        return Description;
    }

    public int getSeverity() {
        return Severity;
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

    public void setSeverity(int severity) {
        Severity = severity;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
