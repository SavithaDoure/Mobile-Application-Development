package com.example.inclass07;

import com.example.inclass06.R;
import com.example.inclass07.db.DAOManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class MainActivity extends Activity {
	static final String KEYWORD = "keyword";
	EditText etKeyword;
	Button bSubmit;
	DAOManager dm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etKeyword = (EditText) findViewById(R.id.idKeyword);
		bSubmit = (Button) findViewById(R.id.idbSubmit);

		bSubmit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = etKeyword.getText().toString();
				if (keyword.equals("")) {
					Toast.makeText(MainActivity.this,
							"Enter a keyword to search", Toast.LENGTH_SHORT)
							.show();
				} else {
					Intent intent = new Intent(MainActivity.this,
							GalleryActivity.class);
					intent.putExtra(KEYWORD, keyword);
					startActivity(intent);
				}

			}
		});
	}
}
