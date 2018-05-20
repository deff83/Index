package com.deff83.indexAPI;
import android.app.*;
import android.content.*;

public class MyApplication extends Application {
    private static MyApplication instance;

    public static MyApplication getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized SharedPreferences getSharedPreferences() {
        return instance.getSharedPreferences(instance.getPackageName(), MODE_PRIVATE);
    }
}
