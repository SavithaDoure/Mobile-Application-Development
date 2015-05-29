package com.example.group1a_hw05;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnSwipeListener;
import com.google.gson.Gson;
import com.mad.adapter.SingleEditWeatherAdapter;
import com.mad.bean.PlaceDetails;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class EditTripActivity extends Activity implements View.OnClickListener {
	PlaceDetails placeDetails;
	private List<ApplicationInfo> mAppList;
	List<PlaceDetails> placeList;
	SwipeMenuListView edittriplist;
	ImageView addteditrip;
	Button savetrip;
	SharedPlacedDetails details;
	SharedPreferences preference;
	ParseUser currentUser;
	String tripName, travelDate;
	String update = "no";
	String updateshare="no";
	SingleEditWeatherAdapter adapter;
	public static final String MyPREFERENCES = "MyPrefs";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_trip);
		currentUser = ParseUser.getCurrentUser();
		placeList = new ArrayList<PlaceDetails>();
		details = new SharedPlacedDetails();
		edittriplist = (SwipeMenuListView) findViewById(R.id.edittriplist);
		addteditrip = (ImageView) findViewById(R.id.addtrip);
		savetrip = (Button) findViewById(R.id.save);
		savetrip.setOnClickListener(this);
		addteditrip.setOnClickListener(this);
		SharedPreferences preference = getApplicationContext()
				.getSharedPreferences(EditTripActivity.MyPREFERENCES,
						Context.MODE_PRIVATE);
		String json = preference.getString("placesList", null);
		if (json != null) {

			Gson gson = new Gson();
			details = gson.fromJson(json, SharedPlacedDetails.class);
			placeList = details.getPlaceList();

			if (getIntent().getExtras().getSerializable("edittripdetaiils") != null) {
				placeDetails = (PlaceDetails) getIntent().getExtras()
						.getSerializable("edittripdetaiils");
				placeList.add(placeDetails);
			}
		} else {

			if (getIntent().getExtras().getSerializable("edittripdetaiils") != null) {
				placeDetails = (PlaceDetails) getIntent().getExtras()
						.getSerializable("edittripdetaiils");
				placeList.add(placeDetails);
			}
		}
			String updates = preference.getString("update", null);
			String updateshared = preference.getString("updateshared", null);
		if(updates!=null){
			updateshare = "no";
		update = "yes";	
		}
		if(updateshared!=null){
			update = "no";
			updateshare = "yes";	
			}
		tripName = preference.getString("tripname", null);
		travelDate = preference.getString("traveldate", null);

		if (getIntent().getExtras().getSerializable("single") != null) {
			SharedPlacedDetails viewDetails = (SharedPlacedDetails) getIntent()
					.getExtras().getSerializable("single");
			tripName = viewDetails.getTripName();
			travelDate = viewDetails.getTraveldate();
			placeList = viewDetails.getPlaceList();
			if(updates!=null){
				updateshare = "no";
			update = "yes";	
			}
			if(updateshared!=null){
				update = "no";
				updateshare = "yes";	
				}

		}
		details.setTripName(tripName);
		details.setTraveldate(travelDate);
		if (placeList.size() > 1) {
			for (int i = 0; i < placeList.size() - 1; i++) {
				Location loc1 = new Location("");
				loc1.setLatitude(placeList.get(i).getLat());
				loc1.setLongitude(placeList.get(i).getLngt());
				Location loc2 = new Location("");
				loc2.setLatitude(placeList.get(i + 1).getLat());
				loc2.setLongitude(placeList.get(i + 1).getLngt());
				float distance = loc1.distanceTo(loc2) / 1609;
				NumberFormat formatter = NumberFormat.getInstance();
				String output = formatter.format(distance);
				placeList.get(i).setDistance(Float.parseFloat(output));
			}
		}
		details.setPlaceList(placeList);
		 adapter = new SingleEditWeatherAdapter(this,
				placeList);
		edittriplist.setAdapter(adapter);
		adapter.setNotifyOnChange(true);
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						getApplicationContext());
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		edittriplist.setMenuCreator(creator);
		edittriplist.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				
				case 0:
					placeList.remove(position);
					adapter.notifyDataSetChanged();
					break;
				}
				return false;
			}
		});
		edittriplist.setOnSwipeListener(new OnSwipeListener() {
			
			@Override
			public void onSwipeStart(int position) {
				// swipe start
			}
			
			@Override
			public void onSwipeEnd(int position) {
				// swipe end
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addtrip:
			preference = getApplicationContext().getSharedPreferences(
					EditTripActivity.MyPREFERENCES, Context.MODE_PRIVATE);
			Editor editor = preference.edit();
			Gson gson = new Gson();
			String json = gson.toJson(details);
			editor.putString("placesList", json);
			editor.commit();
			Intent myIntent = new Intent(EditTripActivity.this,
					SelectPlaceActivity.class);
			startActivity(myIntent);
			finish();
			break;
		case R.id.save:
			saveATrip();
			finish();
			break;

		default:
			break;
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		preference = getApplicationContext()
				.getSharedPreferences(
						EditTripActivity.MyPREFERENCES,
						Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.remove("update").commit();
		editor.remove("updateshared").commit();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.apps_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.logout) {
			
				ParseUser.logOut();
				Intent intent = new Intent(EditTripActivity.this, LoginActivity.class);
				startActivity(intent);
				EditTripActivity.this.finish();
			

		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void saveATrip(){
		if (update.equalsIgnoreCase("yes")) {
			Log.d("save", "insideupdatetrip");
			Gson gson = new Gson();
			final String json = gson.toJson(details);
			ParseQuery<ParseObject> query = ParseQuery
					.getQuery("SavTripList");
			query.whereEqualTo("user", ParseUser.getCurrentUser());
			query.whereEqualTo("tripname", tripName);
			query.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					for (ParseObject p : objects) {
						p.put("savetrip", json);
						p.saveInBackground(new SaveCallback() {
							
							@Override
							public void done(ParseException e) {
								if(e==null){
									preference = getApplicationContext()
											.getSharedPreferences(
													EditTripActivity.MyPREFERENCES,
													Context.MODE_PRIVATE);
									Editor editor = preference.edit();
									editor.clear();
									editor.commit();
									Intent intent = new Intent(EditTripActivity.this,
											TabViewActivity.class);
									startActivity(intent);
									finish();
								}
								
							}
						});
					}

				}
			});
		} else if(updateshare.equalsIgnoreCase("yes")){
			Log.d("save", "insideupdatesharedtrip");
			Gson gson = new Gson();
			final String json = gson.toJson(details);
			ParseQuery<ParseObject> query = ParseQuery
					.getQuery("SharedList");
			query.whereEqualTo("sharedEmailId", ParseUser.getCurrentUser().getEmail());
			query.whereEqualTo("tripname", tripName);
			query.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					for (ParseObject p : objects) {
						p.put("sharedItem", json);
						p.saveInBackground(new SaveCallback() {
							
							@Override
							public void done(ParseException e) {
								if(e==null){
									preference = getApplicationContext()
											.getSharedPreferences(
													EditTripActivity.MyPREFERENCES,
													Context.MODE_PRIVATE);
									Editor editor = preference.edit();
									editor.clear();
									editor.commit();
									Intent intent = new Intent(EditTripActivity.this,
											TabViewActivity.class);
									startActivity(intent);
									finish();
									
								}
								
							}
						});
					}

				}
			});
		}else {
			ParseObject todo = new ParseObject("SavTripList");
			Log.d("save", "insidesavetrip");
			Gson gson = new Gson();
			String json = gson.toJson(details);
			todo.put("savetrip", json);
			todo.put("user", currentUser);
			todo.put("tripname", tripName);
			todo.saveInBackground(new SaveCallback() {

				@Override
				public void done(ParseException e) {
					// TODO Auto-generated method stub
					if (e == null) {
						Log.d("save", "insidesavetripdouble");
						preference = getApplicationContext()
								.getSharedPreferences(
										EditTripActivity.MyPREFERENCES,
										Context.MODE_PRIVATE);
						Editor editor = preference.edit();
						editor.clear();
						editor.commit();
						Intent intent = new Intent(EditTripActivity.this,
								TabViewActivity.class);
						startActivity(intent);
						finish();

					} else {
						Toast.makeText(EditTripActivity.this,
								e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			});
		}

		
	}
	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
	
	 
	
}
