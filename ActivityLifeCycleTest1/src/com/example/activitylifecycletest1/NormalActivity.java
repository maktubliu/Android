package com.example.activitylifecycletest1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
public class NormalActivity extends Activity{
	public final static String TAG2 = "NormalActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.normal_layout);
    	Intent intent = getIntent();
    	Bundle data = intent.getExtras();
    	String fuck = data.getString("ok");
    	Log.d(TAG2, fuck);
    }
    @Override
	protected void onStart(){
		super.onStart();
		Log.d(TAG2, "onStart");
	}
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(TAG2, "onResume");
	}
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(TAG2, "onPause");
	}
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(TAG2, "onStop");
	}
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(TAG2, "onDestory");
	}
	@Override
	protected void onRestart(){
		super.onRestart();
		Log.d(TAG2, "onRestart");
	}
}
