package com.example.group1a_hw05;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddTripFragment extends Fragment implements View.OnClickListener {
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	TextView addtrip;
	private String mParam1;
	private String mParam2;
	EditText tripname,traveldate;
	SharedPreferences preference;
	public final  String TRIP_NAME = "tripName"; 
	static final int DATE_DIALOG_ID = 0;
		public int year, month, day;
		// Picker Dialog first appears
		private int mYear, mMonth, mDay;
		// constructor
			
				// Assign current Date and Time Values to Variables
				
			
	//private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment AddTripFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static AddTripFragment newInstance(String param1, String param2) {
		AddTripFragment fragment = new AddTripFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public AddTripFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_add_trip, container, false);
		addtrip = (TextView) view.findViewById(R.id.nexteditbutton);
		
		tripname = (EditText) view.findViewById(R.id.nameoftraveltrip);
		traveldate = (EditText)view. findViewById(R.id.travelingdate);
		traveldate.setKeyListener(null);
		// Set ClickListener on etDate
		traveldate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Show the DatePickerDialog
				 DialogFragment newFragment = new DatePickerFragment();
				    newFragment.show(getFragmentManager(), "datePicker");	
			}
		});
		
		addtrip.setOnClickListener(this);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		
		
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
	/*	if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}*/
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
		//	mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		//mListener = null;
	}

	// Register DatePickerDialog listener
			private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
				// the callback received when the user "sets" the Date in the
				// DatePickerDialog
				public void onDateSet(DatePicker view, int yearSelected,
						int monthOfYear, int dayOfMonth) {
					year = yearSelected;
					month = monthOfYear + 1;
					day = dayOfMonth;
					// Set the Selected Date in Select date Button
					traveldate.setText(month + "/" + day + "/" + year);
				}
			};
			
			public class DatePickerFragment extends DialogFragment
			implements DatePickerDialog.OnDateSetListener {

			@Override
			public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
			}

			public void onDateSet(DatePicker view, int year, int month, int day) {
				
				// Set the Selected Date in Select date Button
				traveldate.setText(month+1 + "/" + day + "/" + year);
				
				
			}

			
			}

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				
				case R.id.nexteditbutton:
					String tripName = tripname.getText().toString(); 
					String travelDate = traveldate.getText().toString();
					if (tripName.equals("") || travelDate.equals("")) {
						Toast.makeText(getActivity(),
								"Please enter missing data", Toast.LENGTH_SHORT)
								.show();
					}else{
					preference = getActivity().getSharedPreferences(
							EditTripActivity.MyPREFERENCES, Context.MODE_PRIVATE);
					Editor editor = preference.edit();
					editor.putString("tripname", tripName);
					editor.putString("traveldate", travelDate);
					editor.commit();
					tripname.setText("");
					traveldate.setText("");
					Intent intent = new Intent(getActivity(),SelectPlaceActivity.class);
					startActivity(intent);
					getActivity().finish();
					}
					break;

				default:
					break;
				}
				
			}
			
}
