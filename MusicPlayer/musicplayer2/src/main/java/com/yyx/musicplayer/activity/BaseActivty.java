package com.yyx.musicplayer.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yyx.musicplayer.R;

/**
 * Created by Administrator on 2016/2/27.
 */
public abstract class BaseActivty extends AppCompatActivity {
    private RelativeLayout mActivityContent;
    private LayoutInflater mInflater;

    //title
    protected ImageView mBack;
    protected TextView mTitle, mRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setScreenOrietation();
        super.onCreate(savedInstanceState);
        App.getInstance().addActivity(this);
        App.getInstance().setTopActivity(this);
        mInflater = LayoutInflater.from(this);
        setLayout();
        initView();
        setListener();
        getSupportActionBar().hide();
    }

    public void setContentView(int resId) {
        super.setContentView(R.layout.activity_base);
        initTitleView();
        mActivityContent = (RelativeLayout) findViewById(R.id.activity_content);
        mInflater.inflate(resId, mActivityContent, true);
    }

    private void initTitleView(){
        mBack = (ImageView) findViewById(R.id.title_left);
        mTitle = (TextView) findViewById(R.id.title_text);
        mRight = (TextView) findViewById(R.id.title_right);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    private void back(){
        BaseActivty.this.finish();
    }

    private void setScreenOrietation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
    }

    /**
     * 初始化布局
     */
    protected abstract void setLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化监听器
     */
    protected abstract  void setListener();

}
