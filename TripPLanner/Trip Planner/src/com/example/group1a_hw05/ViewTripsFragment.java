package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mad.adapter.SingleTripViewAdapter;
import com.mad.bean.PlaceDetails;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class ViewTripsFragment extends Fragment {
	@Override
	public void onResume() {
		Log.d("fragmentcalled", "onResume"+"view");
		super.onResume();
		viewtrips();
	}

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	List<SharedPlacedDetails> viewTripList;
	ListView tripListView;
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	SharedPreferences preference;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ViewTripsFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ViewTripsFragment newInstance(String param1, String param2) {
		ViewTripsFragment fragment = new ViewTripsFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public ViewTripsFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("fragmentcalled", "onCreate"+"view");
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		Log.d("fragmentcalled", "onCreateView"+"view");
		View view =  inflater.inflate(R.layout.fragment_view_trips, container, false);
		tripListView = (ListView) view.findViewById(R.id.viewtrip);
		return view;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d("fragmentcalled", "onAttach"+"view");
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}
	
		private void viewtrips(){
		
		viewTripList = new ArrayList<SharedPlacedDetails>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SavTripList");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if(objects.size()>0){
					SharedPlacedDetails singletrip;
				Gson gson = new Gson();
				for (ParseObject p : objects) {
					String savObj = p.getString("savetrip");
					singletrip = gson.fromJson(savObj, SharedPlacedDetails.class);
					viewTripList.add(singletrip);
				}
				SingleTripViewAdapter adapter = new SingleTripViewAdapter(
						getActivity(), viewTripList);
				tripListView.setAdapter(adapter);
				adapter.setNotifyOnChange(true);
				tripListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						SharedPlacedDetails singleItem = viewTripList.get(position);
						preference = getActivity().getApplicationContext().getSharedPreferences(
								EditTripActivity.MyPREFERENCES, Context.MODE_PRIVATE);
						Editor editor = preference.edit();
						editor.putString("update", "yes");
						editor.putString("tripname", viewTripList.get(position).getTripName());
						editor.putString("traveldate", viewTripList.get(position).getTraveldate());
						editor.commit();
						Intent intent = new Intent(getActivity(),
								EditTripActivity.class);
						intent.putExtra("single", singleItem);
						startActivity(intent);
						getActivity().finish();


					}
				});
				

			}
			}
		});

		
	}

}
