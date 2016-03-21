package com.yyx.musicplayer.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yyx.musicplayer.R;

/**
 * Created by Administrator on 2016/2/28.
 */
public class MusicTypeActivity extends BaseActivty implements View.OnClickListener{
    private RelativeLayout mOnlineLayout, mLocalLayout;
    private TextView mTvOnlineName;
    private TextView mTvLocalName;

    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_music_type);
        mRight.setVisibility(View.INVISIBLE);
        mTitle.setText("歌曲分类");
    }

    @Override
    protected void initView() {
        mOnlineLayout = (RelativeLayout) findViewById(R.id.music_type_online_layout);
        mLocalLayout = (RelativeLayout) findViewById(R.id.music_type_local_layout);
        mTvOnlineName = (TextView) findViewById(R.id.music_type_online_name);
        mTvLocalName = (TextView) findViewById(R.id.music_type_local_name);
        mTvOnlineName.setText("在线歌曲");
        mTvLocalName.setText("本地歌曲");
    }

    @Override
    protected void setListener() {
        mOnlineLayout.setOnClickListener(this);
        mLocalLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.music_type_online_layout:
                Toast.makeText(MusicTypeActivity.this,
                        "暂不支持此功能", Toast.LENGTH_SHORT).show();
                break;
            case R.id.music_type_local_layout:
                Intent intent = new Intent(MusicTypeActivity.this, LocalMusicListActivity.class);
                MusicTypeActivity.this.startActivity(intent);
                break;
        }
    }
}
