package com.example.group1a_hw05;

import com.mad.adapter.SingleWeatherAdapter;
import com.mad.bean.WeatherDetail;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class WeatherActivity extends Activity {
TextView cityname;
TextView tempvalue;
TextView humidity;
TextView wind;
ImageView image;
WeatherDetail weatherDetails;
String cityName,tempValue,humid,windval;
ListView weatherList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather);
		if(getIntent().getExtras().getSerializable("weather")!=null){
			weatherDetails = (WeatherDetail) getIntent().getExtras().getSerializable("weather");
			
		}
		cityname = (TextView) findViewById(R.id.cityname1);
		tempvalue = (TextView) findViewById(R.id.tempdeg);
		humidity = (TextView) findViewById(R.id.addres);
		wind = (TextView) findViewById(R.id.openstatus);
		image = (ImageView) findViewById(R.id.imageView1);
		weatherList = (ListView) findViewById(R.id.listView1);
		cityname.setText(weatherDetails.getCityName());
		tempvalue.setText(weatherDetails.getDailyTemp().get(0).getDay() +" F");
		humidity.setText(weatherDetails.getDailyTemp().get(0).getHumidity());
		wind.setText(weatherDetails.getDailyTemp().get(0).getSpeed());
		String icon=weatherDetails.getDailyTemp().get(0).getIcon();
		String url = "http://openweathermap.org/img/w/"+icon+".png";
		Picasso.with(this).load(url).into(image);
		SingleWeatherAdapter adapter = new SingleWeatherAdapter(this
				, weatherDetails.getDailyTemp());
		weatherList.setAdapter(adapter);
		
		
	}
	@Override
	public void onBackPressed() {
		finish();
	}
	
	
	
}
