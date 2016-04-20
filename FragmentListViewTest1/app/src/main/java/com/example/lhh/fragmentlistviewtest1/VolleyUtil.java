package com.example.lhh.fragmentlistviewtest1;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by LHH on 2016/4/20.
 */
public class VolleyUtil {
    private volatile static RequestQueue sRequestQueue;
    public static RequestQueue getQueue(Context context){
        if(sRequestQueue == null){
            synchronized (VolleyUtil.class){
                if(sRequestQueue == null){
                    sRequestQueue  = Volley.newRequestQueue(context.getApplicationContext());
                }
            }
        }
        return sRequestQueue;
    }
}