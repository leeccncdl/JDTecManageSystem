package com.allrun.jdtecmanagesystem.ui;

import java.util.ArrayList;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.R;
import com.allrun.jdtecmanagesystem.dao.SlaughterWs;
import com.allrun.jdtecmanagesystem.model.BaseResult;
import com.allrun.jdtecmanagesystem.model.Mission;

public class MissionList extends Activity {
	
	private ListView mMissionLv;
	private List<Mission> mMissionList = new ArrayList<Mission>(); 
	private ProgressDialog mProgress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.task_listview);
		
		findViewById();
		addListener();
		
		new QueryMissionListTask().execute("");
		
	}
	
	 private void findViewById() {
		 mMissionLv = (ListView) findViewById(R.id.task_listview_lv);
	}

	private void addListener() {
		// TODO Auto-generated method stub
		
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
				convertView = LayoutInflater.from(App.app.getApplicationContext()).inflate(R.layout.task_list_item, null);
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
			vh.deviceNumTv.setText(missionList.get(position).getGUID());
			vh.taskDateTv.setText(missionList.get(position).getMISSIONDATE());
			vh.printDateTv.setText(missionList.get(position).getPRINTDATE());
			vh.detailBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(MissionList.this,MissionDetail.class);
					intent.putExtra(MissionDetail.MISSIONGUID, mMissionList.get(position).getGUID());
					startActivity(intent);
					
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
			
			return SlaughterWs.getMissionList("74df9594c76f4245b918ffa9b97c1188");
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
			MissionListAdapter listAdapter = new MissionListAdapter(mMissionList);
			mMissionLv.setAdapter(listAdapter);
//			mMissionLv.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> arg0, View arg1,
//						int arg2, long arg3) {
//					Intent intent = new Intent(MissionList.this,MissionDetail.class);
//					intent.putExtra(MissionDetail.MISSIONGUID, mMissionList.get(arg2).getGUID());
//					startActivity(intent);
//				}
//			});
			
		}
		
		
		
	}
}
