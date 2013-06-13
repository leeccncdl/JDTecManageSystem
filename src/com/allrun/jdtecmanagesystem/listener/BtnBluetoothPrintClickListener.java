package com.allrun.jdtecmanagesystem.listener;

import java.io.File;

import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.model.MissionInfo;
import com.allrun.jdtecmanagesystem.utils.FileUtils;
import com.allrun.jdtecmanagesystem.utils.GenerateWordFile;
import com.allrun.jdtecmanagesystem.utils.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 打印按钮监听器
 * @author wang19
 *
 */
public class BtnBluetoothPrintClickListener implements OnClickListener {
	private Activity mActivity;
	
	private BluetoothAdapter mBluetoothAdapter;
	private AlertDialog mAlertDialog;   //确定打开蓝牙 dialog
	private ProgressDialog mProgressDialog;
	
	public BtnBluetoothPrintClickListener(Activity activity){
		this.mActivity = activity;
	}

	@Override
	public void onClick(View v) {
		if(null == mBluetoothAdapter){
			//取得蓝牙适配器
			mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		}
		if(!mBluetoothAdapter.isEnabled()){
			//蓝牙未打开, 打开蓝牙
			if(null == mAlertDialog){
				mAlertDialog = new AlertDialog.Builder(mActivity)
						.setTitle("打开蓝牙")
						.setPositiveButton("确定", new Dialog.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
									//发送请求，打开蓝牙
									Intent startBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
									mActivity.startActivityForResult(startBluetoothIntent, App.REQUEST_ENABLE);
								}
						}).setNeutralButton("取消", new Dialog.OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mAlertDialog.dismiss();
							}
						}).create();
			}
			mAlertDialog.setMessage("蓝牙未打开，是否打开？");
			mAlertDialog.show();
		}
		else {
			Intent startBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			mActivity.startActivityForResult(startBluetoothIntent, App.REQUEST_PRINT);
		}
	}
	
	/**
	 * 开始打印
	 */
	public void beginPrint(MissionInfo missionInfo) {
		if(null == mProgressDialog){
			mProgressDialog = new ProgressDialog(mActivity);
			mProgressDialog.setMessage("准备数据中...");
		}
		
		//判断printershare.apk 是否安装,如果没有安装。则安装assets中的apk程序
		if (!FileUtils.appIsInstalled(mActivity, "com.dynamixsoftware.printershare")) {
			
			Intent intent = new Intent();
			File file = FileUtils.getAssetFileToCacheDir(mActivity, "printer_share_7.9.6.apk");
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
			mActivity.startActivity(intent);
			return ;
		}
		mProgressDialog.show();
		//准备生成打印word文件
		File file = FileUtils.getGenerateWordDir(mActivity, Utils.GetNowDate().replace('-', '_')+ "_" + missionInfo.getMISSIONNO()); //word生成路径
		if(file.exists()){
			file.delete();
		}
		//准备生成打印word
		
		GenerateWordFile.word(mActivity, file, missionInfo);

		mProgressDialog.hide();
		//跳转到printershare页面
		Intent intent = new Intent();
		ComponentName comp = new ComponentName("com.dynamixsoftware.printershare","com.dynamixsoftware.printershare.ActivityPrintDocuments");
		intent = new Intent();
		intent.setComponent(comp);
		intent.setAction("android.intent.action.VIEW");
		intent.setType("application/msword");
		intent.setData(Uri.fromFile(file));
		mActivity.startActivityForResult(intent,App.REQUEST_RESULT_PRINT);	
	}
//	public void beginPrint(MissionInfo missionInfo) {
//		if(null == mProgressDialog){
//			mProgressDialog = new ProgressDialog(mActivity);
//			mProgressDialog.setMessage("准备数据中...");
//		}
//		mProgressDialog.show();
//		
//		//判断printershare.apk 是否安装,如果没有安装。则安装assets中的apk程序
//		if (!FileUtils.appIsInstalled(mActivity, "com.dynamixsoftware.printershare")) {
//			
//			Intent intent = new Intent();
//			File file = FileUtils.getAssetFileToCacheDir(mActivity, "printer_share_7.9.6.apk");
//			intent.setAction(Intent.ACTION_VIEW);
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			intent.setType("application/vnd.android.package-archive");
//			intent.setData(Uri.fromFile(file));
//			
//			mActivity.startActivity(intent);
//		}
//		//准备生成打印word文件
//		File file = FileUtils.getGenerateWordDir(mActivity, Utils.GetNowDate().replace('-', '_')+ "_" + missionInfo.getMISSIONNO()); //word生成路径
//		if(file.exists()){
//			file.delete();
//		}
//		//准备生成打印word
//		
//		GenerateWordFile.word(mActivity, file, missionInfo);
//
//		mProgressDialog.hide();
//		//跳转到printershare页面
//		Intent intent = new Intent();
//		ComponentName comp = new ComponentName("com.dynamixsoftware.printershare","com.dynamixsoftware.printershare.ActivityPrintDocuments");
//		intent = new Intent();
//		intent.setComponent(comp);
//		intent.setAction("android.intent.action.VIEW");
//		intent.setType("application/msword");
//		intent.setData(Uri.fromFile(file));
//		mActivity.startActivityForResult(intent,App.REQUEST_RESULT_PRINT);	
//	}
}
