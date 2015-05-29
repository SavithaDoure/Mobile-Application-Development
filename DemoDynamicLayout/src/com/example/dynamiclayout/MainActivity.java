package com.example.dynamiclayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		RelativeLayout relativeLayout = new RelativeLayout(this);
		relativeLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		setContentView(relativeLayout);
		
		TextView textview = new TextView(this);
		textview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		textview.setText(R.string.hello_world);
		textview.setId(1);
		
		relativeLayout.addView(textview);
		
		Button button = new Button(this);
		button.setText(R.string.button_click);
		
		//Layout params of the button
		RelativeLayout.LayoutParams buttonRelLayout = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		buttonRelLayout.addRule(RelativeLayout.BELOW, textview.getId());
		
		button.setLayoutParams(buttonRelLayout);
		relativeLayout.addView(button);
	}
}
