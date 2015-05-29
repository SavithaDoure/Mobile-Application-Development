package com.example.group1a_hw05;
import com.mad.bean.Keys;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

import android.app.Application;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class ParseApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		Parse.initialize(this, Keys.applicationId, Keys.clientKey);
		PushService.setDefaultPushCallback(this, LoginActivity.class);
		ParseInstallation.getCurrentInstallation().saveInBackground();
	}

}
