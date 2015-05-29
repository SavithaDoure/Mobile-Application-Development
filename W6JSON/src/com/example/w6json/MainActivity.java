package com.example.w6json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	CountDownTimer ct;
	int current = 0;
	ArrayList<Application> applicationList;
	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView) findViewById(R.id.textView1);
		ct = new CountDownTimer(3000, 1000) {
			public void onTick(long millisUntilFinished) {

			}

			public void onFinish() {
				current++;
				setUpData();
			}
		};
		new GetApplicationAsyncTask()
				.execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=200/json");

	}

	class GetApplicationAsyncTask extends
			AsyncTask<String, Void, ArrayList<Application>> {

		@Override
		protected ArrayList<Application> doInBackground(String... params) {
			BufferedReader bufferedReader = null;
			StringBuilder sb;
			URL url;
			try {
				Log.d("W6JSON", "In try");
				url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setRequestMethod("GET");
				con.connect();
				Log.d("W6JSON", "before status");
				int status = con.getResponseCode();
				if (status == HttpURLConnection.HTTP_OK) {
					Log.d("W6JSON", "status ok");
					bufferedReader = new BufferedReader(new InputStreamReader(
							con.getInputStream()));
					sb = new StringBuilder();
					String line = "";
					while ((line = bufferedReader.readLine()) != null) {
						sb.append(line);
					}
					return JSONParser.ApplicationJSONParser.getApplications(sb
							.toString());
				}
			} catch (MalformedURLException e) {
				Log.d("W6JSON", "MalformedURLException");
				e.printStackTrace();
			} catch (IOException e) {
				Log.d("W6JSON", "IOException");
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPostExecute(ArrayList<Application> result) {
			if (result != null) {
				Collections.sort(result);
				applicationList = result;
				Log.d("W6JSON", applicationList.toString());
				setUpData();
			} else
				Log.d("W6JSON", "Null :  post execute");
		}

	}

	void setUpData() {
		textView.setText(applicationList.get(current).getTitle() + ":"
				+ applicationList.get(current).getPrice());
		ct.start();
	}
}
