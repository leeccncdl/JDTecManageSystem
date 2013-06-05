package com.allrun.jdtecmanagesystem.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.allrun.jdtecmanagesystem.R;

public class ModifyPassword extends Activity implements OnClickListener {

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
			
			break;
		case R.id.modify_pass_cancel_btn:
			
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
		return false;
	}
}
