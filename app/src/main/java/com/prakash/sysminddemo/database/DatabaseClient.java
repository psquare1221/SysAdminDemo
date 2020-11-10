package com.prakash.sysminddemo.database;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.room.Room;

import com.prakash.sysminddemo.R;
import com.prakash.sysminddemo.model.SuperheroDetails;

public class DatabaseClient {

    private final Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private final AppDatabase appDatabase;


    private DatabaseClient(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "CompleteMarvelCharacterInfo").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
