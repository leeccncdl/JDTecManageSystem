package com.allrun.jdtecmanagesystem.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.AppLogger;
import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;
import com.allrun.jdtecmanagesystem.utils.Utility;

public class MainActivity extends Activity implements OnClickListener {
	
	private AppLogger log = AppLogger.getLogger(MainActivity.class);
	
	private ProgressDialog mProgress;
	
	private Button mLoginBtn;
	private EditText mUsernameEdt;
	private EditText mPasswordEdt;
	private TextView mModifyPassword;
	private CheckBox mSaveUsernameCb;
	private CheckBox mSavePasswordCb;
	
	private TextView mServerAdTv;
	
	private boolean mIsUserNameSave;
	private boolean mIsPasswordSave;
	private int loginServerAddIndex;
	private String mUsername;
	private String mPassword;
	private String ServerAddress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		if(!Utility.checkNetworkConnect(this)) {
			AlertDialog.Builder builder = new Builder(this);
			builder.setMessage("是否重新设置网络");
			builder.setTitle("网络未连接");
			builder.setPositiveButton("设置",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
						}

					});

			builder.setNegativeButton("取消", null);
			builder.create().show();
		}
		findViewById();
		addlistener();
		getSharePrefer();
		initView();
		  
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.login_login_btn:
			if(checkLoginInput()) {
				new LoginTask().execute(mUsernameEdt.getText().toString().trim(),mPasswordEdt.getText().toString().trim());
			}
			break;
		case R.id.login_modify_password_tv:
			Intent intent = new Intent(MainActivity.this,ModifyPassword.class);
			startActivity(intent);
			break;
		case R.id.login_server_drop_down_tv:
			Intent intent2 = new Intent(MainActivity.this,ServerList.class);
			startActivityForResult(intent2, 1);
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			getSharePrefer();
			initView();
		}
	}

	private void findViewById() {
		mLoginBtn = (Button) findViewById(R.id.login_login_btn);
		mUsernameEdt = (EditText) findViewById(R.id.login_username_edt);
		mPasswordEdt = (EditText) findViewById(R.id.login_password_edt);
		mModifyPassword = (TextView) findViewById(R.id.login_modify_password_tv);
		String strLink = "<a href="+""+">"+"修改密码"+"</a>";
		mModifyPassword.setText(Html.fromHtml(strLink));
		
		mSaveUsernameCb = (CheckBox) findViewById(R.id.login_save_username_cb);
		mSavePasswordCb = (CheckBox) findViewById(R.id.login_save_password_cb);
		
		mServerAdTv = (TextView) findViewById(R.id.login_server_drop_down_tv);
	}

	private void addlistener() {
		mLoginBtn.setOnClickListener(this);
		mModifyPassword.setOnClickListener(this);
		mServerAdTv.setOnClickListener(this);
	}

	private void getSharePrefer() {
		SharedPreferences loginPrefer = App.JDTecApp.getSharedPreferences(App.PREFER_NAME, MODE_PRIVATE);
		
		mIsUserNameSave = loginPrefer.getBoolean(App.PREFER_ISSAVEUSERNAME, false);
		mIsPasswordSave = loginPrefer.getBoolean(App.PREFER_ISSAVEPASSWORD, false);
		loginServerAddIndex = loginPrefer.getInt(App.PREFER_SAVEDSERVERADDRESSINDEX, 0);
		
		switch (loginServerAddIndex) {
		case 0:
			ServerAddress = loginPrefer.getString(App.PREFER_SERVERADDRESS1, "");
			break;
		case 1:
			ServerAddress = loginPrefer.getString(App.PREFER_SERVERADDRESS2, "");
			break;
		case 2:
			ServerAddress = loginPrefer.getString(App.PREFER_SERVERADDRESS3, "");
			break;
		case 3:
			ServerAddress = loginPrefer.getString(App.PREFER_SERVERADDRESS4, "");
			break;

		default:
			break;
		}
		
		mUsername = loginPrefer.getString(App.PREFER_USERNAME, "");
		mPassword = loginPrefer.getString(App.PREFER_PASSWORD, "");
		
		if(log.isDebugEnabled()) {
			log.debug("显示之前保存的服务器地址，索引为：" +loginServerAddIndex);
			log.debug("服务器地址为：" +ServerAddress);
		}
		
	}

	/** 
	* @Title: initView 
	* @Description: 初始化页面显示，是否保存用户名密码，是否有已经保存的Ip地址
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void initView() {
		if(mIsUserNameSave) {
			mSaveUsernameCb.setChecked(true);
			mUsernameEdt.setText(mUsername);
		}
		if(mIsPasswordSave) {
			mSavePasswordCb.setChecked(true);
			mPasswordEdt.setText(mPassword);
		}
		mServerAdTv.setText(ServerAddress);
		App.BASE_DOMAIN = App.HTTP+ServerAddress;
		App.SERVER_URL = App.BASE_DOMAIN +App.BASE_URL;
		
	}

	/** 
	* @Title: checkInput 
	* @Description: 检查输入的用户名和密码 
	* @return boolean    检查是否通过 
	* @throws 
	*/
	private boolean checkLoginInput(){
		if(mUsernameEdt.getText().toString().trim() == null || mUsernameEdt.getText().toString().trim().equals("")) {
			Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
			return false;
		} 
		if(mPasswordEdt.getText().toString().trim() == null || mPasswordEdt.getText().toString().trim().equals("")) {
			Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(ServerAddress.equals("")) {
			
			Toast.makeText(MainActivity.this, "请输入服务器地址", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}
	
	
	
	/** 
	* @ClassName: LoginTask 
	* @Description: 异步的登陆任务
	* @author 香格李拉   limingdl@yeah.net
	* @date 2013-6-7 下午11:30:05  
	*/
	private class LoginTask extends AsyncTask<String, Integer, String> {
		
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(MainActivity.this,
					"正在登陆", "请稍候...", true, false);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			return SlaughterWs.checkLogin(params[0], params[1]);
		}

		@Override
		protected void onPostExecute(String result) {
			mProgress.dismiss();
			super.onPostExecute(result);
			if(result.equals("SUCCESS")) {
				Intent intent = new Intent(MainActivity.this,MissionList.class);
				saveSharePreference();
				finish();
				startActivity(intent);
			} else {
				Toast.makeText(MainActivity.this, "登陆失败", Toast.LENGTH_LONG).show();
			}
		}
	}
	

	
	private void saveSharePreference() {
		SharedPreferences loginPrefer = App.JDTecApp.getSharedPreferences(App.PREFER_NAME, MODE_PRIVATE);
		Editor editor = loginPrefer.edit();
		if(mSaveUsernameCb.isChecked()) {
			editor.putBoolean(App.PREFER_ISSAVEUSERNAME, true);
			editor.putString(App.PREFER_USERNAME, mUsernameEdt.getText().toString().trim());
		} else {
			editor.putBoolean(App.PREFER_ISSAVEUSERNAME, false);
			editor.putString(App.PREFER_USERNAME, "");
			
		}
		if(mSavePasswordCb.isChecked()) {
			editor.putBoolean(App.PREFER_ISSAVEPASSWORD, true);
			editor.putString(App.PREFER_PASSWORD, mPasswordEdt.getText().toString().trim());
		} else {
			editor.putBoolean(App.PREFER_ISSAVEPASSWORD, false);
			editor.putString(App.PREFER_PASSWORD, "");
		}
		editor.commit();
	}
}
