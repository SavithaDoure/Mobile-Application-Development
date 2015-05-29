package com.example.storinguitags;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements View.OnClickListener {
	LinearLayout root;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//Storing Data in UI Using Tags
/*		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(this);
		b.setTag(R.id.tag_key_color, Color.RED);
		
		b = (Button) findViewById(R.id.button2);
		b.setOnClickListener(this);
		b.setTag(R.id.tag_key_color, Color.GREEN);
		
		b = (Button) findViewById(R.id.button3);
		b.setOnClickListener(this);
		b.setTag(R.id.tag_key_color, Color.BLUE);		
		root = (LinearLayout) findViewById(R.id.LinearLayout1);
		
*/
		
//Storing Data in UI Using Class
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new ButtonStore(Color.RED));
		
		b = (Button) findViewById(R.id.button2);
		b.setOnClickListener(new ButtonStore(Color.GREEN));
		
		b = (Button) findViewById(R.id.button3);	
		b.setOnClickListener(new ButtonStore(Color.BLUE));
		root = (LinearLayout) findViewById(R.id.LinearLayout1);
	}

	@Override
	public void onClick(View v) {
		Log.d("Demo", "onClick : "+v.toString());
		int color = ((Integer)v.getTag(R.id.tag_key_color)).intValue();
		root.setBackgroundColor(color);
		
	}
	
	public class ButtonStore implements View.OnClickListener{
		int color;
		
		public ButtonStore(int color) {
			this.color = color;
		}
		@Override
		public void onClick(View v) {
		//Set background using variable
			root.setBackgroundColor(color);		
		//Set background using hierarchy	
			((LinearLayout)v.getParent()).setBackgroundColor(color);
		}
		
	}
}
