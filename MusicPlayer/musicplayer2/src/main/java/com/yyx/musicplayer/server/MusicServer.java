package com.yyx.musicplayer.server;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.yyx.musicplayer.entity.Music;
import com.yyx.musicplayer.util.Contants;
import com.yyx.musicplayer.util.Utils;
import com.yyx.musicplayer.xml.MusicInfo;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.yyx.musicplayer.server.MusicServer.ServiceReceiver.*;

/**
 * Created by Administrator on 2016/2/29.
 */
public class MusicServer extends Service {
    private MediaPlayer mMediaPlayer;   //播放音乐对象
    private ServiceReceiver mReceiver;  //广播接收器(接收界面发来的广播)

    private Bundle mBundle;

    private List<Music> mMusicFiles;    //sd卡的音频文件集合
    private Music mPlayingMusic;    //当前正在播放的音乐
    private int mMusicPosition = 0;  //当前播放音乐在集合中的位置
    private int mMusicPlayPos = 0;  //当前音乐的播放位置
    private int mMusicState = Contants.STOP;    //音乐播放状态
    private int mMusicMode = Contants.MODE_MUSIC_LIST_ORDER;    //播放模式

    private PowerManager.WakeLock mWakeLock;    //电源锁对象

    private Timer mTimer;   //定时器
    private boolean isSeekbarChanged = false;   //进度条滑动标识

    private AudioManager mAmanager;
    MyOnAudioFocusChangeListener mListener;

    @Override
    public void onCreate() {
        super.onCreate();
        //申请电源锁，已在锁屏后，手机不清理后台服务(然并卵)
        acquireWakeLock();

        //注册接收器
        mReceiver = new ServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Contants.SERVER_RECEIVER_ACTION);
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(mReceiver, filter);

        mBundle = new Bundle();

        //从本地读取之前保存的音乐，模式和播放的位置(初始化界面用)
        mMusicPosition = MusicInfo.getMusicPosition(this);
        mMusicMode = MusicInfo.getMusicMode(this);
        mMusicPlayPos = MusicInfo.getMusicPlayPosition(this);

        //获取音乐列表和当前的音乐对象
        mMusicFiles = Utils.getMusicFile(this);
        if (mMusicFiles != null &&
                (mMusicPosition >= 0 && mMusicPosition < mMusicFiles.size())) {
            mPlayingMusic = mMusicFiles.get(mMusicPosition);
        }

        Uri uri = Uri.parse(mPlayingMusic.getUrl());
        mMediaPlayer = MediaPlayer.create(this, uri);

        mTimer = new Timer();
        mTimer.schedule(new UpdateProgressTask(), 0);

        mAmanager = (AudioManager) getApplicationContext().
                getSystemService(Context.AUDIO_SERVICE);
        mListener = new MyOnAudioFocusChangeListener();

        setListener();

    }

    private void setListener() {
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mMusicState = Contants.STOP;
                mMusicPlayPos = 0;  //自动播放下一首时，播放位置置0
                autoPlayNext();
            }
        });
    }

    // 申请设备电源锁
    private void acquireWakeLock() {
        if (null == mWakeLock) {
            PowerManager pm = (PowerManager) this
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, "");
            if (null != mWakeLock) {
                mWakeLock.acquire();
            }
        }
    }

    // 释放设备电源锁
    private void releaseWakeLock() {
        if (null != mWakeLock) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendBroadcastToActivity();
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新界面的数据
     */
    private void setUpdateActivityParam() {
        mBundle.clear();
        mBundle.putInt(Contants.ACTIVITY_SIGNAL, Contants.UPDATE_PLAY_ACTIVITY);
        mBundle.putInt(Contants.MUSIC_STATE, mMusicState);
        mBundle.putInt(Contants.MUSIC_MODE, mMusicMode);
        mBundle.putInt(Contants.MUSIC_PLAY_POS, mMusicPlayPos);
        mBundle.putSerializable(Contants.CURRENT_MUSIC, mPlayingMusic);

    }

    /**
     * 向界面发送广播
     */
    private void sendBroadcastToActivity() {
        setUpdateActivityParam();

        Intent intent = new Intent();
        intent.setAction(Contants.ACTIVITY_RECEIVER_ACTION);
        if (mBundle != null) {
            intent.putExtras(mBundle);
        }
        sendBroadcast(intent);
    }

    /**
     * 重新播放
     */
    private void replay() {
        int result = mAmanager.requestAudioFocus(mListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        /**焦点获得成功才能播放，这是为了不和其他音乐播放器同时播放,
         * 如果其他音乐播放器没有实现这个功能，则有可能同时播放
         **/
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
            if (mMusicPosition >= 0 && mMusicPosition < mMusicFiles.size()) {
                mPlayingMusic = mMusicFiles.get(mMusicPosition);
                mMediaPlayer.reset();
                try {
                    mMediaPlayer.setDataSource(mPlayingMusic.getUrl());
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                    mMusicState = Contants.PLAY;
                    mMediaPlayer.seekTo(mMusicPlayPos);

                    MusicInfo.setMusicPosition(MusicServer.this, mMusicPosition);

                    sendBroadcastToActivity();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                mMusicState = Contants.STOP;
                sendBroadcastToActivity();
            }
        }

    }

    /**
     * 播放下一首
     */
    private void playNext() {
        mMusicPosition++;
        if (mMusicPosition >= mMusicFiles.size()) {
            mMusicPosition = 0;
        }
        replay();
    }

    /**
     * 自动播放下一首
     */
    private void autoPlayNext() {
        switch (mMusicMode) {
            case Contants.MODE_MUSIC_LIST_ORDER:    //顺序播放
                if (mMusicPosition == mMusicFiles.size() - 1) {
                    sendBroadcastToActivity();
                } else {
                    playNext();
                }
                break;
            case Contants.MODE_MUSIC_LOOP:  //单曲循环
                replay();
                break;
            case Contants.MODE_MUSIC_LIST_LOOP:     //列表循环
                playNext();
                break;
            case Contants.MODE_MUSIC_RANDOM:    //随机播放
                Random rand = new Random();
                int max = mMusicFiles.size() - 1;
                mMusicPosition = rand.nextInt(max);
                replay();
                break;
        }
    }

    /**
     * 播放上一首
     */
    private void playPrevious() {
        mMusicPosition--;
        if (mMusicPosition < 0) {
            mMusicPosition = mMusicFiles.size() - 1;
        }
        replay();
    }

    /**
     * 更新音乐状态
     */
    private void updateMusicState() {
        //获取音频焦点
        int result = mAmanager.requestAudioFocus(mListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        /**焦点获得成功才能播放，这是为了不和其他音乐播放器同时播放,
         * 如果其他音乐播放器没有实现这个功能，则有可能同时播放
         **/
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
            switch (mMusicState) {
                case Contants.PLAY:
                    mMediaPlayer.pause();
                    mMusicState = Contants.PAUSE;
                    sendBroadcastToActivity();
                    break;
                case Contants.PAUSE:
                    mMediaPlayer.start();
                    mMusicState = Contants.PLAY;
                    sendBroadcastToActivity();
                    break;
                case Contants.STOP:
                    replay();
                    break;
            }
        }else{

        }

    }

    /**
     * 更新播放模式
     */
    private void updateMusicMode() {
        switch (mMusicMode) {
            case Contants.MODE_MUSIC_LIST_ORDER:    //顺序播放
                mMusicMode = Contants.MODE_MUSIC_RANDOM;
                break;
            case Contants.MODE_MUSIC_LOOP:  //单曲循环
                mMusicMode = Contants.MODE_MUSIC_LIST_ORDER;
                break;
            case Contants.MODE_MUSIC_LIST_LOOP:     //列表循环
                mMusicMode = Contants.MODE_MUSIC_LOOP;
                break;
            case Contants.MODE_MUSIC_RANDOM:    //随机播放
                mMusicMode = Contants.MODE_MUSIC_LIST_LOOP;
                break;
        }
        MusicInfo.setMusicMode(MusicServer.this, mMusicMode);
        sendBroadcastToActivity();
    }

    /**
     * 判断当前音乐是否在播放
     * @return
     */
    private boolean isPlaying(){
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onDestroy() {
        releaseWakeLock();
        mTimer.cancel();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
        mAmanager.abandonAudioFocus(mListener);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class UpdateProgressTask extends TimerTask {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isPlaying() && !isSeekbarChanged) {
                    mMusicPlayPos = mMediaPlayer.getCurrentPosition();
                    sendBroadcastToActivity();
                    MusicInfo.setMusicPlayPosition(MusicServer.this, mMusicPlayPos);
                }
            }

        }
    }

    class ServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                //监听耳机拔出动作，耳机拔出后需暂停音乐
                case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                    if (isPlaying()) {
                        mMediaPlayer.pause();
                        mMusicState = Contants.PAUSE;
                        sendBroadcastToActivity();
                    }
                    break;
                case Contants.SERVER_RECEIVER_ACTION:
                    int serverSignal = intent.getIntExtra(Contants.SERVER_SIGNAL, -1);
                    switch (serverSignal) {
                        case Contants.UPDATE_MUSIC_STATE:
                            updateMusicState();
                            break;
                        case Contants.PLAY_NEXT:
                            mMusicPlayPos = 0;
                            playNext();
                            break;
                        case Contants.PLAY_PREVIOUS:
                            mMusicPlayPos = 0;
                            playPrevious();
                            break;
                            case Contants.REPLAY:
                                mMusicPlayPos = 0;  //重新播放时，播放位置置0
                                Bundle bun = intent.getExtras();
                                mMusicPosition = bun.getInt(Contants.MUSIC_POS, 0);
                                replay();
                                break;
                        case Contants.UPDATE_MUSCI_PLAY_MODE:
                            updateMusicMode();
                            break;

                        case Contants.UPDATE_PLAY_ACTIVITY:
                            sendBroadcastToActivity();
                            break;
                        case Contants.UPDATE_MUSIC_SEEKBAR:
                            Bundle bundle = intent.getExtras();
                            mMusicPlayPos = bundle.getInt(Contants.MUSIC_PLAY_POS, -1);
                            isSeekbarChanged = bundle.getBoolean(Contants.IS_CHANGED, false);
                            if (mMusicPlayPos != -1) {
                                mMediaPlayer.seekTo(mMusicPlayPos);
                            }
                            break;
                    }
                    break;
            }


        }

    }

    /**
     * 音频焦点的监听器
     */
    class MyOnAudioFocusChangeListener implements
            AudioManager.OnAudioFocusChangeListener
    {
        @Override
        public void onAudioFocusChange(int focusChange)
        {
            switch (focusChange){
                //长时间失去焦点
                case AudioManager.AUDIOFOCUS_LOSS:
                    if (isPlaying()){
                        mMusicState = Contants.PAUSE;
                        mMediaPlayer.pause();
                        sendBroadcastToActivity();
                    }
                    break;
                //短时间失去焦点
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:

                    break;
                //获得焦点
                case AudioManager.AUDIOFOCUS_GAIN:

                    break;
            }

        }
    }
}
