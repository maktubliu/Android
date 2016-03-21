package com.example.activitylifecycletest1;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class DialogActivity extends Activity{
	public final static String TAG1 = "DialogActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.dialog_layout);
    }
    @Override
	protected void onStart(){
		super.onStart();
		Log.d(TAG1, "onStart");
	}
	@Override
	protected void onResume(){
		super.onResume();
		Log.d(TAG1, "onStart");
	}
	@Override
	protected void onPause(){
		super.onPause();
		Log.d(TAG1, "onPause");
	}
	@Override
	protected void onStop(){
		super.onStop();
		Log.d(TAG1, "onStop");
	}
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.d(TAG1, "onDestory");
	}
	@Override
	protected void onRestart(){
		super.onRestart();
		Log.d(TAG1, "onRestart");
	}
}
