package com.example.group1a_hw05;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mad.bean.SelectPlace;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class SelectPlaceAdapter extends ArrayAdapter<SelectPlace> {

	Context context;
	ArrayList<SelectPlace> progItems;

	public SelectPlaceAdapter(Context context, List<SelectPlace> objects) {
		super(context, R.layout.single_select_place, R.id.nexticon,objects);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.progItems =(ArrayList<SelectPlace>) objects;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.single_select_place,
					parent, false);
			holder = new ViewHolder();
			holder.icontitle = (TextView) convertView.findViewById(R.id.icondesc);
			holder.icon = (ImageView) convertView
					.findViewById(R.id.icon);
			convertView.setTag(holder);

		}

		holder = (ViewHolder) convertView.getTag();
		TextView icontitle = holder.icontitle;
		ImageView icon = holder.icon;
		icontitle.setText(progItems.get(position).getIconname());
		icon.setImageResource(progItems.get(position).getIcon());
		return convertView;
	}

	static class ViewHolder {
		TextView icontitle;
		ImageView icon;
	}
}
