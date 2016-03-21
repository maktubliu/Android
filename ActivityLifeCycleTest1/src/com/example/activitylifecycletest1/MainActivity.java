package com.example.activitylifecycletest1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


public class MainActivity extends Activity {
	
	public final static String TAG = "MainActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreat");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Button startNormalActivity = (Button)findViewById(R.id.start_normal_activity);
		Button startDialogActivity = (Button)findViewById(R.id.start_dialog_activity);
		startNormalActivity.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(MainActivity.this,NormalActivity.class);
				Bundle data = new Bundle();
				String fuck = "fuck!";
				data.putString("ok", fuck);
				intent.putExtras(data);
				startActivity(intent);
			}
		});
		startDialogActivity.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent intent = new Intent(MainActivity.this,DialogActivity.class);
				startActivity(intent);
			}
		});
		if(savedInstanceState != null){
			String tempData = savedInstanceState.getString("data_key");
			Log.d(TAG, tempData);
		}
		}
		@Override
		protected void onStart(){
			super.onStart();
			Log.d(TAG, "onStart");
		}
		@Override
		protected void onResume(){
			super.onResume();
			Log.d(TAG, "onStart");
		}
		@Override
		protected void onPause(){
			super.onPause();
			Log.d(TAG, "onPause");
		}
		@Override
		protected void onStop(){
			super.onStop();
			Log.d(TAG, "onStop");
		}
		@Override
		protected void onDestroy(){
			super.onDestroy();
			Log.d(TAG, "onDestory");
		}
		@Override
		protected void onRestart(){
			super.onRestart();
			Log.d(TAG, "onRestart");
		}
		@Override
		protected void onSaveInstanceState(Bundle outState){
			super.onSaveInstanceState(outState);
			String tempData = "Something you just typed";
			outState.putString("data_key", tempData);
		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}*/

	/*@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
