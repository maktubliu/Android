package com.yyx.musicplayer.activity;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/29.
 */
public class App extends Application {

    private List<Activity> mActivitys;
    private Activity mTopActivity;

    public static App mInstance;

    public static App getInstance() {
        if (mInstance == null) {
            mInstance = new App();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mActivitys = new ArrayList<Activity>();
    }

    public Activity getTopActivity() {
        return mTopActivity;
    }

    public void setTopActivity(Activity activity) {
        mTopActivity = activity;
    }


    public void addActivity(Activity activity) {
        if (mActivitys == null || activity == null)
            return;
        mActivitys.add(activity);
    }

    public void removeActivity(Class<?> pClass) {
        if (mActivitys == null) {
            return;
        }
        for (int i = 0; i < mActivitys.size(); i++) {
            if (mActivitys.get(i).getClass().equals(pClass)) {
                mActivitys.remove(i);
                i--;
            }
        }
    }

    public void removeActivity(Activity activity) {
        if (mActivitys == null)
            return;
        mActivitys.remove(activity);
    }


    public void finishActivity(int position) {
        if (mActivitys == null)
            return;
        Activity activity = mActivitys.get(position);
        activity.finish();
        mActivitys.remove(position);
    }

    public void finishActicity(Activity activity) {
        if (mActivitys == null)
            return;
        activity.finish();
        mActivitys.remove(activity);
    }


    public void finishAllActivity() {
        for (Activity activity : mActivitys) {
            activity.finish();
        }
        if (mActivitys != null) {
            mActivitys.clear();
        }
    }

}
