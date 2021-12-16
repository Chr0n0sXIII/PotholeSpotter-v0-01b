package com.example.potholespotterse2;

import android.graphics.Bitmap;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class PotHole {

    private GeoPoint geoPoint;
    private String description;
    private Bitmap pothole_Image;
    private int severity;
    private String severity_Logo;
    private int repair_Status;
    private int repair_Condition;
    private int repair_Type;
    private @ServerTimestamp Date date_Reported;
    private @ServerTimestamp Date date_Repaired;


    public PotHole(){

    }

    public PotHole(GeoPoint geoPoint, String description, Bitmap pothole_Image, int severity, String severity_Logo, int repair_Status, int repair_Condition, int repair_Type, Date date_Reported, Date date_Repaired){
        this.geoPoint = geoPoint;
        this.description = description;
        this.pothole_Image = pothole_Image;
        this.severity = severity;
        this.severity_Logo = severity_Logo;
        this.repair_Status = repair_Status;
        this.repair_Condition = repair_Condition;
        this.repair_Type = repair_Type;
        this.date_Reported = date_Reported;
        this.date_Repaired = date_Repaired;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getPothole_Image() {
        return pothole_Image;
    }

    public int getSeverity() {
        return severity;
    }

    public String getSeverity_Logo() {
        return severity_Logo;
    }

    public int getRepair_Status() {
        return repair_Status;
    }

    public int getRepair_Condition() {
        return repair_Condition;
    }

    public int getRepair_Type() {
        return repair_Type;
    }

    public Date getDate_Reported() {
        return date_Reported;
    }

    public Date getDate_Repaired() {
        return date_Repaired;
    }

    public void setRepair_Status(int repair_Status) {
        this.repair_Status = repair_Status;
    }

    public void setRepair_Condition(int repair_Condition) {
        this.repair_Condition = repair_Condition;
    }

    public void setRepair_Type(int repair_Type) {
        this.repair_Type = repair_Type;
    }

    public void setPothole_Image(Bitmap pothole_Image) {
        this.pothole_Image = pothole_Image;
    }

    public void setDate_Repaired(Date date_Repaired) {
        this.date_Repaired = date_Repaired;
    }
}
