package com.orium.testapplication;

import com.orium.testapplication.network.TestWebApi;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

@Module(
        injects = {
                MainActivity.class
        }
)
public class ApiModule {

    public static final String BASE_URL = "http://staging.salony.com";

    @Provides @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides @Singleton TestWebApi provideApiService(Retrofit retrofit) {
        return retrofit.create(TestWebApi.class);
    }

}
