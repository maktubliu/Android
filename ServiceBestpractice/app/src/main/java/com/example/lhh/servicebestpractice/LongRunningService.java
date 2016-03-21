package com.example.lhh.servicebestpractice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;

/**
 * Created by LHH on 2016/3/10.
 */
public class LongRunningService extends Service {
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("LongRunningService", "executed at" + new Date().toString());
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int AnHour = 60 * 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + AnHour;
        Intent alarmintent = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, alarmintent, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }
}
