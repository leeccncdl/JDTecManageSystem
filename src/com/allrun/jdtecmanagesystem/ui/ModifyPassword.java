package com.allrun.jdtecmanagesystem.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;

public class ModifyPassword extends Activity implements OnClickListener {
	
	private ProgressDialog mProgress;

	private EditText mUsernameEdt;
	private EditText mOldpassEdt;
	private EditText mNewpassEdt;
	private EditText mConfirmpassEdt;
	
	private Button mConfirmBtn;
	private Button mCancelBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_pasword);
		
		findViewById();
		addListener();
	}
	private void findViewById() {
		mUsernameEdt = (EditText) findViewById(R.id.modify_pass_username_edt);
		mOldpassEdt = (EditText) findViewById(R.id.modify_pass_old_pass_edt);
		mNewpassEdt = (EditText) findViewById(R.id.modify_pass_new_pass_edt);
		mConfirmpassEdt = (EditText) findViewById(R.id.modify_pass_confirm_pass_edt);
		
		mConfirmBtn = (Button) findViewById(R.id.modify_pass_confirm_btn);
		mCancelBtn = (Button) findViewById(R.id.modify_pass_cancel_btn);
	}
	private void addListener() {
		mConfirmBtn.setOnClickListener(this);
		mCancelBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.modify_pass_confirm_btn:
			if(checkModifyPassInput()) {
				new ModifyPassTask().execute(mUsernameEdt.getText().toString().trim(),mOldpassEdt.getText().toString().trim(),mNewpassEdt.getText().toString().trim());
			}
			break;
		case R.id.modify_pass_cancel_btn:
			finish();
			break;

		default:
			break;
		}
		
	}

	/** 
	* @Title: checkModifyPass 
	* @Description: 检查修改密码输入的值
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	private boolean checkModifyPassInput() {
		if(mUsernameEdt.getText().toString().trim() == null || mUsernameEdt.getText().toString().trim().equals("")) {
			Toast.makeText(ModifyPassword.this, "请输入用户名", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(mOldpassEdt.getText().toString().trim() == null || mOldpassEdt.getText().toString().trim().equals("")) {
			Toast.makeText(ModifyPassword.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(mNewpassEdt.getText().toString().trim() == null || mNewpassEdt.getText().toString().trim().equals("")) {
			Toast.makeText(ModifyPassword.this, "请输入新密码", Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!mNewpassEdt.getText().toString().trim().equals(mConfirmpassEdt.getText().toString().trim())) {
			Toast.makeText(ModifyPassword.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	private class ModifyPassTask extends AsyncTask<String, Integer, String> {
		
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(ModifyPassword.this,
					"修改密码", "请稍候...", true, false);
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(String... params) {
			return SlaughterWs.updateUserPassword(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			mProgress.dismiss();
			super.onPostExecute(result);
			if(result.equals("SUCCESS")) {
				Toast.makeText(ModifyPassword.this, "修改密码成功", Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(ModifyPassword.this, result, Toast.LENGTH_LONG).show();
			}
		}
	}
}
