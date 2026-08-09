package com.example.test;

import android.app.Application;

import androidx.room.Room;

public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance() {
        return instance;
    }

    private AppDatabase appDatabase;

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        appDatabase = Room.databaseBuilder(
                this,
                AppDatabase.class,
                AppDatabase.DATABASE_NAME


        ).build();
    }

}
