package com.example.demoparse;

import java.util.ArrayList;
import java.util.List;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class EventsActivity extends Activity {

	Button bAddEvent;
	ListView lvEventsList;
	ArrayList<String> eventNames;
	List<Event> eventList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	/*	setContentView(R.layout.activity_events);
		bAddEvent = (Button) findViewById(R.id.bAdd);
		lvEventsList = (ListView) findViewById(R.id.listView1);*/

		bAddEvent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EventsActivity.this,
						AddEventActivity.class);
				startActivity(intent);
			}
		});

		/*
		 * ParseQueryAdapter<ParseObject> adapter = new
		 * ParseQueryAdapter<ParseObject>( this, new
		 * ParseQueryAdapter.QueryFactory<ParseObject>() { public
		 * ParseQuery<ParseObject> create() { ParseUser user =
		 * ParseUser.getCurrentUser();
		 * 
		 * // Here we can configure a ParseQuery to our heart's // desire.
		 * 
		 * ParseQuery query = new ParseQuery("Post"); query.whereEqualTo("user",
		 * user); return query; }
		 * 
		 * });
		 */
		ParseUser user = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
		query.whereEqualTo("user", user);
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objList, ParseException e) {
				if (e == null) {
					eventList = new ArrayList<Event>();
					eventNames = new ArrayList<String>();
					Event eventObj;
					for (ParseObject event : objList) {
						eventObj = new Event(event.getString("eventName"),
								event.getString("eventdesc"), event
										.getString("eventLoc"), event
										.getString("eventDate"));
						eventList.add(eventObj);
						eventNames.add(eventObj.getEname());
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								EventsActivity.this,
								android.R.layout.simple_list_item_1, eventNames);
						lvEventsList.setAdapter(adapter);
					}
				} else {
					Log.d("DemoParse", "Error: " + e.getMessage());
				}

			}
		});

		lvEventsList
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

					}
				});

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.add_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*if (id == ) {
			Intent intent = new Intent(this, AddEventActivity.class);
			startActivity(intent);
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}

}
