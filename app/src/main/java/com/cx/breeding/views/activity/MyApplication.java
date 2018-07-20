package com.cx.breeding.views.activity;

import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * Created by asus on 2017/8/8.
 */

public class MyApplication extends LitePalApplication {

    private static Context mContext;

    @Override
    public void onCreate(){
        super.onCreate();
        mContext = getApplicationContext();

    }

    public static Context getGlobalContext(){
        return mContext;
    }
}
