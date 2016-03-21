package com.example.lhh.servicetest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by LHH on 2016/3/9.
 */
public class Myservice extends Service {
    private DownloadBinder downloadBinder = new DownloadBinder();
    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("Myservice", "DownloadBinder start");
        }
        public int getProgress(){
            Log.d("Myservice", "getprogress");
            return 0;
        }

    }
    @Override
    public IBinder onBind(Intent intent){
        return downloadBinder;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("Myservice", "service create");
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification.Builder(this).setContentTitle("This is the title")
                .setContentText("This is the text")
                .setContentIntent(pi)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setTicker("Notification come")
                .build();
        startForeground(1, notification);
    }
    @Override
    public int onStartCommand(Intent intent, int flag, int startId){
        Log.d("Myservice", "service startCommand");
        return super.onStartCommand(intent, flag, startId);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("Myservice", "service destroy");
    }

}
