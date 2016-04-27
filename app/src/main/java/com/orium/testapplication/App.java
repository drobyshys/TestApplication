package com.orium.testapplication;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

public class App extends Application {

    private static ApplicationComponent component;

    @Singleton @Component(modules = ApiModule.class)
    public interface ApplicationComponent {
        void inject(MainActivity homeActivity);
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
