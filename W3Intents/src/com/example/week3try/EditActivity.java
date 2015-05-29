package com.example.week3try;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EditActivity extends Activity {

	static final int LAYOUT_ID = 1;
	TextView textview;
	EditText editText;
	Button bSave;
	Employee employee;
	String edit;
	RelativeLayout relativeLayout;
	RadioGroup radioGroup;
	RadioButton radioButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		relativeLayout = new RelativeLayout(this);
		relativeLayout.setId(LAYOUT_ID);
		relativeLayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setContentView(relativeLayout);

		textview = new TextView(this);
		textview.setText(R.string.text_name);
		textview.setTextSize(20);
		textview.setId(1);
		// Layout params of the text
		RelativeLayout.LayoutParams textRelLayout = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		textRelLayout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		textview.setLayoutParams(textRelLayout);
		textview.setTextSize(20);

		// EditText
		editText = new EditText(this);
		editText.setId(2);
		// Layout params of the text
		RelativeLayout.LayoutParams editRelLayout = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		editRelLayout.addRule(RelativeLayout.RIGHT_OF, textview.getId());
		editText.setLayoutParams(editRelLayout);
		editText.setTextSize(20);

		// Button
		bSave = new Button(this);
		bSave.setText("Save");
		// Layout params of the button
		RelativeLayout.LayoutParams buttonRelLayout = new RelativeLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		buttonRelLayout.addRule(RelativeLayout.CENTER_HORIZONTAL);
		buttonRelLayout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		bSave.setLayoutParams(buttonRelLayout);
		relativeLayout.addView(bSave);

		// Radio Group
		radioGroup = new RadioGroup(this);
		radioButton = new RadioButton(this);
		radioButton.setText(R.string.rb_sis);
		radioGroup.addView(radioButton);
		radioButton = new RadioButton(this);
		radioButton.setText(R.string.rb_cs);
		radioGroup.addView(radioButton);
		radioButton = new RadioButton(this);
		radioButton.setText(R.string.rb_health);
		radioGroup.addView(radioButton);

		RelativeLayout.LayoutParams radioRelLayout = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		radioRelLayout.addRule(RelativeLayout.BELOW, textview.getId());
		radioGroup.setLayoutParams(radioRelLayout);

		if (getIntent().getExtras() != null) {
			if (getIntent().getExtras().getSerializable(MainActivity.EMP_KEY) != null
					&& getIntent().getExtras().getString(
							DisplayActivity.EDIT_KEY) != null) {
				employee = (Employee) getIntent().getExtras().getSerializable(
						MainActivity.EMP_KEY);
				edit = getIntent().getExtras().getString(
						DisplayActivity.EDIT_KEY);
				setText(edit);
			} else
				setResult(RESULT_CANCELED);
		} else {
			setResult(RESULT_CANCELED);
		}

		bSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d("Demo", edit);
				Log.d("Demo", editText.getText().toString());
				switch (edit) {
				case "name":
					employee.setName(editText.getText().toString());
					break;
				case "age":
					employee.setAge(editText.getText().toString());
					break;
				case "email":
					employee.setEmail(editText.getText().toString());
					break;
				case "phone":
					employee.setPhone(editText.getText().toString());
					break;
				default:
					int childcount = radioGroup.getChildCount();
					for (int i = 0; i < childcount; i++) {
						radioButton = (RadioButton) radioGroup.getChildAt(i);
						if (radioButton.isChecked()) {
							employee.setDepartment(radioButton.getText()
									.toString());
						}
					}
					break;
				}
				Intent data = new Intent();
				data.putExtra(MainActivity.EMP_KEY, employee);
				setResult(RESULT_OK, data);
				finish();

			}
		});

	}

	private void setText(String edit) {
		switch (edit) {
		case "name":
			editText.setText(employee.getName());
			addView1();
			break;
		case "age":
			editText.setText(employee.getAge());
			addView1();
			break;
		case "email":
			editText.setText(employee.getEmail());
			addView1();
			break;
		case "phone":
			editText.setText(employee.getPhone());
			addView1();
			break;
		default:
			RadioButton rb;
			int childcount = radioGroup.getChildCount();
			for (int i = 0; i < childcount; i++) {
				rb = (RadioButton) radioGroup.getChildAt(i);
				if (employee.getDepartment().equals(rb.getText().toString())) {
					radioGroup.check(rb.getId());
				}
			}
			addView2();
			break;
		}
	}

	private void addView1() {
		relativeLayout.addView(textview);
		relativeLayout.addView(editText);

	}

	private void addView2() {
		relativeLayout.addView(textview);
		relativeLayout.addView(radioGroup);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
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
