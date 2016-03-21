package com.example.activitylifecycletest;

import android.app.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class DialogAcitvity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstaceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_layout);
	}
}
