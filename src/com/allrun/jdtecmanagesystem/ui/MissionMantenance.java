package com.allrun.jdtecmanagesystem.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;
import com.allrun.jdtecmanagesystem.listener.BtnBluetoothPrintClickListener;
import com.allrun.jdtecmanagesystem.model.BaseResult;
import com.allrun.jdtecmanagesystem.model.MissionInfo;
import com.allrun.jdtecmanagesystem.view.FriendlyScrollView;

public class MissionMantenance extends Activity implements OnClickListener,OnGestureListener {

	private String mMissionGuid;
	
	private TextView mTaskNumTv;
	private TextView mCarNumTv;
//	private TextView mWorkTypeTv;
	private TextView mDriverNameTv;
	private TextView mTaskTypeTv;
	private TextView mDriverTelephoneTv;
//	private TextView mCarDevideNumTv;
//	private TextView mCarCardNumTv;
	
//	private TextView mEndDateTv;
//	private TextView mTaskDateTv;
//	private TextView mTaskDescriptionTv;
	private TextView mDeviceCompanyTv;
	private TextView mDeviceTypeTv;
	private TextView mDeviceNumTv;
	private TextView mCardNumTv;
//	private TextView mDistributeDescriptionTv;
	
	private TextView mAreaTv;
	private TextView mCompanyTv;
	
	private Button mPrintBtn;
	
	private ProgressDialog mProgress;
	
	private FriendlyScrollView mSv;
	private GestureDetector detector;
	
	private List<MissionInfo> mMissionInfoList = new ArrayList<MissionInfo>();
	
	//打印相关
	private MissionInfo mMissionInfo = null;
	BtnBluetoothPrintClickListener mBluetoothPrint = new BtnBluetoothPrintClickListener(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_mantenance);
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
		mTaskNumTv = (TextView) findViewById(R.id.mission_mantenance_task_num_tv);
		mCarNumTv = (TextView) findViewById(R.id.mission_mantenance_car_num_tv);
//		mWorkTypeTv = (TextView) findViewById(R.id.mission_mantenance_work_type_tv);
		mDriverNameTv = (TextView) findViewById(R.id.mission_mantenance_driver_name_tv);
		mTaskTypeTv = (TextView) findViewById(R.id.mission_mantenance_task_type_tv);
		mDriverTelephoneTv = (TextView) findViewById(R.id.mission_mantenance_driver_telephone_tv);
//		mCarDevideNumTv = (TextView) findViewById(R.id.mission_mantenance_car_device_num_tv);
//		mCarCardNumTv = (TextView) findViewById(R.id.mission_mantenance_car_card_num_tv);
//		mEndDateTv = (TextView) findViewById(R.id.mission_mantenance_end_date_tv);
//		mTaskDateTv = (TextView) findViewById(R.id.mission_mantenance_task_date_tv);
//		mTaskDescriptionTv = (TextView) findViewById(R.id.mission_mantenance_task_description_tv);
		mDeviceCompanyTv = (TextView) findViewById(R.id.mission_mantenance_device_company_tv);
		mDeviceTypeTv = (TextView) findViewById(R.id.mission_mantenance_device_type_tv);
		mDeviceNumTv = (TextView) findViewById(R.id.mission_mantenance_device_num_tv);
		mCardNumTv = (TextView) findViewById(R.id.mission_mantenance_card_num_tv);
//		mDistributeDescriptionTv = (TextView) findViewById(R.id.mission_mantenance_distribute_description_tv);
		
		mPrintBtn = (Button) findViewById(R.id.mission_mantenance_print_btn);
		
		mAreaTv = (TextView) findViewById(R.id.mission_mantenance_area_tv);
		mCompanyTv = (TextView) findViewById(R.id.mission_mantenance_company_tv);
		mSv = (FriendlyScrollView) findViewById(R.id.task_mantenance_sv);
	}

	private class QueryMissionDetailTask extends
			AsyncTask<String, Integer, BaseResult> {

		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(MissionMantenance.this,"正在查询打印任务信息", "请稍候...", true, false);
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
				Toast.makeText(MissionMantenance.this, "查询打印任务信息失败", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			mMissionInfoList = result.getMISSIONINFO();
			if (mMissionInfoList.size() != 0) {
				mMissionInfo = mMissionInfoList.get(0);
				mTaskNumTv.setText(mMissionInfoList.get(0).getMISSIONNO());
				mCarNumTv.setText(mMissionInfoList.get(0).getPLATENO());
				System.out.println(mMissionInfoList.get(0).getPLATENO());
//				mWorkTypeTv.setText(mMissionInfoList.get(0).getBUSINESSTYPE());
				mDriverNameTv.setText(mMissionInfoList.get(0).getDIRVER());
				mTaskTypeTv.setText(mMissionInfoList.get(0).getMISSIONTYPE());
				mDriverTelephoneTv.setText(mMissionInfoList.get(0).getDIRVERPHONE());
//				mCarDevideNumTv.setText(mMissionInfoList.get(0).getVEHICLEDEVICENUMBER());
//				mCarCardNumTv.setText(mMissionInfoList.get(0).getVEHICLECOMMUNICTIONCARD());
//				mEndDateTv.setText(mMissionInfoList.get(0).getEXPRATIONDATE());
//				mTaskDateTv.setText(mMissionInfoList.get(0).getMISSIONDATE());
//				mTaskDescriptionTv.setText(mMissionInfoList.get(0).getMISSIONNOTE());
				mDeviceCompanyTv.setText(mMissionInfoList.get(0).getDEVICEMANUFACTURE());
				mDeviceTypeTv.setText(mMissionInfoList.get(0).getDEVICETYPE());
				mDeviceNumTv.setText(mMissionInfoList.get(0).getDEVICENUMBER());
				mCardNumTv.setText(mMissionInfoList.get(0).getCOMMUNICTIONCARD());
				System.out.println(mMissionInfoList.get(0).getCOMMUNICTIONCARD());
//				mDistributeDescriptionTv.setText(mMissionInfoList.get(0).getALLOCATENOTE());
				mAreaTv.setText(mMissionInfoList.get(0).getREGION());
				mCompanyTv.setText(mMissionInfoList.get(0).getAFFILIATION());
			}
		}

	}

	private class PrintMantenanceTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(MissionMantenance.this,
					"正在提交打印请求", "请稍候...", true, false);
			super.onPreExecute();
		}
		
		
		@Override
		protected String doInBackground(String... params) {
			return SlaughterWs.printMissionInfoByAdd(mMissionGuid, App.appLoginUserCode);
		}
		@Override
		protected void onPostExecute(String result) {
			mProgress.dismiss();
			super.onPostExecute(result);
			if(result.equals("SUCCESS")) {
//				Toast.makeText(MissionMantenance.this, "打印请求返回成功", Toast.LENGTH_LONG).show();
				mBluetoothPrint.onClick(null);
			} else {
				Toast.makeText(MissionMantenance.this, result, Toast.LENGTH_LONG).show();
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
					Toast.makeText(MissionMantenance.this, "数据错误！", Toast.LENGTH_LONG).show();
				}
			} else {
				// 打开蓝牙失败
				Toast.makeText(MissionMantenance.this, "打开蓝牙失败！", Toast.LENGTH_LONG).show();
			}
		}else if(requestCode == App.REQUEST_PRINT){
			if(null != mMissionInfo){
				mBluetoothPrint.beginPrint(mMissionInfo);
			}
			else{
				Toast.makeText(MissionMantenance.this, "数据错误！", Toast.LENGTH_LONG).show();
			}
		}else if(requestCode == App.REQUEST_RESULT_PRINT){
			if(resultCode == -1){//打印完成返回-1.直接按返回键，返回为0
				//TODO 打印完成
//				new PrintMantenanceTask().execute("");
				setResult(RESULT_OK);
				finish();
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mission_mantenance_print_btn:
			//TODO 打印按钮响应
			new PrintMantenanceTask().execute("");
//			mBluetoothPrint.onClick(v);
			break;

		default:
			break;
		}
		
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
