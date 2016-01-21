package com.orium.testapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SalonsResponse {

    @SerializedName("salons")
    private List<Salon> mSalons;

    public void setSalons(final List<Salon> salons) {
        mSalons = salons;
    }

    public List<Salon> getSalons() {
        return mSalons;
    }
}
