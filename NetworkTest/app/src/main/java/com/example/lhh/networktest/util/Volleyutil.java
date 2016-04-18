package com.example.lhh.networktest.util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by LHH on 2016/4/18.
 */
public class Volleyutil {
    private volatile static RequestQueue sRequestQueue;
    public static RequestQueue getQueue(Context context){
        if(sRequestQueue == null){
            synchronized (Volleyutil.class){
                if(sRequestQueue == null){
                    sRequestQueue  = Volley.newRequestQueue(context.getApplicationContext());
                }
            }
        }
        return sRequestQueue;
    }
}
