package com.allrun.jdtecmanagesystem.ui;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;
import com.allrun.jdtecmanagesystem.model.BaseResult;
import com.allrun.jdtecmanagesystem.model.MissionInfo;

public class MissionDetail extends Activity implements OnClickListener {
	
	public static final String MISSIONGUID = "com.allrun.jdtecmanagesystem.missionGuid";
	
	private String mMissionGuid;
	
	private TextView mTaskNumTv;
	private TextView mCarNumTv;
	private TextView mWorkTypeTv;
	private TextView mDriverNameTv;
	private TextView mTaskTypeTv;
	private TextView mDriverTelephoneTv;
	private TextView mCarDevideNumTv;
	private TextView mCarCardNumTv;
	private TextView mEndDateTv;
	private TextView mTaskDateTv;
	private TextView mTaskDescriptionTv;
	private TextView mDeviceCompanyTv;
	private TextView mDeviceTypeTv;
	private TextView mDeviceNumTv;
	private TextView mCardNumTv;
	private TextView mDistributeDescriptionTv;
	
	private LinearLayout mContentRow1Ll;
	private LinearLayout mContentRow2Ll;
	
	private LinearLayout mBackLl;
	
	private ProgressDialog mProgress;
	
	private List<MissionInfo> mMissionInfoList = new ArrayList<MissionInfo>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_detail);
		
		findViewById();
		addListener();
		
		mMissionGuid = getIntent().getStringExtra(MISSIONGUID);
		
		new QueryMissionDetailTask().execute("");
	}

	private void addListener() {
		mBackLl.setOnClickListener(this);
		
	}

	private void findViewById() {
		mTaskNumTv = (TextView) findViewById(R.id.mission_task_num_tv);
		mCarNumTv = (TextView) findViewById(R.id.mission_car_num_tv);
		mWorkTypeTv = (TextView) findViewById(R.id.mission_work_type_tv);
		mDriverNameTv = (TextView) findViewById(R.id.mission_driver_name_tv);
		mTaskTypeTv = (TextView) findViewById(R.id.mission_task_type_tv);
		mDriverTelephoneTv = (TextView) findViewById(R.id.mission_driver_telephone_tv);
		mCarDevideNumTv = (TextView) findViewById(R.id.mission_car_device_num_tv);
		mCarCardNumTv = (TextView) findViewById(R.id.mission_car_card_num_tv);
		mEndDateTv = (TextView) findViewById(R.id.mission_end_date_tv);
		mTaskDateTv = (TextView) findViewById(R.id.mission_task_date_tv);
		mTaskDescriptionTv = (TextView) findViewById(R.id.mission_task_description_tv);
		mDeviceCompanyTv = (TextView) findViewById(R.id.mission_device_company_tv);
		mDeviceTypeTv = (TextView) findViewById(R.id.mission_device_type_tv);
		mDeviceNumTv = (TextView) findViewById(R.id.mission_device_num_tv);
		mCardNumTv = (TextView) findViewById(R.id.mission_car_num_tv);
		mDistributeDescriptionTv = (TextView) findViewById(R.id.mission_distribute_description_tv);
		mContentRow1Ll = (LinearLayout) findViewById(R.id.mission_row_1_ll);
		mContentRow2Ll = (LinearLayout) findViewById(R.id.mission_row_2_ll);
		
		mBackLl = (LinearLayout) findViewById(R.id.task_detail_back_ll);
	}

	private class QueryMissionDetailTask extends AsyncTask<String, Integer, BaseResult> {
		
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(MissionDetail.this,
					"正在查询任务信息", "请稍候...", true, false);
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
			if(result == null) {
				Toast.makeText(MissionDetail.this, "任务信息查询失败", Toast.LENGTH_SHORT).show();
				return;
			}
			mMissionInfoList = result.getMISSIONINFO();
			if(mMissionInfoList.size() != 0) {
				
				if(mMissionInfoList.get(0).getMISSIONTYPECODE().equals("2")) {
					mContentRow1Ll.setVisibility(View.GONE);
					mContentRow2Ll.setVisibility(View.GONE);
				}
				
				mTaskNumTv.setText(mMissionInfoList.get(0).getGUID());
				mCarNumTv.setText(mMissionInfoList.get(0).getPLATENO());
				mWorkTypeTv.setText(mMissionInfoList.get(0).getBUSINESSTYPE());
				mDriverNameTv.setText(mMissionInfoList.get(0).getDIRVER());
				mTaskTypeTv.setText(mMissionInfoList.get(0).getMISSIONTYPE());
				mDriverTelephoneTv.setText(mMissionInfoList.get(0).getDIRVERPHONE());
				mCarDevideNumTv.setText(mMissionInfoList.get(0).getVEHICLEDEVICENUMBER());
				mCarCardNumTv.setText(mMissionInfoList.get(0).getVEHICLECOMMUNICTIONCARD());
				mEndDateTv.setText(mMissionInfoList.get(0).getEXPRATIONDATE());
				mTaskDateTv.setText(mMissionInfoList.get(0).getMISSIONDATE());
				mTaskDescriptionTv.setText(mMissionInfoList.get(0).getMISSIONNOTE());
				mDeviceCompanyTv.setText(mMissionInfoList.get(0).getDEVICEMANUFACTURE());
				mDeviceTypeTv.setText(mMissionInfoList.get(0).getDEVICETYPE());
				mDeviceNumTv.setText(mMissionInfoList.get(0).getDEVICENUMBER());
				mCardNumTv.setText(mMissionInfoList.get(0).getCOMMUNICTIONCARD());
				mDistributeDescriptionTv.setText(mMissionInfoList.get(0).getALLOCATENOTE());
				
			}
		}
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.task_detail_back_ll:
			finish();
			break;

		default:
			break;
		}
		
	}
	
}
