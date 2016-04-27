package com.orium.testapplication;

import com.orium.testapplication.network.TestWebApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    public static final String BASE_URL = "https://raw.githubusercontent.com/orium-dev/TestApplication/dev/api/";

    @Provides @Singleton
    Retrofit provideRetrofit(final OkHttpClient client) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides @Singleton
    OkHttpClient provideOkhttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    @Provides @Singleton TestWebApi provideApiService(Retrofit retrofit) {
        return retrofit.create(TestWebApi.class);
    }

}
