package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class SingleSharedViewAdapter extends ArrayAdapter<SharedPlacedDetails> {

	Context context;
	ArrayList<SharedPlacedDetails> viewtrip;
	AlertDialog alertDialog;
	int PICK_CONTACT = 1;

	public SingleSharedViewAdapter(Context context,
			List<SharedPlacedDetails> objects) {
		super(context, R.layout.single_shared_trip, R.id.sharedtriname, objects);
		this.context = context;
		this.viewtrip = (ArrayList<SharedPlacedDetails>) objects;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.single_shared_trip, parent,
					false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView
					.findViewById(R.id.sharedtriname);
			holder.date = (TextView) convertView
					.findViewById(R.id.shareddistance);
			holder.share = (ImageView) convertView
					.findViewById(R.id.sharedimageView1);
			holder.delete = (ImageView) convertView
					.findViewById(R.id.sharedimageView2);

			convertView.setTag(holder);

		}

		holder = (ViewHolder) convertView.getTag();
		TextView title = holder.title;
		TextView dateval = holder.date;
		title.setText(viewtrip.get(position).getTripName());
		dateval.setText(viewtrip.get(position).getTraveldate());
		ImageView share = holder.share;
		ImageView delete = holder.delete;
		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				alert(position);
				if (alertDialog != null) {
					alertDialog.show();
				}

			}
		});
		share.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				shareAlert(position);
				if (alertDialog != null) {
					alertDialog.show();
				}
			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView date;
		ImageView share;
		ImageView delete;

	}

	public void alert(final int position) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setTitle("Want to delete the Trip?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								deleteTripinParse(position);
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		// create alert dialog
		alertDialog = alertDialogBuilder.create();
		// show it

	}

	private void deleteTripinParse(final int position) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SharedList");
		query.whereEqualTo("sharedEmailId", ParseUser.getCurrentUser()
				.getEmail());
		query.whereEqualTo("tripname", viewtrip.get(position).getTripName());
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (objects.size() > 0) {
					for (ParseObject p : objects) {
						p.deleteInBackground();
					}
					viewtrip.remove(position);
					notifyDataSetChanged();
					Toast.makeText(context, " Deleted the Trip Succesfully",
							Toast.LENGTH_LONG).show();
				}
			}

		});

	}

	public void shareAlert(final int position) {
		LayoutInflater li = LayoutInflater.from(context);
		View promptsView = li.inflate(R.layout.alert_layout, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setView(promptsView);
		final EditText etToDoItem = (EditText) promptsView
				.findViewById(R.id.editmailid);

		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setTitle("Enter An Email Id You Want to Share With.")
				.setPositiveButton("Share",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								String emailId = etToDoItem.getText()
										.toString().trim();
								ParseObject todo = new ParseObject("SharedList");
								Gson gson = new Gson();
								String json = gson.toJson(viewtrip
										.get(position));
								todo.put("sharedItem", json);
								todo.put("sharedEmailId", emailId);
								todo.put("tripname", viewtrip.get(position)
										.getTripName());
								todo.put("currentUser",
										ParseUser.getCurrentUser());
								todo.saveInBackground();
								Toast.makeText(context,
										"Shared With " + emailId,
										Toast.LENGTH_LONG).show();
								Log.d("DemoParse", "Event Saved!!!");
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		alertDialog = alertDialogBuilder.create();
	}
}
