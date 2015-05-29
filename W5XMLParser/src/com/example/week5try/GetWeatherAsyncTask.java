package com.example.week5try;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.util.Log;

public class GetWeatherAsyncTask extends
		AsyncTask<RequestParams, Void, Weather> {
	ISetUpData activity;

	public GetWeatherAsyncTask(ISetUpData activity) {
		this.activity = activity;
	}

	@Override
	protected Weather doInBackground(RequestParams... params) {
		try {
			HttpURLConnection con = params[0].setupConnection();
			con.setRequestMethod("GET");
			con.connect();
			int statusCode = con.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {
				InputStream in = con.getInputStream();
				return WeatherUtils.WeatherSAXParser.parseWeather(in);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Weather result) {
		if (null != result) {
			Log.d("demo", result.toString());
			activity.setUpData(result);
		}
	}

	static public interface ISetUpData {
		public void setUpData(Weather weather);
	}

}
