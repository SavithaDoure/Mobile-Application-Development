package com.example.group1a_hw05;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SignUpCallback;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class LoginActivity extends Activity implements View.OnClickListener{
	EditText etEmail, etPassword;
	Button bLogin, bCreateAcct;
	String email, password;
	ParseObject parseObject;
	LoginButton loginButton;
	CallbackManager callbackManager;
	AlertDialog alertDialog;
	TextView forgotpwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getHashCode();
		 FacebookSdk.sdkInitialize(getApplicationContext());
	        callbackManager = CallbackManager.Factory.create();
		loginButton = (LoginButton) findViewById(R.id.login_button);
		loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
		etEmail = (EditText) findViewById(R.id.editTextEmail);
		etPassword = (EditText) findViewById(R.id.editTextPassword);
		bLogin = (Button) findViewById(R.id.buttonLogin);
		forgotpwd = (TextView) findViewById(R.id.forgot);
		forgotpwd.setOnClickListener(this);
		bCreateAcct = (Button) findViewById(R.id.buttonCreateNewAccount);
		checkForCurrentUser();
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
	        @Override
	        public void onSuccess(LoginResult loginResult) {
	        	AccessToken token = loginResult.getAccessToken();
        		GraphRequest request = GraphRequest.newMeRequest(
	        			token,
	        	        new GraphRequest.GraphJSONObjectCallback() {
	        	            @Override
	        	            public void onCompleted(
	        	                   JSONObject object,
	        	                   GraphResponse response) {
	        	            		JSONObject user = response.getJSONObject();
	        	            		try {
										String id = user.getString("id");
										final String name = user.getString("name");
										final String email = user.optString("email");
										final String password = "facebook";
										loginInFb( email, password,name);
										Intent intent = new Intent(LoginActivity.this, TabViewActivity.class);
										startActivity(intent);
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
	        	            }
	        	        });
	        	Bundle parameters = new Bundle();
	        	parameters.putString("fields", "id,name,email,gender, birthday");
	        	request.setParameters(parameters);
	        	request.executeAsync();
	        	
	        }

	        @Override
	        public void onCancel() {
	        	
	        }

	        @Override
	        public void onError(FacebookException exception) {
	        	Log.d("success", exception.toString());
	            // App code
	        }
	    });  
		
	
		bCreateAcct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
		});

		bLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean networkstate = checkInternet();
				if(networkstate){
					logginIn();	
				}else{
					alert();
					
					alertDialog.show();
				}
				
			}
		});

	}
	public void checkForCurrentUser() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// do stuff with the user
			Log.d("InClass08", currentUser.getUsername() + "Is current User");
			Intent intent = new Intent(LoginActivity.this, TabViewActivity.class);
			startActivity(intent);
			this.finish();

		} else {
			// show the signup or login screen
			Log.d("LoginActivity",
					"No Current User. show the signup or login screen");
		}
	}

	public void logginIn() {
		email = etEmail.getText().toString();
		password = etPassword.getText().toString();
		if (email.equals("") || password.equals("")) {
			Toast.makeText(LoginActivity.this, "Enter the required data",
					Toast.LENGTH_SHORT).show();
		} else {
			ParseUser.logInInBackground(email, password, new LogInCallback() {
				public void done(ParseUser user, ParseException e) {
					if (user != null) {
						settingCurrentUser(user);
						checkForCurrentUser();
					} else {
						Log.d("LoginActivity",
								"Login failed: " + e.getLocalizedMessage());
						Toast.makeText(LoginActivity.this,
								e.getLocalizedMessage(), Toast.LENGTH_SHORT)
								.show();
					}
				}
			});
		}

	}
	
	public void loginInFb(final String email,final String password,final String name){
		ParseUser.logInInBackground(email, password, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					Log.d("LoginFb", "The user is logged in.");
					settingCurrentUser(user);
					checkForCurrentUser();
					
				} else {
					final ParseUser fbuser = new ParseUser();
					fbuser.setUsername(email);
					fbuser.setPassword(password);
					fbuser.setEmail(email);
					fbuser.put("Name", name);
					fbuser.signUpInBackground(new SignUpCallback() {
						public void done(ParseException e) {
							if (e == null) {
								Log.d("LoginFb", "Sign Up successfull");
								settingCurrentUser(fbuser);
								Toast.makeText(LoginActivity.this, name + " Successfully Logged In", Toast.LENGTH_LONG).show();
								Intent intent = new Intent(LoginActivity.this, TabViewActivity.class);
								startActivity(intent);
								LoginActivity.this.finish();
							} else {
								Toast.makeText(LoginActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
							}
						}
					});
				}
			}
		});
		
	}

	public void settingCurrentUser(ParseUser user) {
		ParseUser.becomeInBackground("session-token-here", new LogInCallback() {
			public void done(final ParseUser user, ParseException e) {
				if (user != null) {
				} else {
					// The token could not be validated.
					Log.d("demo", "The token could not be validated.");
				}
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	
	private void getHashCode(){
		try {
	        PackageInfo info = getPackageManager().getPackageInfo(
	                "com.example.group1a_hw05", 
	                PackageManager.GET_SIGNATURES);
	        for (Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	            }
	    } catch (NameNotFoundException e) {

	    } catch (NoSuchAlgorithmException e) {

	    }
	}
	
public boolean checkInternet(){
	ConnectivityManager connMgr = (ConnectivityManager) 
	        getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    if (networkInfo != null && networkInfo.isConnected()) {
	        return true;
	    } else {
	    	return false;
	    }
	    
	
}

public void alert() {
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
			this);
	// set dialog message
	alertDialogBuilder
			.setCancelable(false)
			.setTitle("No Internet Connection. Try Again")
			.setNegativeButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
	// create alert dialog
	alertDialog = alertDialogBuilder.create();
	// show it

}
@Override
public void onClick(View v) {
	switch (v.getId()) {
	case R.id.forgot:
		forgotpwdalert();
		if (alertDialog != null) {
			alertDialog.show();
		}
		break;

	default:
		break;
	}
	
}
public void forgotpwdalert() {
	LayoutInflater li = LayoutInflater.from(this);
	View promptsView = li.inflate(R.layout.alert_layout, null);
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
			this);
	alertDialogBuilder.setView(promptsView);
	final EditText etToDoItem = (EditText) promptsView
			.findViewById(R.id.editmailid);

	// set dialog message
	alertDialogBuilder
			.setCancelable(false)
			.setTitle("Reset Password")
			.setMessage("Enter an email Id")
			.setPositiveButton("Send",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							String email = etToDoItem.getText()
									.toString().trim();
							ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
								
								@Override
								public void done(ParseException e) {
									 if (e == null) {
									     Toast.makeText(LoginActivity.this, "A Reset Password Mail has been Sent", Toast.LENGTH_SHORT).show();
									    } else {
									    	 Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
									    }
									
								}
							});
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
