package com.orium.testapplication.model;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("title")
    private String name;
    private String description;
    @SerializedName("image_preview_url")
    private String image;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }
}
