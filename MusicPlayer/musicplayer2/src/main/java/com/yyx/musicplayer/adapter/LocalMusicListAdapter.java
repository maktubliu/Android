package com.yyx.musicplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yyx.musicplayer.R;
import com.yyx.musicplayer.activity.MainActivity;
import com.yyx.musicplayer.entity.Music;
import com.yyx.musicplayer.server.MusicServer;
import com.yyx.musicplayer.util.Contants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/28.
 */
public class LocalMusicListAdapter extends BaseAdapter<Music> {

    public LocalMusicListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_local_music_list, parent, false);
            holder.mTvMusicName = (TextView) convertView.findViewById(R.id.item_music_name);
            holder.mTvSingerName = (TextView) convertView.findViewById(R.id.item_singer_name);
            holder.mTvMusicFileSize = (TextView) convertView.findViewById(R.id.item_music_size);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvMusicName.setText(String.valueOf(mDatas.get(position).getName()));
        holder.mTvSingerName.setText(String.valueOf(mDatas.get(position).getSingerName()));
        long size = (long) mDatas.get(position).getSize();
        holder.mTvMusicFileSize.setText(formatBToMb(size) + " Mb");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Contants.MUSIC_POS, position);
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    //将Byte转换为Mb
    private String formatBToMb(long size) {
        float fSize = (float) size / (1024 * 1024);
        return String.format("%.2f", fSize);
    }

    class ViewHolder {
        TextView mTvMusicName;
        TextView mTvSingerName;
        TextView mTvMusicFileSize;
    }
}
