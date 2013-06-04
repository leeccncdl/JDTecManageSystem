package com.allrun.jdtecmanagesystem;

import android.app.Application;

public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		AppLogger.config(getApplicationContext());
	}

}
