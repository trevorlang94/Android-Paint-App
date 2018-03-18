package com.example.main.beginningpainttest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.tv.TvInputService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Intro extends AppCompatActivity {

	Button logInButton;
	CheckBox logInCheck;
	EditText passwordEnter, usernameEnter;
	Boolean logInChkChecked = true;
	TextView usernameText, passwordText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro2);

		//Declarations
		logInButton = (Button) findViewById(R.id.continueButton);
		logInCheck = (CheckBox) findViewById(R.id.logInCheckBox);
		passwordEnter = (EditText) findViewById(R.id.passwordTextEnter);
		usernameEnter = (EditText) findViewById(R.id.userNameTextEnter);
		usernameText = (TextView) findViewById(R.id.userNameText);
		passwordText = (TextView) findViewById(R.id.passwordText);

		final SessionManager logInManager = new SessionManager(this);
		SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		SharedPreferences.Editor editor = pref.edit();


		logInCheck.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logInChkChecked = logInCheck.isChecked();
				if(logInChkChecked == false){
					passwordEnter.setVisibility(View.INVISIBLE);
					usernameEnter.setVisibility(View.INVISIBLE);
					passwordText.setVisibility(View.INVISIBLE);
					usernameText.setVisibility(View.INVISIBLE);
				}
				else{
					passwordEnter.setVisibility(View.VISIBLE);
					usernameEnter.setVisibility(View.VISIBLE);
					passwordText.setVisibility(View.VISIBLE);
					usernameText.setVisibility(View.VISIBLE);
				}

			}
		});


		logInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = usernameEnter.getText().toString();
				String password = passwordEnter.getText().toString();

				if(logInChkChecked){
					if (username.trim().length() > 0 && password.trim().length() > 0) {


						logInManager.createLogInSession(username,
								password);

						// Starting MainActivity
						Intent i = new Intent(getApplicationContext(), MainActivity.class);
						i.putExtra("name", username);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(i);

						finish();
					}


						 else{

								// if the check fails..
								Toast.makeText(getApplicationContext(), "Enter a username and password.", Toast.LENGTH_LONG).show();

							}
					}

					else{
					// Starting MainActivity
					Intent i = new Intent(getApplicationContext(), MainActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

					// Add new Flag to start new Activity
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(i);

					finish();
					}
			}
			});
		}
	}



