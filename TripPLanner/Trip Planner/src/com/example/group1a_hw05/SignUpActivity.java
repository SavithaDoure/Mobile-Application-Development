package com.example.group1a_hw05;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class SignUpActivity extends Activity {
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
		startActivity(intent);
		SignUpActivity.this.finish();
	}

	EditText etName, etEmail, etPassword, etPasswordConfirm;
	Button bSignUp, bCancel;
	String name, email, password, passwordConfirm;
	AlertDialog alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		etName = (EditText) findViewById(R.id.editTextUserName);
		etEmail = (EditText) findViewById(R.id.email);
		etPassword = (EditText) findViewById(R.id.pass);
		etPasswordConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);
		

		bSignUp = (Button) findViewById(R.id.buttonSignup);
		bCancel = (Button) findViewById(R.id.buttonCancel);

		bSignUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(checkInternet()){
				name = etName.getText().toString();
				email = etEmail.getText().toString();
				password = etPassword.getText().toString();
				passwordConfirm = etPasswordConfirm.getText().toString();
				if (name.equals("") || email.equals("") || password.equals("")
						|| passwordConfirm.equals("")) {
					Toast.makeText(SignUpActivity.this, "Enter required and vallid data",
							Toast.LENGTH_SHORT).show();
				} else {
					if (password.equalsIgnoreCase(passwordConfirm)) {
						signUp();
					} else
						Toast.makeText(SignUpActivity.this, "Password does not match",
								Toast.LENGTH_SHORT).show();
					etPassword.setText("");
					etPasswordConfirm.setText("");
				}
			}else{
				
			alert();	
			alertDialog.show();
			}
			}
		});

		bCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
				startActivity(intent);
				SignUpActivity.this.finish();
			}
		});
	}

	public void signUp() {
		ParseUser user = new ParseUser();
		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);
		user.put("fName", name);

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					Log.d("demo", "Sign Up successfull");
					ParseInstallation installation = ParseInstallation.getCurrentInstallation();
					installation.put("user",ParseUser.getCurrentUser());
					installation.saveInBackground();
					settingCurrentUser();
					Toast.makeText(SignUpActivity.this, name + " Successfully Logged In", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(SignUpActivity.this, TabViewActivity.class);
					startActivity(intent);
					SignUpActivity.this.finish();
				} else {
					Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public void settingCurrentUser() {
		ParseUser.becomeInBackground("session-token-here", new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					
					Log.d("SignUp", "The current user is now set to user.");
				} else {
					// The token could not be validated.
					Log.d("SignUp", "The token could not be validated.");
				}
			}
		});
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
				.setTitle("No Internet Connection..Try Again")
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

}
