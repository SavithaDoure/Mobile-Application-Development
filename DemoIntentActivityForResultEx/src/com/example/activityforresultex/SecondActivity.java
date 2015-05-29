package com.example.activityforresultex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SecondActivity extends Activity {
	EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		editText = (EditText) findViewById(R.id.editText1);

		findViewById(R.id.buttonSendResult).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String text = editText.getText().toString();
						if(text == null || text.length()==0){
							setResult(RESULT_CANCELED);
						}
						else{
							Intent data = new Intent();
							data.putExtra(MainActivity.TEXT_KEY, text);
							setResult(RESULT_OK,data);
						}
						finish();
					}
				});
	}
}
