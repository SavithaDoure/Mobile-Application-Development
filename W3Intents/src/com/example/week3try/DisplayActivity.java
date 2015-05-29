package com.example.week3try;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends Activity implements View.OnClickListener {

	TextView name, age, email, phone, dept;
	ImageView ivName, ivAge, ivEmail, ivPhone, ivDept;
	Employee employee;

	static final String EDIT_KEY = "edit";
	static final int REQ_CODE = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display);
		name = (TextView) findViewById(R.id.name);
		age = (TextView) findViewById(R.id.age);
		email = (TextView) findViewById(R.id.email);
		phone = (TextView) findViewById(R.id.phone);
		dept = (TextView) findViewById(R.id.department);

		ivName = (ImageView) findViewById(R.id.imageView1);
		ivName.setOnClickListener(this);
		ivAge = (ImageView) findViewById(R.id.imageView2);
		ivAge.setOnClickListener(this);
		ivEmail = (ImageView) findViewById(R.id.imageView3);
		ivEmail.setOnClickListener(this);
		ivPhone = (ImageView) findViewById(R.id.imageView4);
		ivPhone.setOnClickListener(this);
		ivDept = (ImageView) findViewById(R.id.imageView5);
		ivDept.setOnClickListener(this);

		if (getIntent().getExtras() != null) {
			employee = (Employee) getIntent().getExtras().getSerializable(
					MainActivity.EMP_KEY);
			name.setText(employee.getName());
			age.setText(employee.getAge());
			email.setText(employee.getEmail());
			phone.setText(employee.getPhone());
			dept.setText(employee.getDepartment());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display, menu);
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

	@Override
	public void onClick(View v) {
		Intent intent = new Intent("com.example.week3try.intent.action.VIEW");
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.putExtra(MainActivity.EMP_KEY, employee);

		switch (v.getId()) {
		case R.id.imageView1:
			intent.putExtra(EDIT_KEY, "name");
			break;
		case R.id.imageView2:
			intent.putExtra(EDIT_KEY, "age");
			break;
		case R.id.imageView3:
			intent.putExtra(EDIT_KEY, "email");
			break;
		case R.id.imageView4:
			intent.putExtra(EDIT_KEY, "phone");
			break;
		default:
			intent.putExtra(EDIT_KEY, "department");
			break;
		}
		startActivityForResult(intent, REQ_CODE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_CODE) {
			if (resultCode == RESULT_OK) {
				if (data.getExtras() != null) {
					employee = (Employee) data.getExtras().getSerializable(
							MainActivity.EMP_KEY);
					name.setText(employee.getName());
					age.setText(employee.getAge());
					email.setText(employee.getEmail());
					phone.setText(employee.getPhone());
					dept.setText(employee.getDepartment());
				}
			}
		}
	}
}
