package com.example.lhh.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by LHH on 2016/2/13.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context, "Boot Complete", Toast.LENGTH_SHORT).show();
    }
}
