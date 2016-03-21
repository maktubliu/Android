package com.yyx.musicplayer.xml;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.yyx.musicplayer.util.Contants;

/**
 * Created by Administrator on 2016/3/6.
 */
public class MusicInfo {
    private static final String XML_MUSIC_INFO = "music_info";

    //音乐在集合中的位置
    private static final String MUSIC_POSITION = "music_position";
    //音乐的播放模式
    private static final String MUSIC_PLAY_MODE = "music_mode";
    //音乐的播放位置
    private static final String MUSIC_PLAY_POSITION = "music_play_position";

    public static void setMusicPosition(Context context, int value){
         SharedPreferences.Editor editor = context.getSharedPreferences
                 (XML_MUSIC_INFO, Activity.MODE_PRIVATE).edit();
        editor.putInt(MUSIC_POSITION, value);
        editor.commit();
    }

    public static void setMusicMode(Context context, int value){
        SharedPreferences.Editor editor = context.getSharedPreferences
                (XML_MUSIC_INFO, Activity.MODE_PRIVATE).edit();
        editor.putInt(MUSIC_PLAY_MODE, value);
        editor.commit();
    }

    public static void setMusicPlayPosition(Context context, int value){
        SharedPreferences.Editor editor = context.getSharedPreferences
                (XML_MUSIC_INFO, Activity.MODE_PRIVATE).edit();
        editor.putInt(MUSIC_PLAY_POSITION, value);
        editor.commit();
    }

    public static int getMusicPosition(Context context){
        SharedPreferences preferences = context.getSharedPreferences
                (XML_MUSIC_INFO, Activity.MODE_PRIVATE);
        return preferences.getInt(MUSIC_POSITION, 0);
    }

    public static int getMusicMode(Context context){
        SharedPreferences preferences = context.getSharedPreferences
                (XML_MUSIC_INFO, Activity.MODE_PRIVATE);
        return preferences.getInt(MUSIC_PLAY_MODE, Contants.MODE_MUSIC_LIST_ORDER);
    }

    public static int getMusicPlayPosition(Context context){
        SharedPreferences preferences = context.getSharedPreferences
                (XML_MUSIC_INFO, Activity.MODE_PRIVATE);
        return preferences.getInt(MUSIC_PLAY_POSITION, 0);
    }




}
