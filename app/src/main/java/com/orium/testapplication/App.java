package com.orium.testapplication;

import android.app.Application;

import dagger.ObjectGraph;

public class App extends Application {

    private static ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = ObjectGraph.create(new ApiModule());
    }

    public static ObjectGraph getObjectGraph() {
        return objectGraph;
    }
}
