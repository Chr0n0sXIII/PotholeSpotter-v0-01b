package com.example.potholespotterse2;

public class User {

    private String email;
    private String username;
    private String profilePicture;
    private String user_id;

    public User(){

    }

    public User(String email, String username, String profilePicture, String user_id){
        this.email = email;
        this.username = username;
        this.profilePicture = profilePicture;
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
