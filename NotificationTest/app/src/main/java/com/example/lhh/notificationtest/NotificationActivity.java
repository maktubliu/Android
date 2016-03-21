package com.example.lhh.notificationtest;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

/**
 * Created by LHH on 2016/3/7.
 */
public class NotificationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);
    }
}
