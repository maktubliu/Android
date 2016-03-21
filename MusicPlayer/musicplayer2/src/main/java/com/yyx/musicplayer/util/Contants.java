package com.yyx.musicplayer.util;

/**
 * Created by Administrator on 2016/2/29.
 */
public class Contants {
    public static final String SERVER_RECEIVER_ACTION = "server_receiver";
    public static final String ACTIVITY_RECEIVER_ACTION = "activity_receiver";

    //音乐的播放状态
    public static final int PLAY = 1; //播放
    public static final int PAUSE = 2; //暂停
    public static final int STOP = 3;   //停止

    public static final int UPDATE_PLAY_ACTIVITY = 4;   //更新界面状态
    public static final int MUSIC_COMPLETION = 5;   //当音乐播放完成
    public static final int UPDATE_MUSIC_STATE = 6; //更新音乐的播放状态
    public static final int UPDATE_MUSCI_PLAY_MODE = 7; //更新音乐播放模式
    public static final int UPDATE_MUSIC_SEEKBAR = 8;  //更新进度条

    //播放模式
    public static final int MODE_MUSIC_LIST_ORDER = 10; //列表顺序播放
    public static final int MODE_MUSIC_LIST_LOOP = 11;  //列表循环播放
    public static final int MODE_MUSIC_LOOP = 12;   //单曲循环
    public static final int MODE_MUSIC_RANDOM = 13; //随机播放

    //对歌曲列表的操作
    public static final int PLAY_NEXT = 20; //播放下一首
    public static final int PLAY_PREVIOUS = 21;     //播放上一首
    public static final int REPLAY = 22; //重新播放

    //activity向server发送的动作
    public static final String SERVER_SIGNAL = "server_signal";
    //server向activity发送的动作
    public static final String ACTIVITY_SIGNAL = "activity_signal";

    /*********************Activity向Server传递的参数名称******************/
    //音乐在音乐集合中的位置
    public static final String MUSIC_POS= "music_position";
    //音乐的播放位置
    public static final String MUSIC_PLAY_POS= "music_play_position";
    //进度条是否被滑动
    public static final String IS_CHANGED= "is_changed";

    /********************Server向Activity传递的参数名称********************/
    //音乐的状态
    public static final String MUSIC_STATE= "music_state";
    //当前的音乐文件
    public static final String CURRENT_MUSIC= "current_music";
    //音乐的播放模式
    public static final String MUSIC_MODE= "music_mode";




}
