package com.example.activitytest;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ThirdActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.third_layout);
		Button button3 = (Button)findViewById(R.id.button_3);
		//Intent intent1 = getIntent();
		//int data1 = intent1.getIntExtra("extra_data1", 0);
		Log.d("ThirdActivity", "Task id is" + R.id.button_3);
		button3.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				ActivityCollector.finishedall();
			}
		});
	}
}
