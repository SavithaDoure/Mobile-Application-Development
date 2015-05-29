package com.example.inclass09b;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Group1A_InClass09b: Ashraf Cherukuru, Savitha Doure, Venkatesh kalva
 * 
 * */
public class MainActivity extends Activity {

	GoogleMap gMap;
	AlertDialog alertDialog;
	String location;
	Address fetchedAddress;
	double latitude, longitude;
	List<ParseGeoPoint> geoPointsList;
	List<String> nameList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Parse.initialize(this, "o5hvWIXPl0iYxLty9meLyhzoQ4XBlE7Rwz9X85GS",
				"0CfuSfArKp0YYt3DShN9sYerQiuOENEgV11ATqO1");
		ParseUser.enableAutomaticUser();

		gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.idMap))
				.getMap();

		gMap.setMyLocationEnabled(true);

	}

	public void alert() {
		LayoutInflater li = LayoutInflater.from(this);
		View promptsView = li.inflate(R.layout.alert_layout, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(promptsView);
		final EditText etLocation = (EditText) promptsView
				.findViewById(R.id.editTextToDoItem);

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setTitle("Add an Item")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// get user input and set it to result
						// edit text
						location = etLocation.getText().toString();

						if (location.equals("")) {
							Toast.makeText(MainActivity.this,
									"Enter a location", Toast.LENGTH_SHORT)
									.show();
						} else {
							gMap.clear();
							new GeoTask(MainActivity.this).execute(location);
						}
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		// create alert dialog
		alertDialog = alertDialogBuilder.create();
		// show it

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.showfoodplaces, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) { //
			alert();
			if (alertDialog != null) {
				alertDialog.show();
				return true;
			}
		}

		return super.onOptionsItemSelected(item);
	}

	class GeoTask extends AsyncTask<String, Void, List<Address>> {
		Context mContext;

		public GeoTask(Context context) {
			this.mContext = context;
		}

		@Override
		protected List<Address> doInBackground(String... params) {
			List<Address> addressList = null;

			Geocoder geoCoder = new Geocoder(mContext);

			try {
				Log.d("Inclass9b", "In try");
				addressList = geoCoder.getFromLocationName(params[0], 1);

			} catch (IOException e) {
				Log.d("InClass9b", e.toString());
				e.printStackTrace();
			}

			return addressList;
		}

		@Override
		protected void onPostExecute(List<Address> result) {
			if (result == null) {
				Log.d("Inclass9b", "Result null");
			} else {
				Log.d("Inclass9b", "Size: " + result.size());
				if (result.size() > 0) {
					fetchedAddress = result.get(0);
					fetchLatLng();
				} else
					fetchedAddress = null;
			}
		}
	}

	void fetchLatLng() {
		if (fetchedAddress != null) {
			latitude = fetchedAddress.getLatitude();
			longitude = fetchedAddress.getLongitude();

			ParseQuery<ParseObject> query = ParseQuery.getQuery("FoodPlaces");
			query.whereWithinMiles("location", new ParseGeoPoint(latitude,
					longitude), 50);
			query.findInBackground(new FindCallback<ParseObject>() {
				@Override
				public void done(List<ParseObject> objectList, ParseException e) {
					if (e == null) {
						Log.d("Inclass9b", "Retrieved " + objectList.size());
						geoPointsList = new ArrayList<ParseGeoPoint>();
						nameList = new ArrayList<String>();
						for (ParseObject object : objectList) {
							geoPointsList.add(object
									.getParseGeoPoint("location"));
							nameList.add(object.getString("name"));
						}
						setMap();
						Log.d("Inclass9b", "Objects " + nameList.toString());

					} else {
						Log.d("Inclass9b", "Error: " + e.getMessage());
					}
				}
			});
		}

	}

	void setMap() {
		if (geoPointsList != null && nameList != null
				&& geoPointsList.size() > 0 && nameList.size() > 0) {
			gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					geoPointsList.get(0).getLatitude(), geoPointsList.get(0)
							.getLongitude()), 9));
			for (int i = 0; i < geoPointsList.size(); i++) {
				gMap.addMarker(new MarkerOptions().position(
						new LatLng(geoPointsList.get(i).getLatitude(),
								geoPointsList.get(i).getLongitude())).title(
						nameList.get(i)));

			}
		}

	}

}
