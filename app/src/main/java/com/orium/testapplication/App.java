package com.orium.testapplication;

import android.app.Application;
import android.support.annotation.NonNull;

import com.orium.testapplication.network.ApiModule;
import com.orium.testapplication.network.HostSelectionInterceptor;
import com.orium.testapplication.network.OkHttpInterceptorsModule;
import com.orium.testapplication.ui.items.ItemsActivity;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

public class App extends Application {

    public static final String BASE_URL = "https://raw.githubusercontent.com/orium-dev/TestApplication/dev/api/";

    private static ApplicationComponent component;

    @Singleton @Component(modules = {
            ApiModule.class, OkHttpInterceptorsModule.class
    })
    public interface ApplicationComponent {
        void inject(ItemsActivity homeActivity);

        @NonNull
        OkHttpClient getOkHttpClient();

        HostSelectionInterceptor getHostSelectionInterceptor();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApp_ApplicationComponent.builder()
                .apiModule(new ApiModule())
                .build();
    }

    public static ApplicationComponent getComponent() {
        return component;
    }
}
