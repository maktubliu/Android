package com.example.lhh.broadcastbestpractice;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by LHH on 2016/2/28.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
