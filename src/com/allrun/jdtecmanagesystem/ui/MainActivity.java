package com.allrun.jdtecmanagesystem.ui;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;

public class MainActivity extends Activity implements OnClickListener {
	
//	private AppLogger log = AppLogger.getLogger(MainActivity.class);
	
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
	
//	private Spinner mServerSp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		findViewById();
		addlistener();
		getSharePrefer();
		initView();
		  
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


	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.login_login_btn:
			if(checkLoginInput()) {
				new LoginTask().execute(mUsernameEdt.getText().toString().trim(),mPasswordEdt.getText().toString().trim());
			}
			break;
		//TODO 跳转到修改密码页面
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

	/** 
	* @Title: checkInput 
	* @Description: 检查输入的用户名和密码 
	* @param @return    设定文件 
	* @return boolean    返回类型 
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
		
		return true;
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
		if(ServerAddress.equals("")) {
			ServerAddress = getResources().getString(R.string.default_server);
			mServerAdTv.setText(ServerAddress);
			App.BASE_DOMAIN = ServerAddress;
			App.SERVER_URL = App.BASE_DOMAIN +App.BASE_URL;
		} else {
			mServerAdTv.setText(ServerAddress);
			App.BASE_DOMAIN = ServerAddress;
			App.SERVER_URL = App.BASE_DOMAIN +App.BASE_URL;
		}
	}
	
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
	
	private void getSharePrefer() {
		SharedPreferences loginPrefer = App.app.getSharedPreferences("JDTecManage", MODE_PRIVATE);
		
		mIsUserNameSave = loginPrefer.getBoolean("isUserNameSave", false);
		mIsPasswordSave = loginPrefer.getBoolean("isPassWordSave", false);
		loginServerAddIndex = loginPrefer.getInt("loginAddressIndex", 0);
		
		switch (loginServerAddIndex) {
		case 0:
			ServerAddress = loginPrefer.getString("add0", "");
			break;
		case 1:
			ServerAddress = loginPrefer.getString("add1", "");
			break;
		case 2:
			ServerAddress = loginPrefer.getString("add2", "");
			break;
		case 3:
			ServerAddress = loginPrefer.getString("add3", "");
			break;

		default:
			break;
		}
		
		mUsername = loginPrefer.getString("UserName", "");
		mPassword = loginPrefer.getString("Password", "");
		
	}
	
	private void saveSharePreference() {
		SharedPreferences loginPrefer = App.app.getSharedPreferences("JDTecManage", MODE_PRIVATE);
		Editor editor = loginPrefer.edit();
		if(mSaveUsernameCb.isChecked()) {
			editor.putBoolean("isUserNameSave", true);
			editor.putString("UserName", mUsernameEdt.getText().toString().trim());
		} else {
			editor.putBoolean("isUserNameSave", false);
			editor.putString("UserName", "");
			
		}
		if(mSavePasswordCb.isChecked()) {
			editor.putBoolean("isPassWordSave", true);
			editor.putString("Password", mPasswordEdt.getText().toString().trim());
		} else {
			editor.putBoolean("isPassWordSave", false);
			editor.putString("Password", "");
		}
		editor.commit();
	}
}
