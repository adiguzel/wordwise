package com.wordwise;

import android.app.Application;
import android.content.Context;

public class WordwiseApplication extends Application{

    private static Context context;

    public void onCreate(){
        super.onCreate();
        WordwiseApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}