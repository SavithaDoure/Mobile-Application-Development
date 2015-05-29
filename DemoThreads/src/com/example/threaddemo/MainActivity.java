package com.example.threaddemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	ExecutorService threadPool;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.button1).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						Thread thread = new Thread(new DoWork());
						thread.start();
					}
				});

		findViewById(R.id.button2).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						threadPool = Executors.newFixedThreadPool(4);
						threadPool.execute(new DoWork());
					}
				});
	}

	class DoWork implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 10000; i++) {
				for (int j = 0; j < 10000; j++) {

				}
			}
		}

	}
}
