package com.orium.testapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Salon {

    @JsonProperty("name")
    private String mName;
    @JsonProperty("website")
    private String mWebsite;
    private String image;

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        this.mName = name;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(final String website) {
        this.mWebsite = website;
    }

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }
}
