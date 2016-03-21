package com.example.activitytest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class BaseActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanse){
		super.onCreate(savedInstanse);
		Log.d("SavedActivity", getClass().getSimpleName());
		ActivityCollector.addactivity(this);
	}
	
	@Override
	protected void onDestroy(){
		ActivityCollector.removeactivity(this);
	}
}
