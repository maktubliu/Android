package com.example.lhh.notificationtest;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.File;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button send_notice;
    //long [] vibrates = {0, 1000, 1000, 1000};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send_notice = (Button) findViewById(R.id.send_button);
        send_notice.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.send_button:
                //Uri soundUri = Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone.ogg"));

                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Intent intent = new Intent(this, NotificationActivity.class);
                PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                Notification notification = new Notification.Builder(this)
                        //.setAutoCancel(true)
                        .setContentTitle("This is content title")
                        .setContentText("This is content text")
                        .setContentIntent(pi)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker("TickerText:" + "New message")
                        .setWhen(System.currentTimeMillis())

                        .build();
                //notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                //notification.vibrate = vibrates;
                notification.defaults = Notification.DEFAULT_ALL;
                //notification.setLatestEventInfo(this, "This is content title", "This is content text", null);
                manager.notify(1, notification);
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
