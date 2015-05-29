package com.example.listviewdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	String[] colors = { "Red", "Voilet", "Indigo", "Blue", "Green", "Black",
			"Yellow", "White" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ListView listView = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, colors);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("ListViewDemo", "Position: " + position + "Value: "
						+ colors[position]);

			}
		});

		ListView customlistView = (ListView) findViewById(R.id.listView2);
		ColorAdapter customAdapter = new ColorAdapter(this,
				R.layout.item_row_layout, colors);
		customlistView.setAdapter(customAdapter);

	}

	static class ColorAdapter extends ArrayAdapter<String> {
		Context context;
		String[] colors;

		public ColorAdapter(Context context, int resource, String[] objects) {
			super(context, R.layout.item_row_layout, objects);
			this.context = context;
			this.colors = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.item_row_layout,
						parent, false);
				holder = new ViewHolder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.textView1);
				holder.button = (Button) convertView.findViewById(R.id.button1);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			TextView textView = holder.textView;
			Button button = holder.button;
			textView.setText(colors[position]);
			button.setText(colors[position]);
			return convertView;
		}

		static class ViewHolder {
			TextView textView;
			Button button;
		}
	}
}
