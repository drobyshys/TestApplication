package com.orium.testapplication.network;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Provides OkHttp interceptors for debug build.
 */
@Module
public class OkHttpInterceptorsModule {

    // Provided as separate dependency for Developer Settings to be able to change HTTP log level at runtime.
    @Provides @Singleton @NonNull
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

    @Provides @Singleton @NonNull
    public List<Interceptor> provideOkHttpInterceptors(@NonNull HttpLoggingInterceptor httpLoggingInterceptor,
                                                       HostSelectionInterceptor hostSelectionInterceptor) {
        return Arrays.asList(httpLoggingInterceptor, hostSelectionInterceptor);
    }

    @Provides @Singleton @NonNull
    public HostSelectionInterceptor provideHostSelectionInterceptor() {
        return new HostSelectionInterceptor();
    }
}
