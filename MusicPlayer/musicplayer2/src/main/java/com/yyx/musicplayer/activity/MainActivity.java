package com.yyx.musicplayer.activity;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yyx.musicplayer.R;
import com.yyx.musicplayer.entity.Music;
import com.yyx.musicplayer.server.MusicServer;
import com.yyx.musicplayer.util.Contants;
import com.yyx.musicplayer.util.Utils;
import com.yyx.musicplayer.view.XCRoundImageView;

public class MainActivity extends BaseActivty implements View.OnClickListener {

    private XCRoundImageView mMusicImage;   //图片
    private TextView mPlayedTime, mTotalTime;   //已播放的时间和音乐总时间
    private ImageView mPlayMode;    //播放模式
    private ImageView mMusicNext, mMusicPrev;   //上一首，下一首按钮
    private ImageView mMusicPlay;   //播放暂停按钮
    private SeekBar mMusicSeekBar;  //进度条
    private TextView mTvMusicName, mTvMusicList;    //音乐名称，音乐列表

    private Music mPlayingMusic;    //当前正在播放的音乐
    private int mMusicState = Contants.STOP;    //音乐播放状态
    private int mMusicMode = Contants.MODE_MUSIC_LIST_ORDER;    //当前播放模式

    private MusicReceiver mMusicReceiver;

    private boolean isSeekbarChanged = false;   //进度条是否被滑动
    private int mMusicPlayPosition = 0; //当前音乐的播放位置

    private ObjectAnimator mAnimator;   //图片动画

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_main);
        View title = findViewById(R.id.base_title);
        title.setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
        mMusicImage = (XCRoundImageView) findViewById(R.id.main_music_image);
        mPlayedTime = (TextView) findViewById(R.id.main_music_play_time);
        mTotalTime = (TextView) findViewById(R.id.main_music_total_time);
        mMusicSeekBar = (SeekBar) findViewById(R.id.main_music_seekbar);
        mPlayMode = (ImageView) findViewById(R.id.main_play_mode);
        mMusicNext = (ImageView) findViewById(R.id.main_music_next);
        mMusicPrev = (ImageView) findViewById(R.id.main_music_previous);
        mMusicPlay = (ImageView) findViewById(R.id.main_music_play);
        mTvMusicName = (TextView) findViewById(R.id.main_music_name);
        mTvMusicList = (TextView) findViewById(R.id.main_music_list);
        mTvMusicName.setText("未知");

        initAnimation();

        //注册广播接收器
        mMusicReceiver = new MusicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Contants.ACTIVITY_RECEIVER_ACTION);
        registerReceiver(mMusicReceiver, filter);

        if (!Utils.isMusicServerRunning(this)) {
            startMusicServer(); //启动服务
        } else {
            Intent intent = getIntent();
            if (intent.hasExtra(Contants.MUSIC_POS)) {  //从歌曲列表进入此页面
                int position = intent.getIntExtra(Contants.MUSIC_POS, -1);
                Bundle bundle = new Bundle();
                bundle.putInt(Contants.MUSIC_POS, position);
                sendBroadcastToServer(Contants.REPLAY, bundle);
            } else {    //如果服务在运行，则发广播让服务更新界面
                sendBroadcastToServer(Contants.UPDATE_PLAY_ACTIVITY);
            }
        }

    }

    /**
     * 初始化动画
     */
    private void initAnimation(){
        mAnimator = ObjectAnimator.ofFloat(mMusicImage, "rotation", 0, 360);
        mAnimator.setDuration(6000);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setRepeatCount(-1);   //无限重复播放
        mAnimator.setRepeatMode(ObjectAnimator.INFINITE);
    }

    /**
     * 启动播放音乐服务
     */
    private void startMusicServer() {
        Intent intent = new Intent(this, MusicServer.class);
        startService(intent);
    }

    @Override
    protected void setListener() {
        mTvMusicList.setOnClickListener(this);
        mPlayMode.setOnClickListener(this);
        mMusicNext.setOnClickListener(this);
        mMusicPlay.setOnClickListener(this);
        mMusicPrev.setOnClickListener(this);

        mMusicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPlayedTime.setText(Utils.formatToString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeekbarChanged = true;
                Bundle bundle = new Bundle();
                bundle.putInt(Contants.MUSIC_PLAY_POS, -1);
                bundle.putBoolean(Contants.IS_CHANGED, isSeekbarChanged);
                sendBroadcastToServer(Contants.UPDATE_MUSIC_SEEKBAR, bundle);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeekbarChanged = false;
                int currentPosition = seekBar.getProgress();
                Bundle bundle = new Bundle();
                bundle.putInt(Contants.MUSIC_PLAY_POS, currentPosition);
                bundle.putBoolean(Contants.IS_CHANGED, isSeekbarChanged);
                sendBroadcastToServer(Contants.UPDATE_MUSIC_SEEKBAR, bundle);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_music_list:
                Intent intent = new Intent(MainActivity.this, MusicTypeActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.main_play_mode:   //切换播放模式
                sendBroadcastToServer(Contants.UPDATE_MUSCI_PLAY_MODE);
                break;
            case R.id.main_music_next:  //下一首
                sendBroadcastToServer(Contants.PLAY_NEXT);
                break;
            case R.id.main_music_play:  //播放暂停
                sendBroadcastToServer(Contants.UPDATE_MUSIC_STATE);
                break;
            case R.id.main_music_previous:  //上一首
                sendBroadcastToServer(Contants.PLAY_PREVIOUS);
                break;
        }
    }

    /**
     * 切换播放模式
     */
    private void updateMusicMode(int musicMode) {
        switch (musicMode) {
            case Contants.MODE_MUSIC_LIST_ORDER:
                mPlayMode.setImageResource(R.mipmap.mode_order_normal);
                break;
            case Contants.MODE_MUSIC_RANDOM:
                mPlayMode.setImageResource(R.mipmap.mode_shuffle_normal);
                break;
            case Contants.MODE_MUSIC_LIST_LOOP:
                mPlayMode.setImageResource(R.mipmap.mode_loop_normal);
                break;
            case Contants.MODE_MUSIC_LOOP:
                mPlayMode.setImageResource(R.mipmap.mode_loop_one_normal);
                break;
        }

    }

    /**
     * 更新进度条
     */
    private void updateMusicSeekBar(int currentPosition) {
        mMusicSeekBar.setMax(mPlayingMusic.getTotalTime());
        mMusicSeekBar.setProgress(currentPosition);
        String playedTime = Utils.formatToString(currentPosition);
        mPlayedTime.setText(playedTime);
    }

    /**
     * 更新当前音乐的相关信息
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void updateMusicInfo(int musicMode, int musicPlayPos) {
        mTvMusicName.setText(mPlayingMusic.getName());
        mTotalTime.setText(Utils.formatToString(mPlayingMusic.getTotalTime()));
        if (mMusicState == Contants.PLAY) {
            mMusicPlay.setImageResource(R.mipmap.img_music_pause);
            if (!mAnimator.isStarted()){    //启动动画
                mAnimator.start();
            }
            if (mAnimator.isPaused()){  //如果动画暂停，播放动画
                mAnimator.resume();
            }
        } else {
            mMusicPlay.setImageResource(R.mipmap.img_music_play);
            if (mAnimator.isRunning()){ //如果动画播放，暂停动画
                mAnimator.pause();
            }
        }
        updateMusicMode(musicMode);
        updateMusicSeekBar(musicPlayPos);
    }

    /**
     * 给服务发送广播
     * @param state
     *
     */
    private void sendBroadcastToServer(int state) {
        Intent intent = new Intent();
        intent.setAction(Contants.SERVER_RECEIVER_ACTION);
        intent.putExtra(Contants.SERVER_SIGNAL, state);
        sendBroadcast(intent);
    }

    private void sendBroadcastToServer(int state, Bundle bundle){
        Intent intent = new Intent();
        intent.setAction(Contants.SERVER_RECEIVER_ACTION);
        intent.putExtra(Contants.SERVER_SIGNAL, state);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        sendBroadcast(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 用户在首页点击返回时 让应用后台
            moveTaskToBack(true);
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        if (mAnimator.isStarted()){ //取消动画
            mAnimator.cancel();
        }

        if (mMusicReceiver != null) {
            unregisterReceiver(mMusicReceiver);
        }
        super.onDestroy();
    }

    class MusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int activitySignal = bundle.getInt(Contants.ACTIVITY_SIGNAL, -1);
            switch (activitySignal) {
                case Contants.UPDATE_PLAY_ACTIVITY:
                    mMusicState = bundle.getInt(Contants.MUSIC_STATE);
                    mPlayingMusic = (Music) bundle.getSerializable(Contants.CURRENT_MUSIC);
                    mMusicMode = bundle.getInt(Contants.MUSIC_MODE);
                    mMusicPlayPosition = bundle.getInt(Contants.MUSIC_PLAY_POS, 0);
                    updateMusicInfo(mMusicMode, mMusicPlayPosition);
                    break;
            }
        }
    }

}
