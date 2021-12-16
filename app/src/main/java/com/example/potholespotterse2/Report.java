package com.example.potholespotterse2;

import android.location.Location;

import java.sql.Blob;

public interface Report {
    Blob image = null;
    String UID = null;
    String User = null;
    Location location = null;
}
