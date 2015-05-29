package com.example.week4try;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

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
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	int currentImage = 0;
	ImageView rightClick, leftClick;
	ProgressDialog progressDialog;
	RequestParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		params = new RequestParams("GET",
				"http://dev.theappsdr.com/lectures/inclass_http/index.php");
		params.addParam("pid", Integer.toString(currentImage));

		rightClick = (ImageView) findViewById(R.id.imageView3);
		leftClick = (ImageView) findViewById(R.id.imageView2);

		rightClick.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentImage == 19) {
					currentImage = 0;
				} else
					currentImage += 1;
				params = new RequestParams("GET",
						"http://dev.theappsdr.com/lectures/inclass_http/index.php");
				params.addParam("pid", Integer.toString(currentImage));
				download(params);
			}
		});

		leftClick.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (currentImage == 0) {
					currentImage = 19;
				} else
					currentImage -= 1;
				params = new RequestParams("GET",
						"http://dev.theappsdr.com/lectures/inclass_http/index.php");
				params.addParam("pid", Integer.toString(currentImage));
				download(params);
			}
		});

		download(params);
	}

	private void download(RequestParams param) {
		if (isConnected()) {
			new GetDataWithParams().execute(params);
		} else {
			Toast.makeText(MainActivity.this, "No Internet Connected",
					Toast.LENGTH_SHORT).show();
		}
	}

	class GetDataWithParams extends AsyncTask<RequestParams, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(RequestParams... params) {
			InputStream in = null;
			try {

				HttpURLConnection con = params[0].setupConnection();
				in = con.getInputStream();
				Bitmap image = BitmapFactory.decodeStream(in);
				publishProgress();
				return image;

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Loading Image");
			progressDialog.setCancelable(false);
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Bitmap image) {
			progressDialog.dismiss();
			if (image != null)
				((ImageView) findViewById(R.id.imageView1))
						.setImageBitmap(image);
			else
				Log.d("HttpDemo", "Null");
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
}
