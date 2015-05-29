package com.example.group1a_hw05;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class CurrentLocFragment extends Fragment {
	ImageView currentLoca;
	AlertDialog alertDialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_current_location,
				container, false);
		
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		currentLoca = (ImageView) getView().findViewById(R.id.continueloc);

		currentLoca.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),LoginActivity.class);
				startActivity(intent);
				getActivity().finish();
				
				
			}
		});
	}
	
	
	public void alert() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				getActivity());
		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setTitle("Allow Trip Planner to access your current location while you use the app? ")
				.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent(getActivity(),LoginActivity.class);
						startActivity(intent);
						getActivity().finish();	
					}
				})
				.setNegativeButton("Don't Allow",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent intent = new Intent(getActivity(),LoginActivity.class);
								startActivity(intent);
								getActivity().finish();
							}
						});
		// create alert dialog
		alertDialog = alertDialogBuilder.create();
		// show it

	}
}
