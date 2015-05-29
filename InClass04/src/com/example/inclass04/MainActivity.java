package com.example.inclass04;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/*
 **Group1A_InClass04
 *
 *Team: 
 *		Savitha Doure; Ashraf Cherukuru; Venkatesh Kalva 
 *
 */
public class MainActivity extends Activity {

	SeekBar sb;
	TextView tvTime, tvAverage;
	int complexity = 0;
	double sum = 0;
	ProgressDialog progressDialog, pDThread;
	Button bthread, bAsync;
	Handler handler;
	ExecutorService threadPool;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sb = (SeekBar) findViewById(R.id.seekBar1);
		complexity = sb.getProgress() + 1;
		tvTime = (TextView) findViewById(R.id.idtvTimes);
		tvTime.setText(complexity + " Times");
		tvAverage = (TextView) findViewById(R.id.idtvAverage);
		bthread = (Button) findViewById(R.id.button2);
		bAsync = (Button) findViewById(R.id.button1);

		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

				complexity = progress + 1;
				tvTime.setText(complexity + " Times");

			}
		});

		bAsync.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new ComputeAverage().execute();

			}
		});

		bthread.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				threadPool = Executors.newFixedThreadPool(2);
				threadPool.execute(new ComputeAvgThread());

			}
		});

		handler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case ComputeAvgThread.STATUS_START:
					pDThread = new ProgressDialog(MainActivity.this);
					pDThread.setMessage("Retrieving the number..");
					pDThread.setMax(complexity);
					pDThread.setCancelable(false);
					pDThread.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					pDThread.show();
					break;
				case ComputeAvgThread.STATUS_STEP:
					pDThread.setProgress((Integer) msg.obj);
					break;
				case ComputeAvgThread.STATUS_DONE:
					pDThread.dismiss();
					tvAverage.setText(((Double) msg.obj).toString());
					break;
				}
				return false;
			}
		});
	}

	class ComputeAverage extends AsyncTask<Void, Integer, Double> {
		HeavyWork hv;

		@Override
		protected Double doInBackground(Void... params) {
			for (int i = 0; i < complexity; i++) {
				hv = new HeavyWork();
				sum += hv.getNumber();
				publishProgress(i + 1);
			}
			return sum / complexity;
		}

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(MainActivity.this);
			progressDialog.setMessage("Retrieving the number..");
			progressDialog.setMax(complexity);
			progressDialog.setCancelable(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.show();
		}

		@Override
		protected void onPostExecute(Double result) {
			progressDialog.dismiss();
			tvAverage.setText(Double.toString(result));
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			progressDialog.setProgress(values[0]);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class ComputeAvgThread implements Runnable {
		static final int STATUS_START = 0;
		static final int STATUS_STEP = 1;
		static final int STATUS_DONE = 2;
		HeavyWork hv;

		@Override
		public void run() {
			sum = 0;
			Message msg = new Message();
			msg.what = STATUS_START;
			handler.sendMessage(msg);

			for (int i = 0; i < complexity; i++) {
				hv = new HeavyWork();
				sum += hv.getNumber();
				msg = new Message();
				msg.what = STATUS_STEP;
				msg.obj = i + 1;
				handler.sendMessage(msg);
			}
			msg = new Message();
			msg.what = STATUS_DONE;
			msg.obj = sum / complexity;
			handler.sendMessage(msg);

		}
	}
}
