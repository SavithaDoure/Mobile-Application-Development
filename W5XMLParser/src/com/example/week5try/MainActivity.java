package com.example.week5try;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		GetWeatherAsyncTask.ISetUpData {
	EditText etCity;
	TextView tvTemp, tvHumid, tvPressure, tvWind, tvClouds, tvPrecipitation;
	Switch sMetric;
	Button bSubmit;
	RequestParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etCity = (EditText) findViewById(R.id.editText1);

		tvTemp = (TextView) findViewById(R.id.tvTemperature);
		tvHumid = (TextView) findViewById(R.id.idHumidity);
		tvPressure = (TextView) findViewById(R.id.idPressure);
		tvWind = (TextView) findViewById(R.id.idWind);
		tvClouds = (TextView) findViewById(R.id.idClouds);
		tvPrecipitation = (TextView) findViewById(R.id.idPrecipitation);

		sMetric = (Switch) findViewById(R.id.switch1);
		bSubmit = (Button) findViewById(R.id.button1);

		bSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (etCity.getText().toString().equals("")) {
					Toast.makeText(MainActivity.this, "Enter City, State, US",
							Toast.LENGTH_SHORT).show();
				} else {
					params = new RequestParams("GET",
							"http://api.openweathermap.org/data/2.5/find");
					params.addParam("q", etCity.getText().toString());
					if (sMetric.isChecked())
						params.addParam("units", "metric");
					else
						params.addParam("units", "imperial");
					params.addParam("mode", "xml");
					new GetWeatherAsyncTask(MainActivity.this).execute(params);
				}
			}
		});
	}

	@Override
	public void setUpData(Weather weather) {
		etCity.setText(weather.getName() + "," + weather.getCountry());
		tvTemp.setText("Temperature:  " + weather.getTemperature());
		tvHumid.setText("Humidity:  " + weather.getHumidity());
		tvPressure.setText("Pressure:  " + weather.getPressure());
		tvWind.setText("Wind:  " + weather.getWind());
		tvClouds.setText("Clouds:  " + weather.getClouds());
		tvPrecipitation.setText("Precipitation:  " + weather.getPreciitation());

	}
}
