package com.example.lhh.fragmentlistview;

import android.app.Application;
import android.content.Context;

/**
 * Created by LHH on 2016/4/20.
 */
public class MyApplication extends Application {
    private static Context sContext;
    @Override
    public void onCreate(){
        sContext = getApplicationContext();
    }
    public static Context getContext(){
        return sContext;
    }
}
