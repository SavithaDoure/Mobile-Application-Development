package com.example.group1a_hw05;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;


/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
	
	public class ParsePushReciever extends ParsePushBroadcastReceiver {

	    @Override
	    public void onPushOpen(Context context, Intent intent) {
	        Log.e("Push", "Clicked");
	        Intent i = new Intent(context, LoginActivity.class);
	        i.putExtras(intent.getExtras());
	        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        context.startActivity(i);
	    }
	}


