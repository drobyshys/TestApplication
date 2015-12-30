package com.orium.testapplication.network;

import com.squareup.okhttp.OkHttpClient;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

public class ApiClient {

    public static final String BASE_URL = "http://staging.salony.com";

    private static TestWebApi sClient;

    public static synchronized TestWebApi getClient() {
        if (sClient == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            sClient = retrofit.create(TestWebApi.class);
        }
        return sClient;
    }
}
