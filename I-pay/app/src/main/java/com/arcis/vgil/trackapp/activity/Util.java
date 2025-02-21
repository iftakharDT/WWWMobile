package com.arcis.vgil.trackapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.arcis.vgil.trackapp.data.Constants;


public class Util {


	public static SharedPreferences getSharedPreferences(Context context,String name){

		SharedPreferences pref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return pref;
	}

	public static String getUSerContactTypeId(Context context){

		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

		String id = pref.getString(Constants.CONTACTTYPEID, "");
		return id;
	}

	public static String getUSerName(Context context){

		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);

		String username = pref.getString(Constants.username, "");
		return username;
	}

	public static String getPassword(Context context){

		SharedPreferences pref = context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
		String password = pref.getString(Constants.password, "");
		return password;
	}

	public static Toast  showToast(Context context, String msg, int duration){

		Toast toast =Toast.makeText(context, msg, duration);
		return toast;
	}
	
	public static SharedPreferences getAreaMangerSharedPreferences(Context context){

		SharedPreferences pref = context.getSharedPreferences(Constants.AM_PREF, Context.MODE_PRIVATE);
		return pref;
	}


}
