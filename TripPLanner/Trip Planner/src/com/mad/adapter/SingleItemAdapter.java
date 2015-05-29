package com.mad.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.group1a_hw05.R;
import com.example.group1a_hw05.R.drawable;
import com.example.group1a_hw05.R.id;
import com.example.group1a_hw05.R.layout;
import com.mad.bean.Keys;
import com.mad.bean.PlaceDetails;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class SingleItemAdapter extends ArrayAdapter<PlaceDetails> {
	

	Context context;
	ArrayList<PlaceDetails> progItems;
	Address fechedaddress;
	ImageView thumbnail;
	public SingleItemAdapter(Context context,
			 List<PlaceDetails> objects,Address address) {
		super(context, R.layout.activity_single_item, R.id.cityname1, objects);
		this.context=context;
		this.progItems=(ArrayList<PlaceDetails>) objects;
		this.fechedaddress = address;
		// TODO Auto-generated constructor stub
	}




	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_single_item,
					parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.cityname1);
			holder.address = (TextView) convertView
					.findViewById(R.id.addres);
			holder.thumbnail = (ImageView) convertView
					.findViewById(R.id.imageView1);
			holder.openstatus = (TextView) convertView
					.findViewById(R.id.openstatus1);
			holder.distance = (TextView) convertView.findViewById(R.id.distance);
			convertView.setTag(holder);

		}

		holder = (ViewHolder) convertView.getTag();
		TextView title = holder.title;
		TextView address = holder.address;
		TextView openStatus = holder.openstatus;
		 thumbnail = holder.thumbnail;
		TextView disatnce = holder.distance;
		title.setText(progItems.get(position).getPlaceName());
		title.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_Medium);
		address.setText(progItems.get(position).getVicinity());
		address.setTextColor(Color.GRAY);
		if((progItems.get(position).getOpenstatus()!=null)){
		if((progItems.get(position).getOpenstatus().equalsIgnoreCase("true"))){
		openStatus.setText("Now Open");	
		openStatus.setTextColor(Color.BLUE);
		}else{
			openStatus.setText("Closed");
			openStatus.setTextColor(Color.RED);
		}
		
		}else{
			openStatus.setText("-NA-");
		}
		
		Location loc1 = new Location("");
		loc1.setLatitude(fechedaddress.getLatitude());
		loc1.setLongitude(fechedaddress.getLongitude());
		Location loc2 = new Location("");
		loc2.setLatitude(progItems.get(position).getLat());
		loc2.setLongitude(progItems.get(position).getLngt());
		float distance = loc1.distanceTo(loc2) / 1609;
		NumberFormat formatter = NumberFormat.getInstance();
		String output = formatter.format(distance);
		disatnce.setTextColor(Color.GRAY);
		disatnce.setText(output +" mi");
		
		String imgLink = progItems.get(position).getImageUrl();
		if(imgLink!=null){
		String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+imgLink+"&key="+Keys.serverKey;
		Picasso.with(context).load(url).into(thumbnail);
		}else{
			thumbnail.setImageResource(R.drawable.photo_not_found);
		}
		
		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView address;
		TextView openstatus,distance;
		ImageView thumbnail;
	}
	
	 class GetImage extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			String data = null;
			try {
				data = downloadUrl(params[0]);
				Log.d("data", data);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		
		}

		@Override
		protected void onPostExecute(String result) {
			Picasso.with(context).load(result).into(thumbnail);
		}
		
	}
	
	private static String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			int statusCode = urlConnection.getResponseCode();
			if(statusCode==HttpURLConnection.HTTP_OK){
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

		}
		}catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}

		return data;

	}
}
