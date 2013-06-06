package com.allrun.jdtecmanagesystem.ui;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.allrun.jdtecmanagesystem.AppLogger;
import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;
import com.allrun.jdtecmanagesystem.model.Mission;

public class MainActivity extends Activity implements OnClickListener {
	
//	private AppLogger log = AppLogger.getLogger(MainActivity.class);
	
	private ProgressDialog mProgress;
	
	private Button mLoginBtn;
	private EditText mUsernameEdt;
	private EditText mPasswordEdt;
	private TextView mModifyPassword;
	private CheckBox mSaveUsernameCb;
	private CheckBox mSavePasswordCb;
	
	private Spinner mServerSp;

	private List<Mission> mCompanies;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		findViewById();
		addlistener();
		initView();
		
		//For test
//			float scale = MainActivity.this.getResources().getDisplayMetrics().density;
//			int p = (int) (48 * scale + 0.5f);
//			System.out.println("pppppppppppppp:" +p); 72px
		//测试输出手机的屏幕信息
/*		DisplayMetrics dm = new DisplayMetrics();  
		dm = getResources().getDisplayMetrics();  
		float density  = dm.density;        // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）  
		int densityDPI = dm.densityDpi;     // 屏幕密度（每寸像素：120/160/240/320）  
		float xdpi = dm.xdpi;             
		float ydpi = dm.ydpi;  
		int screenWidth  = dm.widthPixels;      // 屏幕宽（像素，如：480px）  
		int screenHeight = dm.heightPixels;     // 屏幕高（像素，如：800px）  
		if(log.isDebugEnabled()) {
			
			log.debug("xdpi=" + xdpi + "; ydpi=" + ydpi);  
			log.debug("density=" + density + "; densityDPI=" + densityDPI);  
			log.debug("screenWidth=" + screenWidth + "; screenHeight=" + screenHeight);
		}*/
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// {"USERCODE":"74df9594c76f4245b918ffa9b97c1188", "LOGIN":"SUCCESS"}
////				SlaughterWs.checkLogin("abc", "123");
////				SlaughterWs.updateUserPassword("admin", "123456", "654321");
//				BaseResult result = SlaughterWs.getMissionList("74df9594c76f4245b918ffa9b97c1188");
////				SlaughterWs.getMissionInfo("sfs", "sfaf");
////				SlaughterWs.printMissionInfoByAdd("safsa", "sfasf");
//				mCompanies = result.getMISSIONLIST();
//			}
//		}).start();
		
		  
	}

	private void findViewById() {
		mLoginBtn = (Button) findViewById(R.id.login_login_btn);
		mUsernameEdt = (EditText) findViewById(R.id.login_username_edt);
		mPasswordEdt = (EditText) findViewById(R.id.login_password_edt);
		mModifyPassword = (TextView) findViewById(R.id.login_modify_password_tv);
		mSaveUsernameCb = (CheckBox) findViewById(R.id.login_save_username_cb);
		mSavePasswordCb = (CheckBox) findViewById(R.id.login_save_password_cb);
//		mServerSp = (Spinner) findViewById(R.id.login_server_sp);
	}

	private void addlistener() {
		mLoginBtn.setOnClickListener(this);
		mModifyPassword.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.login_login_btn:
			if(checkLoginInput()) {
				new LoginTask().execute("");
			}
			break;
		//TODO 跳转到修改密码页面
		case R.id.login_modify_password_tv:
			Intent intent = new Intent(MainActivity.this,ModifyPassword.class);
			startActivity(intent);
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
		if(mUsernameEdt.getText().toString() == null || mUsernameEdt.getText().toString().equals("")) {
			Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
			return false;
		} 
		if(mPasswordEdt.getText().toString() == null || mPasswordEdt.getText().toString().equals("")) {
			Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}
	
	/** 
	* @Title: saveLoginInformation 
	* @Description: 保存用户名和密码（如果用户选择记住）
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void saveLoginInformation() {
		if(mSaveUsernameCb.isChecked()) {
			//保存用户名
		}
		if(mSavePasswordCb.isChecked()) {
			//保存密码
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
			return SlaughterWs.checkLogin("abc", "123");
		}

		@Override
		protected void onPostExecute(String result) {
			mProgress.dismiss();
			super.onPostExecute(result);
			if(result.equals("SUCCESS")) {
				Intent intent = new Intent(MainActivity.this,MissionList.class);
				finish();
				startActivity(intent);
			} else {
				Toast.makeText(MainActivity.this, "登陆失败", Toast.LENGTH_LONG).show();
			}
		}
	}
	
}
