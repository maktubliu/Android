package com.example.lhh.networktest;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.lhh.networktest.fragment.XmlRequestFragment;
import com.example.lhh.networktest.util.Constans;

/**
 * Created by LHH on 2016/4/17.
 */
public class RequestActivity extends FragmentActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        android.support.v4.app.Fragment fr;
        String tag;
        int titleRes;
        int frIndex = getIntent().getIntExtra(Constans.Extra.FRAGMENT_INDEX, 0);
        switch (frIndex){
            default:
            case XmlRequestFragment.INDEX:
                tag = XmlRequestFragment.class.getName();
                fr = getSupportFragmentManager().findFragmentByTag(tag);
                if(fr == null){
                    fr = new XmlRequestFragment();
                }
                titleRes = R.string.xml_request;
                break;
        }
        setTitle(titleRes);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fr, tag).commit();
    }
}
