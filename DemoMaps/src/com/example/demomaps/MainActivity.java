package com.example.demomaps;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	GoogleMap gMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		// gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		gMap.setMyLocationEnabled(true);

		// gMap.animateCamera(CameraUpdateFactory.newLatLng(new
		// LatLng(35.2270869,-80.8431267)));

		gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				35.2270869, -80.8431267), 13));

		gMap.addMarker(new MarkerOptions()
		.position(new LatLng(35.2270869, -80.8431267))
		.title("This is Charlotte"));

		// Instantiates a new Polyline object and adds points to define a rectangle
		PolylineOptions rectOptions = new PolylineOptions()
		        .add(new LatLng(35.2270869, -80.8431267))
		        .add(new LatLng(35.5270869, -80.8431267)); // Closes the polyline.

		// Get back the mutable Polyline
		Polyline polyline = gMap.addPolyline(rectOptions);
		
		gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

			@Override
			public void onMapLongClick(LatLng porsition) {
				gMap.addMarker(new MarkerOptions().position(porsition));

			}
		});
	}
}
