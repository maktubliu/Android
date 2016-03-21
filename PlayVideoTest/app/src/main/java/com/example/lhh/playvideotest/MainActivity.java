package com.example.lhh.playvideotest;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button play_button;
    private Button pause_button;
    private Button replay_button;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play_button = (Button) findViewById(R.id.play);
        pause_button = (Button) findViewById(R.id.pause);
        replay_button = (Button) findViewById(R.id.replay);
        videoView = (VideoView) findViewById(R.id.video_view);
        play_button.setOnClickListener(this);
        replay_button.setOnClickListener(this);
        pause_button.setOnClickListener(this);
        replay_button.setOnClickListener(this);
        initVideoPath();
    }
    private void initVideoPath(){
        File file = new File(Environment.getExternalStorageDirectory(), "fuck.mp4");
        videoView.setVideoPath(file.getPath());
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.play:
                if(!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.pause:
                if(videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.replay:
                if(videoView.isPlaying()){
                    videoView.resume();
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(videoView != null){
            videoView.suspend();
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
