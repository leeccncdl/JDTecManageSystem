package com.allrun.jdtecmanagesystem.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

public class FileUtils {
	
	/**
	 * 获取手机内所有应用       
	 */
    public static List<PackageInfo> getAllAppsNoSystem(Context context) {       
        List<PackageInfo> apps = new ArrayList<PackageInfo>();
        PackageManager pManager = context.getPackageManager();
        //获取手机内所有应用
        List<PackageInfo> paklist = pManager.getInstalledPackages(0);
        for (int i = 0; i < paklist.size(); i++) {
            PackageInfo pak = (PackageInfo) paklist.get(i);
            //判断是否为非系统预装的应用程序
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                apps.add(pak);
            }
        }
        return apps;
    }
	
	/**
	 * 判断apk是否安装
	 */    
	public static boolean appIsInstalled(Context context, String pageName) {
		try {
			context.getPackageManager().getPackageInfo(pageName, 0);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
	
	/**
	 * 把Asset下的apk拷贝到sdcard下 /BluetoothPrint/apk/目录下
	 */
	public static File getAssetFileToCacheDir(Context context, String fileName) {
		try {
			File cacheDir = FileUtils.getSetupDir(context);
			final String cachePath = cacheDir.getAbsolutePath()
					+ File.separator + fileName;
			InputStream is = context.getAssets().open(fileName);
			File file = new File(cachePath);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];

			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取sdcard下 /BluetoothPrint/apk/目录
	 */
	public static File getSetupDir(Context context) {
		String APP_DIR_NAME = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/JDTecManageSystem/apk/";
		File dir = new File(APP_DIR_NAME);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}
	
	/**
	 * 获取生成word文件夹，如果不存在则创建
	 */
	public static File getGenerateDir(Context context){
		String APP_DIR_NAME = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/JDTecManageSystem/data/";
		File dir = new File(APP_DIR_NAME);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	/**
	 * 获取生成word文件路径
	 */
	public static File getGenerateWordDir(Context Context,String strFileName){
		if(strFileName.indexOf(".")== -1){
		}
		String APP_DIR_NAME = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/JDTecManageSystem/data/";
		FileUtils.getGenerateDir(Context);
		File dir = new File(APP_DIR_NAME + strFileName + ".doc");
		return dir;
	}
 
}