package com.example.jsondemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (isConnected()) {
			Log.d("JSONDemo", "is Connected");
			new GetPersonAsyncTask()
					.execute("http://liisp.uncc.edu/~mshehab/api/json/persons-v1.json");
		} else
			Log.d("JSONDemo", "Not Connected");
	}

	class GetPersonAsyncTask extends AsyncTask<String, Void, ArrayList<Person>> {

		@Override
		protected ArrayList<Person> doInBackground(String... params) {
			BufferedReader bufferedReader = null;
			StringBuilder sb;
			URL url;
			try {
				Log.d("JSONDemo", "In try");
				url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setRequestMethod("GET");
				con.connect();
				Log.d("JSONDemo", "before status");
				int status = con.getResponseCode();
				Log.d("JSONDemo", "status: " + status);
				if (status == HttpURLConnection.HTTP_OK) {
					Log.d("JSONDemo", "status ok");
					bufferedReader = new BufferedReader(new InputStreamReader(
							con.getInputStream()));
					sb = new StringBuilder();
					String line = "";
					while ((line = bufferedReader.readLine()) != null) {
						sb.append(line);
					}

					return PersonUtils.PersonJSONParser.getPersons(sb
							.toString());
				}
			} catch (MalformedURLException e) {
				Log.d("JSONDemo", "MalformedURLException");
				e.printStackTrace();
			} catch (IOException e) {
				Log.d("JSONDemo", "IOException");
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(ArrayList<Person> personList) {
			if (personList != null)
				Log.d("JSONDemo", personList.toString());
			else
				Log.d("JSONDemo", "Null :  post execute");
		}

	}

	private boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}
}
