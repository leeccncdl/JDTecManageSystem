package com.allrun.jdtecmanagesystem;

import android.app.Application;

public class App extends Application {
	
	public static final int HTTPTIMEOUT = 1000*30;
	
	public static final String USER_VALIDITY_FAILURE = "FAILURE"; 
	
	public static final String SERVEREXCEPTION = "访问服务器异常"; 
	
	public static final String PREFER_NAME = "JDTecManage";
	public static final String PREFER_ISSAVEUSERNAME = "isUserNameSave";
	public static final String PREFER_ISSAVEPASSWORD = "isPassWordSave";
	public static final String PREFER_SAVEDSERVERADDRESSINDEX = "loginAddressIndex";
	public static final String PREFER_SERVERADDRESS1 = "add0";
	public static final String PREFER_SERVERADDRESS2 = "add1";
	public static final String PREFER_SERVERADDRESS3 = "add2";
	public static final String PREFER_SERVERADDRESS4 = "add3";
	public static final String PREFER_USERNAME = "UserName";
	public static final String PREFER_PASSWORD = "Password";
	
	public static String HTTP = "http://";

	public static String BASE_NAMESPACE = "http://www.allrun.com.cn/"; //webservice namespace
	public static String BASE_DOMAIN; //服务器域名加端口号	test：192.168.102.10:812（813）
	public static String BASE_URL = "/webservice/WebService.asmx"; //服务地址
	public static String SERVER_URL = BASE_DOMAIN + BASE_URL; //服务地址全路径
	
	public static App JDTecApp;
	
	public static String appLoginUserCode = "";

	@Override
	public void onCreate() {
		JDTecApp = this;
		super.onCreate();
		AppLogger.config(getApplicationContext());
	}

}
