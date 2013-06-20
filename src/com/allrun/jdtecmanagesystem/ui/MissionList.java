package com.allrun.jdtecmanagesystem.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.AppLogger;
import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;
import com.allrun.jdtecmanagesystem.model.BaseResult;
import com.allrun.jdtecmanagesystem.model.Mission;
import com.allrun.jdtecmanagesystem.view.XListView;
import com.allrun.jdtecmanagesystem.view.XListView.IXListViewListener;

public class MissionList extends Activity implements OnClickListener,IXListViewListener {
	
	private AppLogger log = AppLogger.getLogger(this.getClass());
	private SimpleDateFormat todayFormatter = new SimpleDateFormat("HH:mm");
	private XListView mMissionLv;
	private List<Mission> mMissionList = new ArrayList<Mission>(); 
	private ProgressDialog mProgress;
	private ImageView mQuitIv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_listview);
		
		findViewById();
		addListener();
		
		new QueryMissionListTask().execute("");
	}
	
	 private void findViewById() {
		 mMissionLv = (XListView) findViewById(R.id.task_listview_lv);
		 mQuitIv = (ImageView) findViewById(R.id.task_list_header_quit_iv);
		 
		 mMissionLv.setPullLoadEnable(false);
	}

	private void addListener() {
		mQuitIv.setOnClickListener(this);
		mMissionLv.setXListViewListener(MissionList.this);
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			new QueryMissionListTask().execute("");
		}
	}
	private class MissionListAdapter extends BaseAdapter {
		 private List<Mission> missionList = new ArrayList<Mission>();
		 public MissionListAdapter(List<Mission> data) {
			 missionList = data;
		 }
		 
		@Override
		public int getCount() {
			return missionList.size();
		}

		@Override
		public Object getItem(int position) {
			return missionList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			if(convertView == null) {
				vh = new ViewHolder();
				convertView = LayoutInflater.from(App.JDTecApp.getApplicationContext()).inflate(R.layout.task_list_item, null);
				vh.missionNumTv = (TextView) convertView.findViewById(R.id.task_list_mission_number_tv);
				vh.carNumTv = (TextView) convertView.findViewById(R.id.task_list_car_number_tv);
				vh.taskTypeTv = (TextView) convertView.findViewById(R.id.task_list_task_type_tv);
				vh.deviceNumTv = (TextView) convertView.findViewById(R.id.task_list_device_number_tv);
				vh.taskDateTv = (TextView) convertView.findViewById(R.id.task_list_task_date_tv);
				vh.printDateTv = (TextView) convertView.findViewById(R.id.task_list_print_date_tv);
				
				vh.detailBtn = (Button) convertView.findViewById(R.id.task_list_detial_btn);
				vh.printBtn = (Button) convertView.findViewById(R.id.task_list_print_btn);
				convertView.setTag(vh);
			} else {
				vh = (ViewHolder) convertView.getTag();
				resetViewHolder(vh);
			}
			vh.missionNumTv.setText(missionList.get(position).getMISSIONNO());
			vh.carNumTv.setText(missionList.get(position).getPLATENO());
			vh.taskTypeTv.setText(missionList.get(position).getMISSIONTYPE());
			vh.deviceNumTv.setText(missionList.get(position).getVEHICLEDEVICENUMBER());
			if(missionList.get(position).getMISSIONDATE() == null || missionList.get(position).getMISSIONDATE().equals("")||missionList.get(position).getMISSIONDATE().equals("null")) {
				vh.taskDateTv.setText("");
			} else {
				vh.taskDateTv.setText(missionList.get(position).getMISSIONDATE());
			}
			
			if(missionList.get(position).getPRINTDATE() == null || missionList.get(position).getPRINTDATE().equals("")||missionList.get(position).getPRINTDATE().equals("null")) {
				vh.printDateTv.setText("");
			} else {
				vh.printDateTv.setText(missionList.get(position).getPRINTDATE());
			}
			vh.detailBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MissionList.this,MissionDetail.class);
					intent.putExtra(MissionDetail.MISSIONGUID, mMissionList.get(position).getGUID());
					startActivity(intent);
					
				}
			});
			vh.printBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					if(mMissionList.get(position).getMISSIONTYPECODE().equals("2")) {
						intent.setClass(MissionList.this, MissionCharge.class);
					} else if(mMissionList.get(position).getMISSIONTYPECODE().equals("0")) {
						intent.setClass(MissionList.this, MissionMantenance.class);
					} else if(mMissionList.get(position).getMISSIONTYPECODE().equals("1")) {
						intent.setClass(MissionList.this, MissionFixing.class);
					} else {
						if(log.isDebugEnabled()) {
							log.debug("任务类型Code返回不正确，不能跳转页面");
							return;
						}
					}
					intent.putExtra(MissionDetail.MISSIONGUID, mMissionList.get(position).getGUID());
					startActivityForResult(intent, 0);
				}
			});
			return convertView;
		}
		
		private class ViewHolder{
			private TextView missionNumTv;
			private TextView carNumTv;
			private TextView taskTypeTv;
			private TextView deviceNumTv;
			private TextView taskDateTv;
			private TextView printDateTv;
			
			private Button detailBtn;
			private Button printBtn;
			
		}
		
		private void resetViewHolder(ViewHolder vh) {
			vh.missionNumTv.setText("");
			vh.carNumTv.setText("");
			vh.taskTypeTv.setText("");
			vh.deviceNumTv.setText("");
			vh.taskDateTv.setText("");
			vh.printDateTv.setText("");
		}
	 }
	
	private class QueryMissionListTask extends AsyncTask<String, Integer, BaseResult> {
		
		@Override
		protected void onPreExecute() {
			mProgress = ProgressDialog.show(MissionList.this,
					"正在查询任务列表", "请稍候...", true, false);
			super.onPreExecute();
		}

		@Override
		protected BaseResult doInBackground(String... params) {
			
			return SlaughterWs.getMissionList(App.appLoginUserCode);
		}

		@Override
		protected void onPostExecute(BaseResult result) {
			mProgress.dismiss();
			super.onPostExecute(result);
			
			if(result == null) {
				Toast.makeText(MissionList.this, "查询失败", Toast.LENGTH_SHORT).show();
				return;
			}
			mMissionList = result.getMISSIONLIST();
			if(mMissionList == null) {
				Toast.makeText(MissionList.this, "当前任务列表没有任务", Toast.LENGTH_SHORT).show();
				mMissionList = new ArrayList<Mission>();				 
				MissionListAdapter listAdapter = new MissionListAdapter(mMissionList);
				mMissionLv.setAdapter(listAdapter);
			} else {
				MissionListAdapter listAdapter = new MissionListAdapter(mMissionList);
				mMissionLv.setAdapter(listAdapter);
			}
			onLoad();
		}
		
		
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.task_list_header_quit_iv:
			finish();
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onRefresh() {
		new QueryMissionListTask().execute("");
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
	
	private void onLoad() {

		mMissionLv.stopRefresh();
		mMissionLv.setRefreshTime(todayFormatter.format(new Date(System
				.currentTimeMillis())));
	}
}
