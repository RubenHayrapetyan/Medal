package com.sub_zet.medal.models;

import com.google.gson.annotations.SerializedName;

public class HelpModel {

    @SerializedName("video_tutorial")
    private String videoLink;
    @SerializedName("description")
    private String description;

    public HelpModel(String videoLink, String description) {
        this.videoLink = videoLink;
        this.description = description;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
