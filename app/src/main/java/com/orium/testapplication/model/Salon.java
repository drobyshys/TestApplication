package com.orium.testapplication.model;

import com.google.gson.annotations.SerializedName;

public class Salon {

    private String name;
    private String website;
    @SerializedName("profile_image_urls")
    private ProfileImageUrls image;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getImage() {
        return image.original;
    }

    static class ProfileImageUrls {
        String original;
    }
}
