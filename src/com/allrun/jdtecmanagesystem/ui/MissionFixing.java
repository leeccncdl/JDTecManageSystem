package com.allrun.jdtecmanagesystem.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;
import com.allrun.jdtecmanagesystem.model.BaseResult;
import com.allrun.jdtecmanagesystem.model.MissionInfo;

public class MissionFixing extends Activity implements OnClickListener {

	private String mMissionGuid;
	
	private TextView mTaskNumTv;
	private TextView mCarNumTv;
	private TextView mWorkTypeTv;
	private TextView mDriverNameTv;
	private TextView mTaskTypeTv;
	private TextView mDriverTelephoneTv;
	private TextView mCarDevideNumTv;
	private TextView mCarCardNumTv;
	
	private EditText mDeviceCompanyEdt;
	private EditText mDeviceTypeEdt;
	private EditText mDeviceNumEdt;
	private EditText mCardNumEdt;
	
	private Button mPrintBtn;
	
	
	private ProgressDialog mProgress;
	
	private List<MissionInfo> mMissionInfoList = new ArrayList<MissionInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_fixing);

		findViewById();
		addListener();

		mMissionGuid = getIntent().getStringExtra(MissionDetail.MISSIONGUID);

		new QueryMissionDetailTask().execute("");
	}

	private void addListener() {
		mPrintBtn.setOnClickListener(this);
	}

	private void findViewById() {
		mTaskNumTv = (TextView) findViewById(R.id.mission_fixing_task_num_tv);
		mCarNumTv = (TextView) findViewById(R.id.mission_fixing_car_num_tv);
		mWorkTypeTv = (TextView) findViewById(R.id.mission_fixing_work_type_tv);
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

				mTaskNumTv.setText(mMissionInfoList.get(0).getGUID());
				mCarNumTv.setText(mMissionInfoList.get(0).getPLATENO());
				mWorkTypeTv.setText(mMissionInfoList.get(0).getBUSINESSTYPE());
				mDriverNameTv.setText(mMissionInfoList.get(0).getDIRVER());
				mTaskTypeTv.setText(mMissionInfoList.get(0).getMISSIONTYPE());
				mDriverTelephoneTv.setText(mMissionInfoList.get(0).getDIRVERPHONE());
				mCarDevideNumTv.setText(mMissionInfoList.get(0)
						.getVEHICLEDEVICENUMBER());
				mCarCardNumTv.setText(mMissionInfoList.get(0)
						.getVEHICLECOMMUNICTIONCARD());
				
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
				Toast.makeText(MissionFixing.this, "打印请求返回成功", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(MissionFixing.this, "打印请求提交失败", Toast.LENGTH_LONG).show();
			}
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mission_fixing_print_btn:
			new PrintFixingTask().execute(mDeviceCompanyEdt.getText().toString().trim(),
											mDeviceTypeEdt.getText().toString().trim(),
											mDeviceNumEdt.getText().toString().trim(),
											mCardNumEdt.getText().toString().trim());
			break;

		default:
			break;
		}
		
	}
}
