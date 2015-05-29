package com.example.alertdialogexample;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	static final String TAG = "Demo";
	final CharSequence[] items = { "One", "Two", "Three", "Four" };
	ArrayList<Integer> choices = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Simple Alert
		AlertDialog.Builder simpleBuilder = new AlertDialog.Builder(this);
		simpleBuilder
				.setCancelable(false)
				.setMessage("Simple Alert")
				.setTitle("Simple Alert")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d(TAG, "OK");

					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();

							}
						});

		final AlertDialog simpleAlert = simpleBuilder.create();
		findViewById(R.id.button1).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						simpleAlert.show();

					}
				});

		// Single Choice Item Selection
		AlertDialog.Builder singleChoicebuilder = new AlertDialog.Builder(this);
		singleChoicebuilder.setTitle("Single Choice Builder").setItems(items,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d(TAG, items[which].toString());

					}
				});

		final AlertDialog singleChoiceAlert = singleChoicebuilder.create();
		findViewById(R.id.button2).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						singleChoiceAlert.show();

					}
				});

		// Radio Button Choice
		AlertDialog.Builder radioBuilder = new AlertDialog.Builder(this);
		radioBuilder
				.setTitle("Radio Button choice")
				.setSingleChoiceItems(items, 0,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Log.d(TAG, items[which].toString());

							}
						})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d(TAG, "OK");

					}
				});

		final AlertDialog radioAlert = radioBuilder.create();
		findViewById(R.id.button3).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						radioAlert.show();

					}
				});

		// Multiple choice Alert Dialog
		AlertDialog.Builder multipleBuilder = new AlertDialog.Builder(this);
		multipleBuilder
				.setTitle("Multiple Choice Dialog")
				.setMultiChoiceItems(items, null,
						new DialogInterface.OnMultiChoiceClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which, boolean isChecked) {
								if (isChecked) {
									choices.add(Integer.valueOf(which));
									Log.d(TAG, "Adding: " + items[which]);
								} else {
									choices.remove(Integer.valueOf(which));
									Log.d(TAG, "Remove: " + items[which]);
								}

							}
						})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d(TAG, "Multiple Choice: OK");
						for (Integer i : choices) {
							Log.d(TAG, items[i.intValue()] + "");
						}
					}
				});

		final AlertDialog multipleAlert = multipleBuilder.create();
		findViewById(R.id.button4).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						multipleAlert.show();

					}
				});

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Progress Bar Demo");
		progressDialog.setCancelable(true);

		findViewById(R.id.button5).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						progressDialog.show();

					}
				});
	}
}
