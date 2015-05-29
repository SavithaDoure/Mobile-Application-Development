package com.example.group1a_hw05;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.adapter.SingleItemAdapter;
import com.mad.bean.PlaceDetails;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class PlaceDetailsActivity extends Activity implements
		View.OnClickListener, BackgroundTask.senddata {
	Button enter;
	EditText cityname;
	LocationManager locManager;
	LocationListener mlistner;
	public static double lat;
	public static double lng;
	public static List<PlaceDetails> listOfplaces;
	ListView mailListView;
	List<PlaceDetails> totalItemList;
	Context context;
	Location currentLocation;
	String currentLoc;
	View view;
	String selectedPlace;
	RelativeLayout parentLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_details);
		view = findViewById(R.layout.activity_place_details);
		GPSTracker mGPS = new GPSTracker(this);
		if (mGPS.canGetLocation) {
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			currentLocation = mGPS.getLocation();
			if(currentLocation!=null){
			List<Address> addresses = null;
			try {
				addresses = geocoder.getFromLocation(
						currentLocation.getLatitude(),
						currentLocation.getLongitude(), 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (addresses.size() > 0) {
				currentLoc = addresses.get(0).getAddressLine(0);
				Log.d("currentLocation", currentLoc);
			}
			}
			Log.d("gpslocation",
					"Lat" + mGPS.getLatitude() + "Lon" + mGPS.getLongitude());
		} else {
			Log.d("gpslocation", "didnt find");
		}

		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mailListView = (ListView) findViewById(R.id.listView1);
		enter = (Button) findViewById(R.id.enter);
		cityname = (EditText) findViewById(R.id.cityname);
		enter.setOnClickListener(this);
		context = this;
		if (getIntent().getExtras().getString(
				SelectPlaceActivity.SELECTED_PLACE) != null) {
			selectedPlace = getIntent().getExtras().getString(
					SelectPlaceActivity.SELECTED_PLACE);
		}
		if (currentLoc != null) {
			new BackgroundTask.GeoTask(PlaceDetailsActivity.this,
					PlaceDetailsActivity.this, selectedPlace)
					.execute(currentLoc);
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		GPSTracker mGPS = new GPSTracker(this);
		if (mGPS.canGetLocation) {
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			currentLocation = mGPS.getLocation();
			if(currentLocation!=null){
				List<Address> addresses = null;
				try {
					addresses = geocoder.getFromLocation(
							currentLocation.getLatitude(),
							currentLocation.getLongitude(), 1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (addresses.size() > 0) {
					currentLoc = addresses.get(0).getAddressLine(0);
					Log.d("currentLocation", currentLoc);
				}
				}
			Log.d("gpslocation",
					"Lat" + mGPS.getLatitude() + "Lon" + mGPS.getLongitude());
		} else {
			Log.d("gpslocation", "didnt find");
		}
		if (currentLoc != null) {
			new BackgroundTask.GeoTask(PlaceDetailsActivity.this,
					PlaceDetailsActivity.this, selectedPlace)
					.execute(currentLoc);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(PlaceDetailsActivity.this,
				SelectPlaceActivity.class);
		startActivity(intent);
		finish();
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.enter:
			String cityName = cityname.getText().toString();
			new BackgroundTask.GeoTask(PlaceDetailsActivity.this,
					PlaceDetailsActivity.this, selectedPlace).execute(cityName);
			break;

		default:
			break;
		}

	}

	@Override
	public void setDetails(SingleItemAdapter adapter,
			List<PlaceDetails> itemList) {
		Log.d("list", itemList.toString());
		if (itemList.size() > 0 && itemList != null) {
			totalItemList = itemList;
			mailListView.setAdapter(adapter);
			mailListView
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							PlaceDetails singleItem = totalItemList
									.get(position);

							Intent intent = new Intent(
									PlaceDetailsActivity.this,
									PreviewActivity.class);
							intent.putExtra("single", singleItem);
							startActivity(intent);
							finish();

						}
					});
		} else {
			Toast.makeText(context, "No Results found for this search", Toast.LENGTH_SHORT).show();
		}
	}

	public String getLocationName(double lattitude, double longitude) {

		String cityName = "Not Found";
		Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
		try {

			List<Address> addresses = gcd.getFromLocation(lattitude, longitude,
					10);

			for (Address adrs : addresses) {
				if (adrs != null) {

					String city = adrs.getLocality();
					if (city != null && !city.equals("")) {
						cityName = city;
						System.out.println("city ::  " + cityName);
					} else {

					}
					// // you should also try with addresses.get(0).toSring();

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cityName;

	}

}
