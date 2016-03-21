package com.example.activitytest;

import android.app.Activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.second_layout);
		// Intent intent = getIntent();
		// String data = intent.getStringExtra("extra_data");
		// Log.d("SecondActivity", data);
		// int data1 = 2;
		// Intent intent1 = new Intent(SecondActivity.this,ThirdActivity.class);
		// intent1.putExtra("extra_data1", data1);
		// startActivity(intent1);
		Button button2 = (Button) findViewById(R.id.button_2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent();
				intent2.putExtra("data_return", "Hello FirstActivity");
				setResult(RESULT_OK, intent2);
				finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.putExtra("data_return", "Hello FirstActivity");
		setResult(RESULT_OK, intent);
		finish();
	}

}
