package com.allrun.jdtecmanagesystem.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.R;

public class ServerList extends Activity implements OnClickListener {
	private String[] s = new String[4];
	
	private EditText mAddress1Edt;
	private EditText mAddress2Edt;
	private EditText mAddress3Edt;
	private EditText mAddress4Edt;
	
	private Button mConfirm1Btn;
	private Button mConfirm2Btn;
	private Button mConfirm3Btn;
	private Button mConfirm4Btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.server_list);
		getServerAddressSharePrefer();
		
		findViewById();
		addListener();

		
		
	}
	
	private void findViewById() {
		mAddress1Edt = (EditText) findViewById(R.id.server_list_1_edt);
		mAddress2Edt = (EditText) findViewById(R.id.server_list_2_edt);
		mAddress3Edt = (EditText) findViewById(R.id.server_list_3_edt);
		mAddress4Edt = (EditText) findViewById(R.id.server_list_4_edt);
		
		mConfirm1Btn = (Button) findViewById(R.id.server_list_1_btn);
		mConfirm2Btn = (Button) findViewById(R.id.server_list_2_btn);
		mConfirm3Btn = (Button) findViewById(R.id.server_list_3_btn);
		mConfirm4Btn = (Button) findViewById(R.id.server_list_4_btn);
		
		mAddress1Edt.setText(s[0]);
		mAddress2Edt.setText(s[1]);
		mAddress3Edt.setText(s[2]);
		mAddress4Edt.setText(s[3]);
		
	}
	private void addListener() {
		mConfirm1Btn.setOnClickListener(this);
		mConfirm2Btn.setOnClickListener(this);
		mConfirm3Btn.setOnClickListener(this);
		mConfirm4Btn.setOnClickListener(this);
	}


	private void saveServerAddress(String key,String address,int loginAddressIndex) {
		if(address == null || address.equals("")) {
			Toast.makeText(ServerList.this, "服务器地址不能为空！", Toast.LENGTH_LONG).show();
		} else {
			
			SharedPreferences serverPrefer = App.app.getSharedPreferences("JDTecManage", MODE_PRIVATE);
			Editor edit = serverPrefer.edit();
			edit.putString(key, address);
			edit.putInt("loginAddressIndex", loginAddressIndex);
			edit.commit();
		}
	}

	private void getServerAddressSharePrefer() {
		SharedPreferences serverPrefer = App.app.getSharedPreferences("JDTecManage", MODE_PRIVATE);
		s[0] = new String(serverPrefer.getString("add0", ""));
		s[1] = new String(serverPrefer.getString("add1", ""));
		s[2] = new String(serverPrefer.getString("add2", ""));
		s[3] = new String(serverPrefer.getString("add3", ""));
	}

	@Override
	public void onClick(View v) {
		String key = "";
		String value = "";
		int firstAddress = 0;
		switch (v.getId()) {
		case R.id.server_list_1_btn:
			key = "add0";
			value = mAddress1Edt.getText().toString().trim();
			firstAddress = 0;
			break;
		case R.id.server_list_2_btn:
			key = "add1";
			value = mAddress2Edt.getText().toString().trim();
			firstAddress = 1;
			
			break;
		case R.id.server_list_3_btn:
			key = "add2";
			value = mAddress3Edt.getText().toString().trim();
			firstAddress = 2;
			break;
		case R.id.server_list_4_btn:
			key = "add3";
			value = mAddress4Edt.getText().toString().trim();
			firstAddress = 3;
			
			break;

		default:
			break;
		}
		saveServerAddress(key,value,firstAddress);
		setResult(RESULT_OK);
		finish();
	}
}
