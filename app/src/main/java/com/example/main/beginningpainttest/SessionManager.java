package com.example.main.beginningpainttest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Main on 12/6/2017.
 */

public class SessionManager {
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	Context _context;
	public static final String KEY_NAME = "name";
	public static final String KEY_PASSWORD = "password";
	// Shared Preferences file name
	private static final String PREFERENCE_NAME = "PaintPref";
	private static final String IS_LOGIN = "IsLoggedIn";

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Constructor
	public SessionManager(Context context){
		this._context = context;
		preferences = _context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
		editor = preferences.edit();
	}

	public void createLogInSession(String name, String password) {
		editor.putBoolean(IS_LOGIN, true);
		editor.putString(KEY_NAME, name);
		editor.putString(KEY_PASSWORD, password);
		editor.commit();
	}

	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(KEY_NAME, preferences.getString(KEY_NAME, null));
		user.put(KEY_PASSWORD, preferences.getString(KEY_PASSWORD, null));

		return user;
	}

	public boolean checkLoggedIn(){
		if(!this.isLoggedIn()){
			Intent i = new Intent(_context, Intro.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			_context.startActivity(i);

			return true;
		}
		return false;
	}

	public void logoutUser(){

		//clear Shared Preferences
		editor.clear();
		editor.commit();

		Intent i = new Intent(_context, Intro.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		_context.startActivity(i);
	}


	public boolean isLoggedIn(){
		return preferences.getBoolean(IS_LOGIN, false);
	}

}

