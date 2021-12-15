package com.example.potholespotterse2;

public class User {
    private String Name;
    private String UID;
    private String Password;

    public  User ( String Name, String UID, String Password){
        this.Name = Name;
        this.UID = UID;
        this.Password = Password;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUID() {
        return UID;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }
}
