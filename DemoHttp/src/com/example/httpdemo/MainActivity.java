package com.example.httpdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.button1).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (isConnected()) {
							Toast.makeText(MainActivity.this, "Connected",
									Toast.LENGTH_SHORT).show();
							// new
							// GetData().execute("http://rss.cnn.com/rss/cnn_tech.rss");
							// new
							// GetImage().execute("https://www.google.com/images/srpr/logo11w.png");
							// new
							// GetDataHttpClient().execute("http://rss.cnn.com/rss/cnn_tech.rss");
							//new GetImageHttpClient().execute("https://www.google.com/images/srpr/logo11w.png");
							
							RequestParams params = new RequestParams("GET", "http://dev.theappsdr.com/lectures/params.php");
							params.addParam("k1", "v1");
							params.addParam("k2", "v2");
							params.addParam("k3", "v3");
							params.addParam("k4", "v4");
							
							new GetDataWithParams().execute(params);
							
						} else
							Toast.makeText(MainActivity.this, "Not Connected",
									Toast.LENGTH_SHORT).show();

					}
				});
	}

	private boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

	class GetData extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			BufferedReader bufferedReader = null;
			try {
				URL url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.setRequestMethod("GET");
				bufferedReader = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line + "\n");
				}
				return sb.toString();

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null)
				Log.d("HttpDemo", result);
			else
				Log.d("HttpDemo", "Null");
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
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				ImageView iv = (ImageView) findViewById(R.id.imageView1);
				iv.setImageBitmap(result);
			} else
				Log.d("HttpDemo", "Null");
		}

	}

	class GetDataHttpClient extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			AndroidHttpClient client = AndroidHttpClient
					.newInstance("AndroidAgent");
			HttpGet request = new HttpGet(params[0]);

			HttpResponse response;
			try {
				response = client.execute(request);
				return EntityUtils.toString(response.getEntity());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				client.close();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null)
				Log.d("HttpDemo", result);
			else
				Log.d("HttpDemo", "Null");
		}

	}

	class GetImageHttpClient extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			InputStream in = null;
			AndroidHttpClient client = AndroidHttpClient
					.newInstance("AndroidAgent");
			HttpGet request = new HttpGet(params[0]);

			HttpResponse response;
			try {
				response = client.execute(request);
				in = response.getEntity().getContent();
				Bitmap image = BitmapFactory.decodeStream(in);
				return image;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				client.close();
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				ImageView iv = (ImageView) findViewById(R.id.imageView1);
				iv.setImageBitmap(result);
			} else
				Log.d("HttpDemo", "Null");
		}

	}

	class GetDataWithParams extends AsyncTask<RequestParams, Void, String> {

		@Override
		protected String doInBackground(RequestParams... params) {
			BufferedReader bufferedReader = null;
			try {
				
				HttpURLConnection con = params[0].setupConnection();
				bufferedReader = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line + "\n");
				}
				return sb.toString();

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null)
				Log.d("HttpDemo", result);
			else
				Log.d("HttpDemo", "Null");
		}

	}
}
