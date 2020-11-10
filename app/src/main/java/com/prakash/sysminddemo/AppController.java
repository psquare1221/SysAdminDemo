package com.prakash.sysminddemo;

import android.app.Application;
import android.content.Context;

public class AppController extends Application {


    private static Context sContext;

    public static boolean isComingFromFullScreen = false;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }


    public static Context getContext() {

        return sContext.getApplicationContext();
    }

}
