package com.example.instahappy.paid.model;


import com.google.firebase.auth.FirebaseUser;

public class PersonalPhoto {
    public String uid;
    public FirebaseUser user;
    private String mTitle;
    private String mImageUrl;
    Category category;

    public PersonalPhoto() {

    }

    public PersonalPhoto(Category cat,String uid, FirebaseUser user, String title, String imageUrl) {
        this.category = cat;
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
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
