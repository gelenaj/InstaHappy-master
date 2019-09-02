package com.example.instahappy.paid;


import com.google.firebase.auth.FirebaseUser;

public class PersonalPhoto {
    public String uid;
    public FirebaseUser user;
    private String mTitle;
    private String mImageUrl;

    public PersonalPhoto() {

    }

    public PersonalPhoto(String uid, FirebaseUser user, String title, String imageUrl) {
        this.uid = uid;
        this.user=user;
        if (title.trim().equals("")) {
            title = "";
        }
        this.mTitle = title;
        this.mImageUrl = imageUrl;
    }

    public String getName() {
        return mTitle;
    }
    public void setName(String name) {
        mTitle = name;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
