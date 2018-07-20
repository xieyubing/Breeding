package com.cx.breeding.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cx.breeding.views.activity.MyApplication;


/**
 * Created by asus on 2017/8/8.
 */

public class SPHelper {
    private SharedPreferences config = MyApplication.getGlobalContext().getSharedPreferences("config", Context.MODE_PRIVATE);
    private SharedPreferences.Editor configEditor = config.edit();
    private static SPHelper instance;

    private SPHelper(){

    }

    public static SPHelper getInstance(){
        if(instance == null){
            synchronized(SPHelper.class){
                if(instance == null){
                    instance = new SPHelper();
                }
            }
        }
        return instance;
    }

    public int getFontSize(){
        return config.getInt("font_size",45);
    }
    public void setFontSize(int size){
        configEditor.putInt("font_size",size).apply();
    }
    public void setNightMode(boolean which){
        configEditor.putBoolean("night_mode",which).apply();
    }
    public boolean isNightMode(){
        Log.i("真",config.getBoolean("night_mode",false)+"");
        return config.getBoolean("night_mode",false);
    }


}
