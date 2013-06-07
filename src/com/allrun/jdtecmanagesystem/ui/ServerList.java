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
	private String[] mServerAddressList = new String[4];
	
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
	
	private void getServerAddressSharePrefer() {
		SharedPreferences serverPrefer = App.JDTecApp.getSharedPreferences(App.PREFER_NAME, MODE_PRIVATE);
		mServerAddressList[0] = new String(serverPrefer.getString(App.PREFER_SERVERADDRESS1, ""));
		mServerAddressList[1] = new String(serverPrefer.getString(App.PREFER_SERVERADDRESS2, ""));
		mServerAddressList[2] = new String(serverPrefer.getString(App.PREFER_SERVERADDRESS3, ""));
		mServerAddressList[3] = new String(serverPrefer.getString(App.PREFER_SERVERADDRESS4, ""));
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
		
		mAddress1Edt.setText(mServerAddressList[0]);
		mAddress2Edt.setText(mServerAddressList[1]);
		mAddress3Edt.setText(mServerAddressList[2]);
		mAddress4Edt.setText(mServerAddressList[3]);
		
	}
	private void addListener() {
		mConfirm1Btn.setOnClickListener(this);
		mConfirm2Btn.setOnClickListener(this);
		mConfirm3Btn.setOnClickListener(this);
		mConfirm4Btn.setOnClickListener(this);
	}


	/** 
	* @Title: saveServerAddress 
	* @Description: 点击选定后，保存服务器地址
	* @param @param key
	* @param @param address
	* @param @param loginAddressIndex    
	* @return void    返回类型 
	* @throws 
	*/
	private void saveServerAddress(String key,String address,int loginAddressIndex) {
		if(address == null || address.equals("")) {
			Toast.makeText(ServerList.this, "服务器地址不能为空！", Toast.LENGTH_LONG).show();
		} else {
			
			SharedPreferences serverPrefer = App.JDTecApp.getSharedPreferences(App.PREFER_NAME, MODE_PRIVATE);
			Editor edit = serverPrefer.edit();
			edit.putString(key, address);
			edit.putInt(App.PREFER_SAVEDSERVERADDRESSINDEX, loginAddressIndex);
			edit.commit();
		}
	}



	@Override
	public void onClick(View v) {
		String key = "";
		String value = "";
		int firstAddress = 0;
		switch (v.getId()) {
		case R.id.server_list_1_btn:
			key = App.PREFER_SERVERADDRESS1;
			value = mAddress1Edt.getText().toString().trim();
			firstAddress = 0;
			break;
		case R.id.server_list_2_btn:
			key = App.PREFER_SERVERADDRESS2;
			value = mAddress2Edt.getText().toString().trim();
			firstAddress = 1;
			
			break;
		case R.id.server_list_3_btn:
			key = App.PREFER_SERVERADDRESS3;
			value = mAddress3Edt.getText().toString().trim();
			firstAddress = 2;
			break;
		case R.id.server_list_4_btn:
			key = App.PREFER_SERVERADDRESS4;
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
