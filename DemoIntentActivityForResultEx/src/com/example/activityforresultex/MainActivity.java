package com.example.activityforresultex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final int REQ_CODE = 100;
	static final String TEXT_KEY = "text";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.button1).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								SecondActivity.class);

						startActivityForResult(intent, REQ_CODE);

					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQ_CODE){
			if(resultCode == RESULT_OK){
				Toast.makeText(this, data.getExtras().getString(TEXT_KEY), Toast.LENGTH_LONG).show();
			}
			else if(resultCode == RESULT_CANCELED){
				Toast.makeText(this, "Result Cancelled", Toast.LENGTH_LONG).show();
			}
		}

	}
}
