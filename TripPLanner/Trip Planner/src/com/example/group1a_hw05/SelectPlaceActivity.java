package com.example.group1a_hw05;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mad.bean.SelectPlace;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class SelectPlaceActivity extends Activity 
		 {
	ListView selectPlaceList;
	public static final String SELECTED_PLACE = "selectedplace";
	List<SelectPlace> placeslist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_place);
		selectPlaceList = (ListView) findViewById(R.id.selectplacelistView1);
		placeslist = new ArrayList<SelectPlace>();
		SelectPlace place1 = new SelectPlace();
		place1.setIcon(R.drawable.ic_restaurant_menu_64);
		place1.setIconId("restaurant");
		place1.setIconname("Restaurant");
		
		SelectPlace place2 = new SelectPlace();
		place2.setIcon(R.drawable.ic_local_airport_64);
		place2.setIconname("Airport");
		place2.setIconId("airport");
		
		SelectPlace place3 = new SelectPlace();
		place3.setIcon(R.drawable.ic_local_car_wash_64);
		place3.setIconname("Car Repair");
		place3.setIconId("car_repair");
		
		SelectPlace place4 = new SelectPlace();
		place4.setIcon(R.drawable.ic_local_bar_64);
		place4.setIconname("Bar");
		place4.setIconId("bar");
		
		SelectPlace place5 = new SelectPlace();
		place5.setIcon(R.drawable.ic_local_gas_station_64);
		place5.setIconname("Gas Station");
		place5.setIconId("gas_station");
		
		SelectPlace place6 = new SelectPlace();
		place6.setIcon(R.drawable.ic_home_64);
		place6.setIconname("Lodging");
		place6.setIconId("Lodging");
		
		SelectPlace place7 = new SelectPlace();
		place7.setIcon(R.drawable.ic_movie_64);
		place7.setIconname("Movies");
		place7.setIconId("movie_theater");
		
		SelectPlace place8 = new SelectPlace();
		place8.setIcon(R.drawable.ic_shopping_cart_64);
		place8.setIconname("Shopping Mall");
		place8.setIconId("shopping_mall");
		
		placeslist.add(place1);
		placeslist.add(place2);
		placeslist.add(place3);
		placeslist.add(place4);
		placeslist.add(place5);
		placeslist.add(place6);
		placeslist.add(place7);
		placeslist.add(place8);
		
		SelectPlaceAdapter adapter = new SelectPlaceAdapter(this, placeslist);
		selectPlaceList.setAdapter(adapter);
		adapter.setNotifyOnChange(true);
		selectPlaceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SelectPlace singleItem = placeslist.get(position);

				Intent intent = new Intent(SelectPlaceActivity.this,
						PlaceDetailsActivity.class);
				intent.putExtra(SELECTED_PLACE, singleItem.getIconId());
				Log.d("iconId", singleItem.getIconId());
				startActivity(intent);
				finish();
				
			}
		});
		
	}

	
}
