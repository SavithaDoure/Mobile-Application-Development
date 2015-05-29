package com.example.week2try;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {

	Button add, subtract, multiply, divide, clearAll;
	EditText number1, number2;
	TextView result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		add = (Button) findViewById(R.id.button1);
		add.setOnClickListener(this);
		subtract = (Button) findViewById(R.id.button2);
		subtract.setOnClickListener(this);
		multiply = (Button) findViewById(R.id.button3);
		multiply.setOnClickListener(this);
		divide = (Button) findViewById(R.id.button4);
		divide.setOnClickListener(this);
		clearAll = (Button) findViewById(R.id.button5);
		clearAll.setOnClickListener(this);

		number1 = (EditText) findViewById(R.id.editText1);
		number2 = (EditText) findViewById(R.id.editText2);

		result = (TextView) findViewById(R.id.textView1);

	}

	@Override
	public void onClick(View v) {
		Log.d("Check", "in onclick");
		int duration = Toast.LENGTH_SHORT;
		Toast toast;
		if (v.getId() == R.id.button5) {
			number1.setText("");
			number2.setText("");
			result.setText("Result : ");
		} else {
			if (number1.getText().toString().equals("")
					|| number2.getText().toString().equals("")
					|| !isNumeric(number1.getText().toString())
					|| !isNumeric(number2.getText().toString())) {

				toast = Toast.makeText(getApplicationContext(),
						"Enter valid Number", duration);
				toast.show();
			} else {
				float op1 = Float.parseFloat(number1.getText().toString());
				float op2 = Float.parseFloat(number2.getText().toString());
				Log.d("Check", Float.toString(op1));

				switch (v.getId()) {
				case R.id.button1:
					result.setText("Result : " + Float.toString(op1 + op2));
					break;
				case R.id.button2:
					result.setText("Result : " + Float.toString(op1 - op2));
					break;
				case R.id.button3:
					result.setText("Result : " + Float.toString(op1 * op2));
					break;
				case R.id.button4:
					if (op2 == 0) {
						toast = Toast.makeText(getApplicationContext(),
								"Error: Divide by 0", duration);
						toast.show();
					} else {
						result.setText("Result : " + Float.toString(op1 / op2));
					}
					break;
				}
			}
		}

	}

	public static boolean isNumeric(String str) {
		try {
			float f = Float.parseFloat(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
