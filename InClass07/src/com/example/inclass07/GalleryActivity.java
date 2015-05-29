package com.example.inclass07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONException;

import com.example.inclass06.R;
import com.example.inclass07.GalleryActivity.GalleryAdapter.ViewHolder;
import com.example.inclass07.db.DAOManager;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class GalleryActivity extends Activity {
	static final String GALLERY_OBJ = "gallery";
	String keyword;
	RequestParams params;
	ListView lvgallery;
	ArrayList<Photo> galleryList;
	ProgressDialog progressDialog;
	static DAOManager dm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		dm = new DAOManager(this);
		lvgallery = (ListView) findViewById(R.id.idGallery);
		if (getIntent().getExtras() != null) {
			keyword = getIntent().getExtras().getString(MainActivity.KEYWORD);
			params = new RequestParams("GET",
					"https://api.500px.com/v1/photos/search?");
			params.addParam("consumer_key",
					"sL2cwEF7Eu3nBhlTYEsBAZXIwMWjDEz8s2fOE0aW");
			params.addParam("term", keyword);
			params.addParam("image_size", "4");
			params.addParam("rpp", "50");
			new GetGalleryAsyncTask().execute(params);
		}

		lvgallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("inclass", "Photo object: " + galleryList.get(position));
				dm.savePhoto(galleryList.get(position));
				Photo dbPhoto = dm.getPhoto(galleryList.get(position));
				Log.d("Inclass", dbPhoto.toString());

				Intent detailsIntent = new Intent(GalleryActivity.this,
						DetailsActivity.class);
				detailsIntent.putExtra(GALLERY_OBJ, galleryList.get(position));
				startActivity(detailsIntent);

			}
		});

		lvgallery
				.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						ImageView ivFav = (ImageView) view
								.findViewById(R.id.imageView2);
						Photo photo = null;
						photo = dm.getPhoto(galleryList.get(position));
						if (photo != null) {
							dm.deletePhoto(galleryList.get(position));
							ivFav.setImageResource(R.drawable.favgrey);
						} else {
							dm.savePhoto(galleryList.get(position));
							ivFav.setImageResource(R.drawable.favyellow);
						}
						return false;
					}
				});
	}

	class GetGalleryAsyncTask extends
			AsyncTask<RequestParams, Void, ArrayList<Photo>> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = new ProgressDialog(GalleryActivity.this);
			progressDialog.setMessage("Loading Results...");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected ArrayList<Photo> doInBackground(RequestParams... params) {
			BufferedReader bufferedReader = null;
			StringBuilder sb;
			URL url;
			try {
				Log.d("InClass06", "In try");
				HttpURLConnection con = params[0].setupConnection();
				int status = con.getResponseCode();
				if (status == HttpURLConnection.HTTP_OK) {
					Log.d("InClass06", "status ok");
					bufferedReader = new BufferedReader(new InputStreamReader(
							con.getInputStream()));
					sb = new StringBuilder();
					String line = "";
					while ((line = bufferedReader.readLine()) != null) {
						sb.append(line);
					}
					Log.d("InClass06", sb.toString());

					return JSONParser.GalleryJSONParser.getGallery(sb
							.toString());

				}
			} catch (MalformedURLException e) {
				Log.d("InClass06", "MalformedURLException");
				e.printStackTrace();
			} catch (IOException e) {
				Log.d("InClass06", "IOException");
				e.printStackTrace();
			} catch (JSONException e) {
				Log.d("InClass06", "JSONException");
				e.printStackTrace();
			}

			return null;

		}

		@Override
		protected void onPostExecute(ArrayList<Photo> result) {
			progressDialog.dismiss();
			if (result != null) {
				galleryList = result;
				Log.d("InClass06", galleryList.toString());
				/*
				 * ArrayAdapter<Photo> adapter = new ArrayAdapter<Photo>(
				 * GalleryActivity.this, android.R.layout.simple_list_item_1,
				 * galleryList); lvgallery.setAdapter(adapter);
				 */

				GalleryAdapter customAdapter = new GalleryAdapter(
						GalleryActivity.this, R.layout.row_list_activity,
						galleryList);
				lvgallery.setAdapter(customAdapter);
			} else
				Log.d("InClass06", "Null :  post execute");

		}

	}

	static class GalleryAdapter extends ArrayAdapter<Photo> {
		Context context;
		ArrayList<Photo> photos;

		public GalleryAdapter(Context context, int resource,
				ArrayList<Photo> objects) {
			super(context, R.layout.row_list_activity, objects);
			this.context = context;
			this.photos = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.row_list_activity,
						parent, false);
				holder = new ViewHolder();
				holder.textView = (TextView) convertView
						.findViewById(R.id.textView1);
				holder.photo = (ImageView) convertView
						.findViewById(R.id.imageView1);
				// holder.favorite = (ImageView) convertView.findViewById(R.)
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			TextView textView = holder.textView;
			ImageView photo = holder.photo;
			ImageView favorite = holder.favorite;
			textView.setText(photos.get(position).getName());
			Picasso.with(context).load(photos.get(position).getImageUrl())
					.into(photo);
			return convertView;
		}

		static class ViewHolder {
			TextView textView;
			ImageView photo, favorite;
		}
	}
}
