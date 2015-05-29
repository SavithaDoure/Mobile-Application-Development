package com.example.group1a_hw05;
import android.app.Application;

import com.facebook.FacebookSdk;
import com.mad.bean.Keys;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.PushService;

/**
 * 
 * @Team : Savitha doure, Venkatesh Kalva, Ashraf Cherukuri
 *
 */
public class TripApplication extends Application  {
	
	@Override public void onCreate() { 
        super.onCreate();

        Parse.initialize(this, Keys.applicationId, Keys.clientKey);  
       PushService.setDefaultPushCallback(this, TabViewActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();	
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
	

}


