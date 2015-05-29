package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.List;

import com.mad.bean.Keys;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class MainActivity extends Activity  {

	String toDoItem;
	AlertDialog alertDialog;
	ArrayList<String> toDoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d("MainActivity", "before Trans");
		getFragmentManager()
				.beginTransaction()
				.add(R.id.container, new SplashScreenFragment(), "splashscreen")
				.commit();
		Log.d("MainActivity", "after Trans");

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

					getFragmentManager()
							.beginTransaction()
							.replace(R.id.container, new CurrentLocFragment(), "currlocation")
							.addToBackStack(null).commit();

			}
		}, 5 * 1000);

	}





	
	
}
