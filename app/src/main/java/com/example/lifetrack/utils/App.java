package com.example.lifetrack.utils;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.lifetrack.room.AppDataBase;

public class App extends Application {

    public static App instance;
    private static AppDataBase database = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public  static AppDataBase initDatabase(Context context){
        if (database == null){
            database = Room.databaseBuilder(context, AppDataBase.class, "database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public static App getInstance() {
        return instance;
    }

    public static AppDataBase getDatabase() {
        return database;
    }
}
