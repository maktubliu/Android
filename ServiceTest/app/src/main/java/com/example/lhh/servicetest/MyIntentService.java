package com.example.lhh.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by LHH on 2016/3/9.
 */
public class MyIntentService extends IntentService {
    public MyIntentService(){
        super("MayIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent){
        Log.d("MyIntentService", "thread Id is" + Thread.currentThread().getId());
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyIntentSerivice", "Destroy executed");
    }
}
