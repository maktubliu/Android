package com.example.lhh.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.widget.Toast;

/**
 * Created by LHH on 2016/2/13.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context, "recevied in MyBroadcastReceiver", Toast.LENGTH_SHORT).show();
        abortBroadcast();//阻断广播
    }
}
