package com.example.w7sqlite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class NewsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		if (getIntent().getExtras() != null) {
			String url = getIntent().getExtras().getString("url");
			Log.d("W7SQL", url);
			new GetNewsFeed().execute(url);
		}
	}

	public class GetNewsFeed extends AsyncTask<String, Void, ArrayList<String>> {
		BufferedReader bufferedReader = null;

		@Override
		protected void onPreExecute() {
			Log.d("W7SQL", "OnPreExecute");
			super.onPreExecute();
		}

		@Override
		protected ArrayList<String> doInBackground(String... params) {
			URL url;
			try {
				url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setRequestMethod("GET");
				bufferedReader = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line + "\n");
				}
				Log.d("W7SQL", sb.toString());
				return null;
			} catch (MalformedURLException e) {
				Log.d("W7SQL", "MalformedURLException");
				e.printStackTrace();
			} catch (IOException e) {
				Log.d("W7SQL", "IOException");
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			Log.d("W7SQL", "OnPostExecute");
			super.onPostExecute(result);
		}

	}
}
