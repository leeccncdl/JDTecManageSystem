package com.allrun.jdtecmanagesystem;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.yfmandroid.log.Logger;
import com.yfmandroid.log.LoggerConfigure;
import com.yfmandroid.log.LoggerConfigure.LogLevel;

/**
 * @class：AppLogger.java 
 * @description: 自定义应用Log类，配置log信息
 * @author: lee
 * 
 * @history:
 *   日期             版本          担当者         修改内容
 *   2013-1-6  1.0     lee      初版
 */
public class AppLogger extends Logger {
	
	public static boolean config(Context context){
		LoggerConfigure.logTag = "JDTECManage";
		LoggerConfigure.logLevel = LogLevel.ERROR;
		LoggerConfigure.logLocation = Environment.getExternalStorageDirectory()+File.separator+"JDTEC"+File.separator+"Log";
		
		//如果需要将log信息写入本地文件，需要做该初始化，否则不需要
//		LoggerConfigure.initialize();
		
		return true;
	}
	
	public static AppLogger getLogger(Class<?> cls){
		String name = cls.getSimpleName();
		if(name == null || name.length() == 0){
			name = cls.getName();
		}
		return new AppLogger(name);
	}

	public static AppLogger getLogger(String name) {
		return new AppLogger(name);
	}
	
	private AppLogger(String name) {
		super(name);
	}

	@Override
	protected void logStorage(LogLevel level, String message) {
		super.logStorage(level, message);
	}
}
