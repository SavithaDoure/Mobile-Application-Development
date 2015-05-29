package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_events);
				
		Button b = (Button) findViewById(R.id.button1);		
		
		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("Demo", "Approach 1 Clicked");
				
			}
		});
		
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button4).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.button2){
			Log.d("Demo", "Approach 2 Clicked");	
		}
		else if(v.getId() == R.id.button4){
			Log.d("Demo", "Approach 4 Clicked");	
		}
			
	}
	
	public void whenClicked(View v){
		Log.d("Demo", "Approach 2 Clicked");
	}

	
}
