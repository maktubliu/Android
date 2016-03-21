package com.example.lhh.servicetest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button start_service;
    private Button stop_service;
    private Button bindService;
    private Button ubindService;
    private Myservice.DownloadBinder downloadBinder;
    private Button send_intent_service;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (Myservice.DownloadBinder)service;//向下转型，之后就可以使用downloadBinder的方法了
            downloadBinder.startDownload();//
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start_service = (Button) findViewById(R.id.start_service);
        stop_service = (Button) findViewById(R.id.stop_service);
        bindService = (Button) findViewById(R.id.bind_service);
        ubindService = (Button) findViewById(R.id.ubind_service);
        send_intent_service = (Button) findViewById(R.id.send_intent_service);
        send_intent_service.setOnClickListener(this);
        bindService.setOnClickListener(this);
        ubindService.setOnClickListener(this);
        start_service.setOnClickListener(this);
        stop_service.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.start_service:
                Intent startIntent = new Intent(this, Myservice.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this, Myservice.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, Myservice.class);
                bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);//绑定MainActivity和Myservice
                break;
            case R.id.ubind_service:
                unbindService(serviceConnection);
                break;
            case R.id.send_intent_service:
                Log.d("MainActivity", "the threadId is" + Thread.currentThread().getId());
                Intent intentService = new Intent(this, MyIntentService.class);
                startService(intentService);
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
