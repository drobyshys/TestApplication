package com.orium.testapplication.network;

import com.orium.testapplication.model.SalonsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestWebApi {

    @GET("/v1/salons")
    Call<SalonsResponse> getSalons();

}
