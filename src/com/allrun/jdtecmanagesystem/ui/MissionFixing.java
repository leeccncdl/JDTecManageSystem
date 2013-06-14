package com.allrun.jdtecmanagesystem.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;
import com.allrun.jdtecmanagesystem.listener.BtnBluetoothPrintClickListener;
import com.allrun.jdtecmanagesystem.model.BaseResult;
import com.allrun.jdtecmanagesystem.model.MissionInfo;
import com.allrun.jdtecmanagesystem.view.FriendlyScrollView;

public class MissionFixing extends Activity implements OnClickListener ,OnGestureListener {

	private String mMissionGuid;
	
	private TextView mTaskNumTv;
	private TextView mCarNumTv;
//	private TextView mWorkTypeTv
	private TextView mDriverNameTv;
	private TextView mTaskTypeTv;
	private TextView mDriverTelephoneTv;
	private TextView mCarDevideNumTv;
	private TextView mCarCardNumTv;
	
	private EditText mDeviceCompanyEdt;
	private EditText mDeviceTypeEdt;
	private EditText mDeviceNumEdt;
	private EditText mCardNumEdt;
	
	private TextView mAreaTv;
	private TextView mCompanyTv;
	private TextView mCarDeviceCompanyTv;
	private TextView mCarDeviceTypeNumTv;
	
	private Button mPrintBtn;
	
	private FriendlyScrollView mSv;
	private GestureDetector detector;
	private ProgressDialog mProgress;
	
	private List<MissionInfo> mMissionInfoList = new ArrayList<MissionInfo>();
	
	//打印相关
	private MissionInfo mMissionInfo = null;
	BtnBluetoothPrintClickListener mBluetoothPrint = new BtnBluetoothPrintClickListener(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_fixing);
		detector = new GestureDetector(this);
		findViewById();
		addListener();

		mMissionGuid = getIntent().getStringExtra(MissionDetail.MISSIONGUID);

		new QueryMissionDetailTask().execute("");
	}

	private void addListener() {
		mPrintBtn.setOnClickListener(this);
		mSv.setGestureDetector(detector);
	}

	private void findViewById() {
		mTaskNumTv = (TextView) findViewById(R.id.mission_fixing_task_num_tv);
		mCarNumTv = (TextView) findViewById(R.id.mission_fixing_car_num_tv);
//		mWorkTypeTv = (TextView) findViewById(R.id.mission_fixing_work_type_tv);
		mDriverNameTv = (TextView) findViewById(R.id.mission_fixing_driver_name_tv);
		mTaskTypeTv = (TextView) findViewById(R.id.mission_fixing_task_type_tv);
		mDriverTelephoneTv = (TextView) findViewById(R.id.mission_fixing_driver_telephone_tv);
		mCarDevideNumTv = (TextView) findViewById(R.id.mission_fixing_car_device_num_tv);
		mCarCardNumTv = (TextView) findViewById(R.id.mission_fixing_car_card_num_tv);
	
		mDeviceCompanyEdt = (EditText) findViewById(R.id.mission_fixing_device_company_edt);
		mDeviceTypeEdt = (EditText) findViewById(R.id.mission_fixing_device_type_edt);
		mDeviceNumEdt = (EditText) findViewById(R.id.mission_fixing_devece_num_edt);
		mCardNumEdt = (EditText) findViewById(R.id.mission_fixing_card_num_edt);
		mPrintBtn = (Button) findViewById(R.id.mission_fixing_print_btn);
		
		mAreaTv = (TextView) findViewById(R.id.mission_fixing_area_tv);
		mCompanyTv = (TextView) findViewById(R.id.mission_fixing_company_tv);
		mCarDeviceCompanyTv = (TextView) findViewById(R.id.mission_fixing_company_tv);
		mCarDeviceTypeNumTv = (TextView) findViewById(R.id.mission_fixing_car_device_typenum_tv);
		mSv = (FriendlyScrollView) findViewById(R.id.task_fixing_scv);
	
	}

	private class QueryMissionDetailTask extends
			AsyncTask<String, Integer, BaseResult> {

		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(MissionFixing.this,"正在查询打印任务信息", "请稍候...", true, false);
			super.onPreExecute();
		}

		@Override
		protected BaseResult doInBackground(String... params) {
			 return SlaughterWs.getMissionInfo(mMissionGuid, App.appLoginUserCode);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			 mProgress.dismiss();
			 super.onPostExecute(result);
			if (result == null) {
				Toast.makeText(MissionFixing.this, "查询打印任务信息失败", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			mMissionInfoList = result.getMISSIONINFO();
			if (mMissionInfoList.size() != 0) {
				mMissionInfo = mMissionInfoList.get(0);
				mTaskNumTv.setText(mMissionInfoList.get(0).getMISSIONNO());
				mCarNumTv.setText(mMissionInfoList.get(0).getPLATENO());
//				mWorkTypeTv.setText(mMissionInfoList.get(0).getBUSINESSTYPE());
				mDriverNameTv.setText(mMissionInfoList.get(0).getDIRVER());
				mTaskTypeTv.setText(mMissionInfoList.get(0).getMISSIONTYPE());
				mDriverTelephoneTv.setText(mMissionInfoList.get(0).getDIRVERPHONE());
				mCarDevideNumTv.setText(mMissionInfoList.get(0)
						.getVEHICLEDEVICENUMBER());
				mCarCardNumTv.setText(mMissionInfoList.get(0)
						.getVEHICLECOMMUNICTIONCARD());
				
				mAreaTv.setText(mMissionInfoList.get(0).getREGION());
				mCompanyTv.setText(mMissionInfoList.get(0).getAFFILIATION());
				mCarDeviceCompanyTv.setText(mMissionInfoList.get(0).getVEHICLECODEVICEMANUFACTURE());
				mCarDeviceTypeNumTv.setText(mMissionInfoList.get(0).getVEHICLECODEVICETYPE());
				
				if(mMissionInfoList.get(0).getDEVICEMANUFACTURE()!=null && !mMissionInfoList.get(0).getDEVICEMANUFACTURE().equals("")) {
					mDeviceCompanyEdt.setText(mMissionInfoList.get(0).getDEVICEMANUFACTURE());
				}
				if(mMissionInfoList.get(0).getDEVICETYPE()!=null && !mMissionInfoList.get(0).getDEVICETYPE().equals("")) {
					mDeviceTypeEdt.setText(mMissionInfoList.get(0).getDEVICETYPE());
				}
				if(mMissionInfoList.get(0).getDEVICENUMBER()!=null && !mMissionInfoList.get(0).getDEVICENUMBER().equals("")) {
					mDeviceNumEdt.setText(mMissionInfoList.get(0).getDEVICENUMBER());
				}
				if(mMissionInfoList.get(0).getCOMMUNICTIONCARD()!=null && !mMissionInfoList.get(0).getCOMMUNICTIONCARD().equals("")) {
					mCardNumEdt.setText(mMissionInfoList.get(0).getCOMMUNICTIONCARD());
				}
			}
		}

	}
	
	private class PrintFixingTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(MissionFixing.this,
					"正在提交打印请求", "请稍候...", true, false);
			super.onPreExecute();
		}
		
		
		@Override
		protected String doInBackground(String... params) {
			return SlaughterWs.printMissionInfoByModify(mMissionGuid, App.appLoginUserCode, params[0], params[1], params[2], params[3]);
		}
		@Override
		protected void onPostExecute(String result) {
			mProgress.dismiss();
			super.onPostExecute(result);
			if(result.equals("SUCCESS")) {
//				Toast.makeText(MissionFixing.this, "打印请求返回成功", Toast.LENGTH_LONG).show();
				mBluetoothPrint.onClick(null);
			} else {
				Toast.makeText(MissionFixing.this, result, Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == App.REQUEST_ENABLE) {
			// 请求为 "打开蓝牙"
			if (resultCode == RESULT_OK) {
				// 打开蓝牙成功
				if(null != mMissionInfo){
					mBluetoothPrint.beginPrint(mMissionInfo);
				}
				else{
					Toast.makeText(MissionFixing.this, "数据错误！", Toast.LENGTH_LONG).show();
				}
			} else {
				// 打开蓝牙失败
				Toast.makeText(MissionFixing.this, "打开蓝牙失败！", Toast.LENGTH_LONG).show();
			}
		}else if(requestCode == App.REQUEST_PRINT){
			if(null != mMissionInfo){
				mBluetoothPrint.beginPrint(mMissionInfo);
			}
			else{
				Toast.makeText(MissionFixing.this, "数据错误！", Toast.LENGTH_LONG).show();
			}
		}else if(requestCode == App.REQUEST_RESULT_PRINT){
			if(resultCode == -1){//打印完成返回-1.直接按返回键，返回为0
				setResult(RESULT_OK);
				finish();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mission_fixing_print_btn:
			if(checkInput()) {
				//TODO 打印按钮响应
				new PrintFixingTask().execute(mDeviceCompanyEdt.getText().toString().trim(),
						mDeviceTypeEdt.getText().toString().trim(),
						mDeviceNumEdt.getText().toString().trim(),
						mCardNumEdt.getText().toString().trim());
				
			}
			break;

		default:
			break;
		}
		
	}
	
	private boolean checkInput() {
		if(mDeviceCompanyEdt.getText().toString().trim().equals("") || 
				mDeviceTypeEdt.getText().toString().trim().equals("")||
				mDeviceNumEdt.getText().toString().trim().equals("")||
				mCardNumEdt.getText().toString().trim().equals("")) {
			Toast.makeText(MissionFixing.this, "输入不能为空", Toast.LENGTH_LONG).show();
			return false;
		}
		
		//将最新输入内容更新到model中，便于后面打印
		mMissionInfo.setDEVICEMANUFACTURE(mDeviceCompanyEdt.getText().toString().trim());
		mMissionInfo.setDEVICETYPE(mDeviceTypeEdt.getText().toString().trim());
		mMissionInfo.setDEVICENUMBER(mDeviceNumEdt.getText().toString().trim());
		mMissionInfo.setCOMMUNICTIONCARD(mCardNumEdt.getText().toString().trim());
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if(e1.getX()-e2.getX()<0 && Math.abs(e1.getX()-e2.getX())>100){
			finish();
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}
