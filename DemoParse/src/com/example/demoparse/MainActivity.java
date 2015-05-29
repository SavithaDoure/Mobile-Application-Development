package com.example.demoparse;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tvDisplay;
	Button bSignUp, bLogin, bCheckCurrentUser, bLogout, bEvents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);
		Parse.initialize(this, "o5hvWIXPl0iYxLty9meLyhzoQ4XBlE7Rwz9X85GS",
				"0CfuSfArKp0YYt3DShN9sYerQiuOENEgV11ATqO1");

	/*	tvDisplay = (TextView) findViewById(R.id.textView1);
		bSignUp = (Button) findViewById(R.id.bSignUp);
		bLogin = (Button) findViewById(R.id.button2);
		bCheckCurrentUser = (Button) findViewById(R.id.button3);
		bLogout = (Button) findViewById(R.id.button4);
		bEvents = (Button) findViewById(R.id.bretrieve);*/
		bEvents.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						EventsActivity.class);
				startActivity(intent);
			}
		});

		bSignUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				signUp();
			}
		});

		bLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logginIn();

			}
		});

		bCheckCurrentUser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				currentUserExists();
			}
		});

		bLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logout();

			}
		});
	}

	public void parseDemo() {
		Log.d("DemoParse", "On create");

		// Saving objects
		ParseObject gameScore = new ParseObject("GameScore");
		gameScore.put("score", 1337);
		gameScore.put("playerName", "Sean Plott");
		gameScore.put("cheatMode", false);
		gameScore.saveInBackground();

		// Retrieving Objects
		ParseQuery<ParseObject> query = ParseQuery.getQuery("GameScore");
		query.getInBackground("9VbaIN7iqz", new GetCallback<ParseObject>() {

			@Override
			public void done(ParseObject parseObject, ParseException e) {
				if (e == null) {
					// object will be your game score
					Log.d("DemoParse", "Object Retrieved");
					Log.d("DemoParse", "Score: " + parseObject.getInt("score"));
				} else {
					Log.d("DemoParse",
							"Error occured: " + e.getLocalizedMessage());
				}
			}
		});
	}

	public void signUp() {
		ParseUser user = new ParseUser();
		user.setUsername("Username");
		user.setPassword("Password");
		user.setEmail("email@example.com");

		// other fields can be set just like with ParseObject
		// user.put("phone", "650-253-0000");

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					// Hooray! Let them use the app now.
					Log.d("DemoParse", "Hooray! Let them use the app now.");
					tvDisplay.setText("Hooray! Let them use the app now.");
					settingCurrentUser();
				} else {
					// Sign up didn't succeed. Look at the ParseException
					// to figure out what went wrong
					Log.d("DemoParse",
							"Sign up didn't succeed cos: "
									+ e.getLocalizedMessage());
					tvDisplay.setText("Sign up didn't succeed");
				}
			}
		});
	}

	public void logginIn() {
		ParseUser.logInInBackground("Username", "Password",
				new LogInCallback() {
					public void done(ParseUser user, ParseException e) {
						if (user != null) {
							// Hooray! The user is logged in.
							// Log.d("DemoParse",
							// "Hooray! The user is logged in.");
							// tvDisplay.setText("Hooray! The user is logged in.");
							// settingCurrentUser();
						} else {
							// Login failed. Look at the ParseException to see
							// what happened.
							// Log.d("DemoParse", "Login failed");
							// tvDisplay.setText("Login failed");
						}
					}
				});
	}

	public void currentUserExists() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// do stuff with the user
			Log.d("DemoParse", "Is current User");
			tvDisplay.setText(currentUser.getUsername() + " is current User.");

		} else {
			// show the signup or login screen
			Log.d("DemoParse",
					"No Current User. show the signup or login screen");
			tvDisplay
					.setText("No Current User. show the signup or login screen");
		}
	}

	public void settingCurrentUser() {
		ParseUser.becomeInBackground("session-token-here", new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					// The current user is now set to user.
					Log.d("DemoParse", "The current user is now set to user.");
					tvDisplay.setText("The current user is now set to user.");
				} else {
					// The token could not be validated.
					Log.d("DemoParse", "The token could not be validated.");
					tvDisplay.setText("The token could not be validated.");
				}
			}
		});
	}

	public void logout() {
		ParseUser.logOut();
	}

}
