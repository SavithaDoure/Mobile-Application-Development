package com.example.week3try;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

	static final String EMP_KEY = "employee";
	Button bSubmit;
	EditText etName, etAge, etEmail, etPhoneNo;
	RadioGroup radioGroup;
	RadioButton radioButton;
	
	Employee employee;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bSubmit = (Button) findViewById(R.id.save);
		etName = (EditText) findViewById(R.id.text);
		etAge = (EditText) findViewById(R.id.editText2);
		etEmail = (EditText) findViewById(R.id.editText3);
		etPhoneNo = (EditText) findViewById(R.id.editText4);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

		bSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String name, age, email, phone, dept;
				name = etName.getText().toString();
				age = etAge.getText().toString();
				email = etEmail.getText().toString();
				phone = etPhoneNo.getText().toString();

				if (!(name.equals("")) && !(age.equals(""))
						&& !(email.equals("")) && !(phone.equals(""))) {					
					
					radioButton= (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
					dept = radioButton.getText().toString();
					employee = new Employee(name, age, email, phone, dept);
					/*Toast.makeText(MainActivity.this,
							employee.toString()+"", Toast.LENGTH_SHORT)
							.show();*/
					
					Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
					intent.putExtra(EMP_KEY, employee);
					startActivity(intent);

				} else if (name.equals("") || age.equals("")
						|| email.equals("") || phone.equals("")) {
					Toast.makeText(MainActivity.this,
							"Please enter missing entries", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});

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
}
