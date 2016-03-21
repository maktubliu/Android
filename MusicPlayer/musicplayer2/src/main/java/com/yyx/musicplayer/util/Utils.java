package com.yyx.musicplayer.util;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;

import com.yyx.musicplayer.entity.Music;
import com.yyx.musicplayer.server.MusicServer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/2/28.
 */
public class Utils {

    /**
     * 判断音乐服务是否启动
     *
     * @param context
     * @return
     */
    public static boolean isMusicServerRunning(Context context) {
        ActivityManager manager = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo server :
                manager.getRunningServices(Integer.MAX_VALUE)) {
            if (MusicServer.class.getName()
                    .equals(server.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取SD卡中的音乐文件
     * @param context
     * @return
     */
    public static List<Music> getMusicFile(Context context) {
        //生成动态数组，并且转载数据
        List<Music> MusicFiles = new ArrayList<Music>();

        //查询媒体数据库
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        //遍历媒体数据库
        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                //歌曲编号
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                //歌曲标题
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

                if (size > 1024 * 800) {//大于800K
                    if (title.equals("<unknown>") || title.equals("")) {
                        title = "未知";
                    }
                    if ("<unknown>".equals(artist) || "".equals(artist)) {
                        artist = "未知";
                    }

                    Music music = new Music(id, title, artist,
                            url, album, duration, size);
                    MusicFiles.add(music);
                }
                cursor.moveToNext();
            }
        }
        return MusicFiles;
    }

    /**
     * 将int值转换为分钟和秒的格式
     * @param value
     * @return
     */
    public static String formatToString(int value){
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        Date date = new Date(value);
        String result = format.format(date);
        return result;
    }
}
