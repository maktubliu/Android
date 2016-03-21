package com.example.lhh.servicebestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by LHH on 2016/3/10.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Intent i = new Intent(context, LongRunningService.class);
        context.startService(i);
    }
}
