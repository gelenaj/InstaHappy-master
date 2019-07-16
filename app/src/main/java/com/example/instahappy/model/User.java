package com.example.instahappy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user")
    @Expose
    public String id;
    public String name;

}
