package com.allrun.jdtecmanagesystem.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

@SuppressLint("SimpleDateFormat")
public class Utility {

	public static boolean checkNetworkConnect(Activity act) {
		ConnectivityManager manager = (ConnectivityManager) act
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkinfo = manager.getActiveNetworkInfo();
		if (networkinfo == null || !networkinfo.isAvailable()) {
			return false;
		}
		return true;
	}
	
	public static String covertDateToString(Date date, String format){
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}
}
