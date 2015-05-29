package com.example.radiobuttonsdemo;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

	ArrayList<ColorInfo> colors;
	RadioGroup radioGroup;
	RelativeLayout root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		root = (RelativeLayout) findViewById(R.id.rootLayout);

		colors = new ArrayList<ColorInfo>();
		colors.add(new ColorInfo("Red", Color.RED));
		colors.add(new ColorInfo("Green", Color.GREEN));
		colors.add(new ColorInfo("Blue", Color.BLUE));
		colors.add(new ColorInfo("White", Color.WHITE));
		colors.add(new ColorInfo("Black", Color.BLACK));

		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

		for (int i = 0; i < colors.size(); i++) {
			ColorInfo color = colors.get(i);
			RadioButton radioButton = new RadioButton(this);
			radioButton.setText(color.getLabel());
			radioButton.setTag(color.getRgb());
			radioGroup.addView(radioButton);

			if (i == 0) {
				radioGroup.check(radioButton.getId());
			}
		}

		findViewById(R.id.button1).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						if (radioGroup.getCheckedRadioButtonId() != -1) {
							RadioButton radioButtonChecked = (RadioButton) findViewById(radioGroup
									.getCheckedRadioButtonId());
							root.setBackgroundColor((Integer) radioButtonChecked
									.getTag());
						}
					}
				});
	}
}
