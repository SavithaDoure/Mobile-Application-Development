package com.mad.adapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;








import com.example.group1a_hw05.PreviewActivity;
import com.example.group1a_hw05.R;
import com.example.group1a_hw05.R.drawable;
import com.example.group1a_hw05.R.id;
import com.example.group1a_hw05.R.layout;
import com.mad.bean.DailyTemp;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class SingleWeatherAdapter extends ArrayAdapter<DailyTemp> {

	

	Context context;
	ArrayList<DailyTemp> progItems;
	
	public SingleWeatherAdapter(Context context,
			 List<DailyTemp> objects) {
		super(context, R.layout.single_weather, R.id.precipitation, objects);
		this.context=context;
		this.progItems=(ArrayList<DailyTemp>) objects;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.single_weather,
					parent, false);
			holder = new ViewHolder();
			holder.date = (TextView) convertView.findViewById(R.id.date1);
			holder.temp = (TextView) convertView
					.findViewById(R.id.temp1);
			holder.thumbnail = (ImageView) convertView
					.findViewById(R.id.imageView1);
			holder.precipitation = (TextView) convertView
					.findViewById(R.id.precipitation);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		TextView date = holder.date;
		TextView temp = holder.temp;
		TextView precipitation = holder.precipitation;
		ImageView thumbnail = holder.thumbnail;
		long timeStamp = Long.parseLong(progItems.get(position).getDt());
		Date time=new java.util.Date((long)timeStamp*1000);
		String str1 = time.toString().substring(4, 7);
		String str2 = time.toString().substring(8, 10);
		String str3 = time.toString().substring(24, 28);
		String finalDate= str1+"/"+str2+"/"+str3;
		date.setText(finalDate);
		temp.setText(progItems.get(position).getMax());
		precipitation.setText(progItems.get(position).getPrecipitation());
		String icon=progItems.get(position).getIcon();
		String url = "http://openweathermap.org/img/w/"+icon+".png";
		Picasso.with(context).load(url).into(thumbnail);
		//thumbnail.setImageResource(R.drawable.sun);
		return convertView;
	}

	static class ViewHolder {
		TextView date;
		TextView temp;
		TextView precipitation;
		ImageView thumbnail;

	}
}
