package com.example.activitytest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_layout);
		Log.d("FirstAcitvity", this.toString());
		Button button1 = (Button) findViewById(R.id.button_1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// String data = "Hello SecondActivity";
				Intent intent = new Intent(FirstActivity.this,
						FirstActivity.class);
				// intent.putExtra("extra_data", data);
				// Intent intent1 = new
				// Intent(FirstActivity.this,ThirdActivity.class);
				// intent.putExtra("extra_data1", data1);
				// Intent intent = new Intent(Intent.ACTION_DIAL);
				// intent.addCategory("com.example.activitytest.MY_CATEGORY");
				// intent.addCategory("com.example.activitytest.FUCK_CATEGORY");
				// intent.setData(Uri.parse("tel:10086"));
				//startActivityForResult(intent, 1);
				startActivity(intent);
				// Toast.makeText(FirstActivity.this, R.string.fuck,
				// Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				String returnedData = data.getStringExtra("data_return");
				Log.d("FirstActivity", returnedData);
			}
			break;
		default:
		}
	}

	public boolean onOptionItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.add_item:
			Toast.makeText(FirstActivity.this, "You clicked Add!",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.remove_item:
			Toast.makeText(FirstActivity.this, "You clicked Remove!",
					Toast.LENGTH_SHORT).show();
			break;
		default:
		}
		return true;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
