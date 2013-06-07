package com.allrun.jdtecmanagesystem;

import android.app.Application;

import com.allrun.jdtecmanagesystem.model.User;

public class App extends Application {
	
	
	public static final String DEBUG_TAG = "JDTec"; 
	public static final String USER_VALIDITY_SUCCESS = "SUCCESS"; 
	public static final String USER_VALIDITY_FAILURE = "FAILURE"; 
	public static final String USER_VALIDITY_ACCOUNT_ERROR = "ACCOUNT_ERROR";
	
	public static final String PREF_SETTING = "app_setting";
	public static final String PREF_USER_CODE = "user_code";
	public static final String PREF_PASSWORD = "password";
	public static final String PREF_SERVER_DOMAIN = "server_domain";
	
	public static String BASE_NAMESPACE = "http://www.allrun.com.cn/"; 

	public static String BASE_DOMAIN; //服务器域名加端口号
	public static String BASE_URL = "/webservice/WebService.asmx"; //服务器地址
	
	public static String SERVER_URL = BASE_DOMAIN + BASE_URL; //服务器地址////////////////
	
//	public static final String HTTP_PREFIX = "http://";
//	public static final String HTTPS_PREFIX = "https://";
	
	public static User CURR_USER = null;
	
	public static App app;
	
	public static String UserCode = "";

	@Override
	public void onCreate() {
		app = this;
		super.onCreate();
		AppLogger.config(getApplicationContext());
	}

}
