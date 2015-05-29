package com.example.group1a_hw05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;
import com.mad.bean.DailyTemp;
import com.mad.bean.Keys;
import com.mad.bean.PlaceDetails;
import com.mad.bean.WeatherDetail;
import com.mad.util.PlaceJSONParser;
import com.squareup.picasso.Picasso;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class PreviewActivity extends Activity implements View.OnClickListener {

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(PreviewActivity.this,
				SelectPlaceActivity.class);
		startActivity(intent);
		finish();
		
	}


ImageView image,weatherSym;
TextView title,temp,openstatus;
TextView address;
String imagLink;
PlaceDetails placeDetails;
double lat;
double lan;
WeatherDetail weatherDetails;
Button addBtn;
SharedPreferences preference;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		preference = getApplicationContext()
				.getSharedPreferences(EditTripActivity.MyPREFERENCES,
						Context.MODE_PRIVATE);
		if(getIntent().getExtras().getSerializable("single")!=null){
			 placeDetails = (PlaceDetails) getIntent().getExtras().getSerializable("single");
			imagLink=placeDetails.getImageUrl();
		}
		
		image = (ImageView) findViewById(R.id.imageView1);
		weatherSym = (ImageView) findViewById(R.id.imageView4);
		title = (TextView) findViewById(R.id.cityname1);
		address = (TextView) findViewById(R.id.address);
		temp = (TextView) findViewById(R.id.temp);
		addBtn = (Button) findViewById(R.id.add);
		openstatus = (TextView) findViewById(R.id.openstatus);
		addBtn.setOnClickListener(this);
		weatherSym.setOnClickListener(this);
			if (imagLink == null || "".equalsIgnoreCase(imagLink)) {
				image.setImageResource(R.drawable.photo_not_found);
			} else{
				String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+imagLink+"&key="+Keys.serverKey;
				Picasso.with(PreviewActivity.this).load(url).into(image);
			}
			title.setText(placeDetails.getPlaceName());
			address.setText(placeDetails.getVicinity());
			if((placeDetails.getOpenstatus()!=null)){
				if((placeDetails.getOpenstatus().equalsIgnoreCase("true"))){
					openstatus.setText("Now Open");	
					openstatus.setTextColor(Color.BLUE);
				}else{
					openstatus.setText("Closed");
					openstatus.setTextColor(Color.RED);
				}
				
				}else{
					openstatus.setText("-NA-");
				}
			lat=  placeDetails.getLat();
			lan= placeDetails.getLngt();
			new WeatherTask().execute("http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lan+"&cnt=10&&units=imperial&mode=jsonc");
		}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageView4:

			Intent intent = new Intent(PreviewActivity.this,WeatherActivity.class);
			intent.putExtra("weather", weatherDetails);
			startActivity(intent);
			break;
		case R.id.add:

			Intent intent1 = new Intent(PreviewActivity.this,EditTripActivity.class);
			intent1.putExtra("edittripdetaiils", placeDetails);
			startActivity(intent1);
			finish();
			break;
		default:
			break;
		}
		
	
	}
	
	class WeatherTask extends AsyncTask<String, Void, WeatherDetail>{
		String data = null;
		JSONObject jObject;
		WeatherDetail places = null;
		@Override
		protected WeatherDetail doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				data = downloadUrl(params[0]);
				PlaceJSONParser placeJsonParser = new PlaceJSONParser();

				try {
					jObject = new JSONObject(data);

					/** Getting the parsed data as a List construct */
					places = placeJsonParser.parseWeather(jObject);

				} catch (Exception e) {
					Log.d("Exception", e.toString());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return places;
		}

		@Override
		protected void onPostExecute(WeatherDetail result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			if(result!=null){
			List<DailyTemp> dailyTempList = result.getDailyTemp();
			String dayTemp = dailyTempList.get(0).getDay();
			temp.setText(dayTemp +" F");
			String icon = dailyTempList.get(0).getIcon();
			String url = "http://openweathermap.org/img/w/"+icon+".png";
			Log.d("url",url);
			Picasso.with(PreviewActivity.this).load(url).into(weatherSym);
			weatherDetails = result;
		}else{
			weatherSym.setImageResource(R.drawable.photo_not_found);
			weatherSym.setClickable(false);
			temp.setText("");
			
		}
		
		}
	}
	
	
	private static String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} 
		return data;

	}

	
	}



