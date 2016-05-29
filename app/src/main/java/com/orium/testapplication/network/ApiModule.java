package com.orium.testapplication.network;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
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
    OkHttpClient provideOkhttpClient(List<Interceptor> interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor i : interceptors) {
            builder.addInterceptor(i);
        }
        return builder.build();
    }

    @Provides @Singleton TestWebApi provideApiService(Retrofit retrofit) {
        return retrofit.create(TestWebApi.class);
    }

}
