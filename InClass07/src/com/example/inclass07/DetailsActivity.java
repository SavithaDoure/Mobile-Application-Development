package com.example.inclass07;

import com.example.inclass06.R;
import com.example.inclass07.db.DAOManager;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * 
 * References:
 * http://code.tutsplus.com/tutorials/android-sdk-working-with-picasso--cms-22149
 * */
public class DetailsActivity extends Activity {
	TextView tvPhotoName, tvOwner;
	ImageView ivImage;
	DAOManager dm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		tvPhotoName = (TextView) findViewById(R.id.idPhotoName);
		tvOwner = (TextView) findViewById(R.id.idOwner);
		ivImage = (ImageView) findViewById(R.id.imageView1);

		if (getIntent().getExtras() != null) {
			Photo photo = (Photo) getIntent().getExtras().getSerializable(
					GalleryActivity.GALLERY_OBJ);
			tvPhotoName.setText(photo.getName());
			tvOwner.setText(tvOwner.getText().toString() + photo.getUserName());
			if (photo.getImageUrl().equals("")) {
				ivImage.setImageResource(R.drawable.photo_not_found);
			} else
				Picasso.with(this).load(photo.getImageUrl()).into(ivImage);
		
		}

	}
}
