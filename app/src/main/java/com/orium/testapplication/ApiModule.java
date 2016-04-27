package com.orium.testapplication;

import com.orium.testapplication.network.TestWebApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

@Module
public class ApiModule {

    public static final String BASE_URL = "http://staging2.salony.com";

    @Provides @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides @Singleton TestWebApi provideApiService(Retrofit retrofit) {
        return retrofit.create(TestWebApi.class);
    }

}
