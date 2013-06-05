package com.allrun.jdtecmanagesystem.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.allrun.jdtecmanagesystem.AppLogger;
import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;
import com.allrun.jdtecmanagesystem.model.BaseResult;
import com.allrun.jdtecmanagesystem.model.Mission;

public class MainActivity extends Activity implements OnClickListener {
	
	private AppLogger log = AppLogger.getLogger(MainActivity.class);
	
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
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// {"USERCODE":"74df9594c76f4245b918ffa9b97c1188", "LOGIN":"SUCCESS"}
//				SlaughterWs.checkLogin("abc", "123");
//				SlaughterWs.updateUserPassword("admin", "123456", "654321");
				BaseResult result = SlaughterWs.getMissionList("74df9594c76f4245b918ffa9b97c1188");
//				SlaughterWs.getMissionInfo("sfs", "sfaf");
//				SlaughterWs.printMissionInfoByAdd("safsa", "sfasf");
				mCompanies = result.getMISSIONLIST();
			}
		}).start();
		
		  
	}

	private void findViewById() {
		mLoginBtn = (Button) findViewById(R.id.login_login_btn);
		mUsernameEdt = (EditText) findViewById(R.id.login_username_edt);
		mPasswordEdt = (EditText) findViewById(R.id.login_password_edt);
		mModifyPassword = (TextView) findViewById(R.id.login_modify_password_tv);
		mSaveUsernameCb = (CheckBox) findViewById(R.id.login_save_username_cb);
		mSavePasswordCb = (CheckBox) findViewById(R.id.login_save_password_cb);
		mServerSp = (Spinner) findViewById(R.id.login_server_sp);
	}

	private void addlistener() {
		mLoginBtn.setOnClickListener(this);
		mModifyPassword.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//TODO 登陆
		case R.id.login_login_btn:
			
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
		return false;
	}
	
	/** 
	* @Title: saveLoginInformation 
	* @Description: 保存用户名和密码（如果用户选择记住）
	* @param     设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	private void saveLoginInformation() {
		
	}
	
}
