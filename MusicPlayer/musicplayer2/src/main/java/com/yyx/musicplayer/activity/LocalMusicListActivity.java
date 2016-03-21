package com.yyx.musicplayer.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;

import com.yyx.musicplayer.R;
import com.yyx.musicplayer.adapter.LocalMusicListAdapter;
import com.yyx.musicplayer.entity.Music;
import com.yyx.musicplayer.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/28.
 */
public class LocalMusicListActivity extends BaseActivty {
    private ListView mLvLocalMusic;
    private LocalMusicListAdapter mAdapter;
    private List<Music> mMusicDatas;
    private Context mContext = this;


    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_local_music_list);
        mRight.setVisibility(View.INVISIBLE);
        mTitle.setText("本地歌曲");
    }

    @Override
    protected void initView() {
        mLvLocalMusic = (ListView) findViewById(R.id.local_music_list);
        mMusicDatas = new ArrayList<Music>();
        mMusicDatas = Utils.getMusicFile(mContext);
        mAdapter = new LocalMusicListAdapter(this);
        mAdapter.resetData(mMusicDatas);
        mLvLocalMusic.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {

    }



}
