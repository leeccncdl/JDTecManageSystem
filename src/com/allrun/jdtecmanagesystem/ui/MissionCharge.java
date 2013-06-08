package com.allrun.jdtecmanagesystem.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;
import com.allrun.jdtecmanagesystem.model.BaseResult;
import com.allrun.jdtecmanagesystem.model.MissionInfo;

public class MissionCharge extends Activity implements OnClickListener {

	private String mMissionGuid;
	
	private TextView mTaskNumTv;
	private TextView mCarNumTv;
//	private TextView mWorkTypeTv;
//	private TextView mDriverNameTv;
	private TextView mTaskTypeTv;
//	private TextView mDriverTelephoneTv;
//	private TextView mCarDevideNumTv;
//	private TextView mCarCardNumTv;
	
	private TextView mCompanyTv;
	private EditText mChargeEndDateEdt;
	private EditText mCostEdt;
	
	private Button mChargeDateBtn;
	private Button mPickDateBtn;
	
	private ProgressDialog mProgress;
	
	private List<MissionInfo> mMissionInfoList = new ArrayList<MissionInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_charge);

		findViewById();
		addListener();

		mMissionGuid = getIntent().getStringExtra(MissionDetail.MISSIONGUID);

		new QueryMissionDetailTask().execute("");
	}

	private void addListener() {
		mChargeDateBtn.setOnClickListener(this);
		mPickDateBtn.setOnClickListener(this);
	}

	private void findViewById() {
		mTaskNumTv = (TextView) findViewById(R.id.mission_charge_task_num_tv);
		mCarNumTv = (TextView) findViewById(R.id.mission_charge_car_num_tv);
//		mWorkTypeTv = (TextView) findViewById(R.id.mission_charge_work_type_tv);
//		mDriverNameTv = (TextView) findViewById(R.id.mission_charge_driver_name_tv);
		mTaskTypeTv = (TextView) findViewById(R.id.mission_charge_task_type_tv);
//		mDriverTelephoneTv = (TextView) findViewById(R.id.mission_charge_driver_telephone_tv);
//		mCarDevideNumTv = (TextView) findViewById(R.id.mission_charge_car_device_num_tv);
//		mCarCardNumTv = (TextView) findViewById(R.id.mission_charge_car_card_num_tv);
		mCompanyTv = (TextView) findViewById(R.id.mission_charge_company_tv);
		mChargeEndDateEdt = (EditText) findViewById(R.id.mission_charge_end_dstring_edt);
		mChargeEndDateEdt.setKeyListener(null);
		mCostEdt = (EditText) findViewById(R.id.mission_charge_cost_edt);
		mChargeDateBtn = (Button) findViewById(R.id.mission_charge_print_btn);
		mPickDateBtn = (Button) findViewById(R.id.mission_charge_pick_date_btn);
	}

	private class QueryMissionDetailTask extends
			AsyncTask<String, Integer, BaseResult> {

		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(MissionCharge.this,"正在查询打印任务信息", "请稍候...", true, false);
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
				Toast.makeText(MissionCharge.this, "查询打印任务信息失败", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			mMissionInfoList = result.getMISSIONINFO();
			if (mMissionInfoList.size() != 0) {

				mTaskNumTv.setText(mMissionInfoList.get(0).getMISSIONNO());
				mCarNumTv.setText(mMissionInfoList.get(0).getPLATENO());
//				mWorkTypeTv.setText(mMissionInfoList.get(0).getBUSINESSTYPE());
//				mDriverNameTv.setText(mMissionInfoList.get(0).getDIRVER());
				mTaskTypeTv.setText(mMissionInfoList.get(0).getMISSIONTYPE());
//				mDriverTelephoneTv.setText(mMissionInfoList.get(0).getDIRVERPHONE());
//				mCarDevideNumTv.setText(mMissionInfoList.get(0)
//						.getVEHICLEDEVICENUMBER());
//				mCarCardNumTv.setText(mMissionInfoList.get(0)
//						.getVEHICLECOMMUNICTIONCARD());
				mCompanyTv.setText(mMissionInfoList.get(0).getAFFILIATION());
				if(mMissionInfoList.get(0).getEXPRATIONDATE()!=null && !mMissionInfoList.get(0).getEXPRATIONDATE().equals("")&& !mMissionInfoList.get(0).getEXPRATIONDATE().equals("null")) {
					mChargeEndDateEdt.setText(mMissionInfoList.get(0).getEXPRATIONDATE());
				} else {
					mChargeEndDateEdt.setText("");
				}
				if(mMissionInfoList.get(0).getCOST()!=null && !mMissionInfoList.get(0).getCOST().equals("")) {
					mCostEdt.setText(mMissionInfoList.get(0).getCOST());
				}


			}
		}

	}
	
	private class PrintChargeTask extends AsyncTask<String, Integer, String> {
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(MissionCharge.this,
					"正在提交打印请求", "请稍候...", true, false);
			super.onPreExecute();
		}
		
		
		@Override
		protected String doInBackground(String... params) {
			return SlaughterWs.printMissionInfoByCharge(mMissionGuid, App.appLoginUserCode, params[0], params[1]);
		}
		@Override
		protected void onPostExecute(String result) {
			mProgress.dismiss();
			super.onPostExecute(result);
			if(result.equals("SUCCESS")) {
				Toast.makeText(MissionCharge.this, "打印请求提交成功", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(MissionCharge.this, result, Toast.LENGTH_LONG).show();
			}
		}
		
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mission_charge_print_btn:
			if(checkInput()) {
				new PrintChargeTask().execute(mChargeEndDateEdt.getText().toString().trim(),mCostEdt.getText().toString().trim());
			}
			break;
		case R.id.mission_charge_pick_date_btn:
			
			Calendar cToday = Calendar.getInstance();
			cToday.setTime(new Date(System.currentTimeMillis()));
			
			int y = cToday.get(Calendar.YEAR);
			int m = cToday.get(Calendar.MONTH);
			int d = cToday.get(Calendar.DAY_OF_MONTH);
			
			new DatePickerDialog(MissionCharge.this , new DatePickerDialog.OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					Calendar cPick = Calendar.getInstance();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					cPick.set(year, monthOfYear, dayOfMonth);
					mChargeEndDateEdt.setText(format.format(cPick.getTime()));
				}
			}, y , m , d).show();
			break;

		default:
			break;
		}
		
	}
	
	private boolean checkInput() {
		if(mChargeEndDateEdt.getText().toString().equals("")) {
			Toast.makeText(MissionCharge.this, "请选择日期", Toast.LENGTH_LONG).show();
			return false;
		}
		if(mCostEdt.getText().toString().equals("") || mCostEdt.getText().toString().equals("0.00")) {
			Toast.makeText(MissionCharge.this, "请输入金额", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
}
