package com.example.handlerintro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	Handler handler;
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("In Progress");
		progressDialog.setMax(100);
		progressDialog.setCancelable(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		handler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case DoWork.STATUS_START:
					progressDialog.show();
					break;
				case DoWork.STATUS_STEP:
					// progressDialog.setProgress((Integer) msg.obj);
					progressDialog
							.setProgress(msg.getData().getInt("Progress"));
					break;
				case DoWork.STATUS_DONE:
					progressDialog.dismiss();
					break;
				}
				return false;
			}
		});

		new Thread(new DoWork()).start();

	}

	class DoWork implements Runnable {
		static final int STATUS_START = 0;
		static final int STATUS_STEP = 1;
		static final int STATUS_DONE = 2;

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = STATUS_START;
			handler.sendMessage(msg);
			for (int i = 0; i < 100; i++) {
				for (int j = 0; j < 1000000; j++) {

				}
				msg = new Message();
				msg.what = STATUS_STEP;
				msg.obj = i + 1;

				Bundle bundle = new Bundle();
				bundle.putInt("Progress", i + 1);
				msg.setData(bundle);
				handler.sendMessage(msg);
			}
			msg = new Message();
			msg.what = STATUS_DONE;
			handler.sendMessage(msg);
		}

	}
}
