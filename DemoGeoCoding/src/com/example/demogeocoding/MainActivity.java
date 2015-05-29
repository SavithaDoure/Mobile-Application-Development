package com.example.demogeocoding;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (Geocoder.isPresent()) {
			new GeoTask(this).execute("SFO");
		} else {
			Toast.makeText(this, "No GeoCoder", Toast.LENGTH_SHORT).show();
		}
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
				addressList = geoCoder.getFromLocationName(params[0], 10);
				addressList = geoCoder.getFromLocation(35.2270869, -80.8431267,
						10);
			} catch (IOException e) {
				Log.d("DemoGeoCoder:Exception", e.toString());
				e.printStackTrace();
			}

			return addressList;
		}

		@Override
		protected void onPostExecute(List<Address> result) {
			if (result == null) {
				Log.d("DemoGeoCoder", "Result null");
			} else {
				Log.d("DemoGeoCoder", result.toString());
			}
		}

	}
}
