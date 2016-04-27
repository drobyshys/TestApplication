package com.orium.testapplication.network;

import com.orium.testapplication.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestWebApi {

    @GET("items")
    Call<List<Item>> getItems();

}
