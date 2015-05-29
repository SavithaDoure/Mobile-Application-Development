package com.example.demoparse;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEventActivity extends Activity {

	EditText etName, etDesc, etLocation, etDate;
	Button bAddEvent, bRetrieve, bDelete;
	ParseUser user;
	ParseObject parseObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	/*	setContentView(R.layout.activity_add_event);
		etName = (EditText) findViewById(R.id.editText1);
		etDesc = (EditText) findViewById(R.id.editText2);
		etLocation = (EditText) findViewById(R.id.editText3);
		etDate = (EditText) findViewById(R.id.editText4);
		bAddEvent = (Button) findViewById(R.id.bAdd);
		bRetrieve = (Button) findViewById(R.id.bretrieve);
		bDelete = (Button) findViewById(R.id.bDel);*/

		bAddEvent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				user = ParseUser.getCurrentUser();

				// Make a new post
				ParseObject post = new ParseObject("Post");
				post.put("eventName", etName.getText().toString());
				post.put("eventdesc", etDesc.getText().toString());
				post.put("eventLoc", etLocation.getText().toString());
				post.put("eventDate", etDate.getText().toString());
				post.put("user", user);
				post.saveInBackground();
				Log.d("DemoParse", "Event Saved!!!");

			}
		});

		bRetrieve.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Find all posts by the current user
				findEvents();
			}
		});

		bDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				findEvents();
				if (parseObject != null) {
					parseObject.deleteInBackground();
					findEvents();
				}

			}
		});
	}

	// Find all posts by the current user
	public void findEvents() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
		query.whereEqualTo("user", user);
		query.whereEqualTo("eventName", etName.getText().toString());
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> arg0, ParseException e) {
				if (arg0.size() > 0) {
					parseObject = arg0.get(0);
					Log.d("DemoParse", "Retrieved: Size: " + arg0.size());
				} else if (e != null) {
					Log.d("DemoParse", "Error: " + e.getLocalizedMessage());
				} else
					Log.d("DemoParse", "List size 0");
			}
		});
	}
}
