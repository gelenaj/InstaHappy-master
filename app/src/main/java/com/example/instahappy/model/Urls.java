package com.example.instahappy.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Urls{
    @SerializedName("raw")
    @Expose
    private String raw;

    @SerializedName("full")
    @Expose
    private String full;

    @SerializedName("regular")
    @Expose
    private String regular;

    @SerializedName("small")
    @Expose
    private String small;

    @SerializedName("thumb")
    @Expose
    private String thumb;

    @SerializedName("custom")
    @Expose
    private String custom;

    public String getRaw() {
        return raw;
    }
    public void setRaw(String raw) {
        this.raw = raw;
    }
    public String getFull() {
        return full;
    }
    public void setFull(String full) {
        this.full = full;
    }
    public String getRegular() {
        return regular;
    }
    public void setRegular(String regular) {
        this.regular = regular;
    }
    public String getSmall() {
        return small;
    }
    public void setSmall(String small) {
        this.small = small;
    }
    public String getThumb() {
        return thumb;
    }
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    public String getCustom() {
        return custom;
    }
    public void setCustom(String custom) {
        this.custom = custom;
    }
}
