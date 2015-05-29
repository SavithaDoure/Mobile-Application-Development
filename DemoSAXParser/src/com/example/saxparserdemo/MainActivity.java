package com.example.saxparserdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new GetPersonAsyncTask().execute("http://liisp.uncc.edu/~mshehab/api/xml/persons.xml");
	}
}
