package com.example.lhh.adapterdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Created by LHH on 2016/1/27.
 */
public class AdapterDemo extends Activity {
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrayadapterdemo);
        lv=(ListView)findViewById(R.id.arrayadpterdemolistview);
        lv.setAdapter();
    }
}
