package com.example.demoparse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApplication extends Application {

	@Override
	public void onCreate() {

		super.onCreate();

		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);
		Parse.initialize(this, "o5hvWIXPl0iYxLty9meLyhzoQ4XBlE7Rwz9X85GS",
				"0CfuSfArKp0YYt3DShN9sYerQiuOENEgV11ATqO1");

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();

		// If you would like all objects to be private by default, remove this
		// line.
		defaultACL.setPublicReadAccess(true);

		ParseACL.setDefaultACL(defaultACL, true);
	}

}
