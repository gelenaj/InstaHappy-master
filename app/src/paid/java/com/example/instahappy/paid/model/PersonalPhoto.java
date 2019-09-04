package com.example.instahappy.paid.model;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class PersonalPhoto {
    public String uid;
    private String title;
    private String imageUrl;
    private String category;

    public PersonalPhoto() {

    }

    public PersonalPhoto(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        title = name;
       imageUrl = imageUrl;
    }

    public PersonalPhoto(String uid, String title, String imageUrl,String category) {
        this.uid = uid;
        if (title.trim().equals("")) {
            title = "";
        }
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("title", title);
        result.put("imageUrl", imageUrl);
        result.put("category", category);

        return result;
    }
}
