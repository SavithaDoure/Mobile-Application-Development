package com.example.inclass05;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

/*
 * InClass05
 * Group1A: Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva 
 * 
 * References:
 * 		Lecture Videos on Moodle
 * */

public class MainActivity extends Activity implements View.OnClickListener {

	EditText etKeyword;
	Button bGo;
	ImageView ivImage, ivPrev, ivNext;
	Switch sParser;
	static String parser = "Pull";
	RequestParams params;
	ProgressDialog progressDialog;
	int current, prev, next;
	ArrayList<String> imagesList;
	String keyword = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etKeyword = (EditText) findViewById(R.id.editText1);
		bGo = (Button) findViewById(R.id.button1);
		ivImage = (ImageView) findViewById(R.id.imageView1);
		ivPrev = (ImageView) findViewById(R.id.imageView2);
		ivNext = (ImageView) findViewById(R.id.imageView3);
		sParser = (Switch) findViewById(R.id.switch1);

		sParser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sParser.isChecked()) {
					parser = "SAX";
				} else
					parser = "Pull";
			}
		});
		ivPrev.setOnClickListener(null);
		ivNext.setOnClickListener(null);
		bGo.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (isConnected()) {
			if (v.getId() == R.id.imageView2) {
				if (imagesList.size() <= 0)
					Toast.makeText(this, "No images to display",
							Toast.LENGTH_SHORT).show();
				else if (current == 0)
					current = imagesList.size() - 1;
				else
					current--;
				new GetImage().execute(imagesList.get(current));
			} else if (v.getId() == R.id.imageView3) {
				if (current == imagesList.size() - 1)
					current = 0;
				else
					current++;
				new GetImage().execute(imagesList.get(current));
			} else if (v.getId() == R.id.button1) {
				if (etKeyword.getText() != null) {
					keyword = etKeyword.getText().toString();
				}
				params = new RequestParams("GET",
						"https://api.flickr.com/services/rest/?");
				params.addParam(getResources().getString(R.string.method),
						"flickr.photos.search");
				params.addParam(getResources().getString(R.string.api_key),
						"88f39025688c5a0d99006cddebf80f70");
				params.addParam(getResources().getString(R.string.text),
						keyword);
				params.addParam(getResources().getString(R.string.extras),
						"url_m");
				params.addParam(getResources().getString(R.string.per_page),
						"20");
				params.addParam(getResources().getString(R.string.format),
						"rest");
				new ParserImages().execute(params);
			}

		} else {
			Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT)
					.show();
		}

	}

	private boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	class ParserImages extends
			AsyncTask<RequestParams, Void, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(RequestParams... params) {

			try {
				HttpURLConnection con = params[0].setupConnection();
				con.setRequestMethod("GET");
				con.connect();
				int statusCode = con.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {
					InputStream in = con.getInputStream();
					publishProgress();
					if (parser.equals("SAX")) {
						return ParserUtils.SAXParser.parseForImageSAX(in);
					} else
						return ParserUtils.PullParser.parseForImagePull(in);
				}
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}

		@Override
		protected void onPreExecute() {
			Log.d("inclass05", parser);
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Loading Photo");
			progressDialog.setCancelable(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			progressDialog.show();
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			progressDialog.dismiss();
			Log.d("InClass05", result.toString());
			imagesList = result;
			current = 0;
			if (imagesList.size() <= 0)
				Toast.makeText(MainActivity.this, "No images to display",
						Toast.LENGTH_SHORT).show();
			else {
				ivPrev.setOnClickListener(MainActivity.this);
				ivNext.setOnClickListener(MainActivity.this);
				new GetImage().execute(result.get(current));
			}

			super.onPostExecute(result);
		}
	}

	class GetImage extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... params) {
			InputStream in = null;
			try {
				URL url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setRequestMethod("GET");
				in = con.getInputStream();
				Bitmap image = BitmapFactory.decodeStream(in);
				publishProgress();
				return image;

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			Log.d("inclass05", "Download Image");
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Loading Photo");
			progressDialog.setCancelable(false);
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			progressDialog.show();
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			progressDialog.dismiss();
			if (result != null) {
				ivImage.setImageBitmap(result);
			} else
				Log.d("HttpDemo", "Null");
		}
	}

}
