package com.example.instahappy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAltDescription() {
        return altDescription;
    }

    public void setAltDescription(String altDescription) {
        this.altDescription = altDescription;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls mUrls) {
        this.urls =mUrls;
    }



    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("created_at")
    @Expose
    public String created_at;

    @SerializedName("width")
    @Expose
    private int width;

    @SerializedName("height")
    @Expose
    private int height;

    @SerializedName("color")
    @Expose
    private String color;

    @SerializedName("alt_description")
    @Expose
    private String altDescription;

    @SerializedName("urls")
    @Expose
    private Urls urls;

    public List<Photo> getPhotosList() {
        return photosList;
    }

    public void setPhotosList(List<Photo> photosList) {
        this.photosList = photosList;
    }

    private List<Photo>photosList;

    @SerializedName("user")
    @Expose
    public User user;

}
