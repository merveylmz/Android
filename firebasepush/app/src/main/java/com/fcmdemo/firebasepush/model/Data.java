package com.fcmdemo.firebasepush.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ss on 22.6.2017.
 */

public class Data {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("pageActivity")
    @Expose
    private String pageActivity;
    @SerializedName("is_background")
    @Expose
    private Boolean isBackground;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("payload")
    @Expose
    private String payload;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPageActivity() {
        return pageActivity;
    }

    public void setPageActivity(String pageActivity) {
        this.pageActivity = pageActivity;
    }

    public Boolean getIsBackground() {
        return isBackground;
    }

    public void setIsBackground(Boolean isBackground) {
        this.isBackground = isBackground;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
