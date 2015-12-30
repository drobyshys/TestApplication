package com.orium.testapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SalonsResponse {

    @JsonProperty("salons")
    private List<Salon> mSalons;

    public void setSalons(final List<Salon> salons) {
        mSalons = salons;
    }

    public List<Salon> getSalons() {
        return mSalons;
    }
}
