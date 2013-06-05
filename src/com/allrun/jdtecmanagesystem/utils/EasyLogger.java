package com.allrun.jdtecmanagesystem.utils;

import android.util.Log;

/**
 * @class：EasyLogger.java 
 * @description: 简易Log类
 * @author: lilingxu
 * 
 * @history:
 *   日期             版本          担当者         修改内容
 *   2012-5-3  1.0     lilingxu      初版
 */
public class EasyLogger{
	
	public static boolean DEBUG_MODE = true;

	/**
	 * @description 以Error方式向logcat中打印LOG
	 * @param tag LOG标记
	 * @param msg 打印的信息
	 */
	public static void e(String tag, String msg){
		if(DEBUG_MODE){
			Log.e(tag, msg);
		}
	}
	
	/**
	 * @description 以Error方式向logcat中打印LOG
	 * @param tag LOG标记
	 * @param msg 打印的信息
	 * @param tr  异常对象
	 */
	public static void e(String tag, String msg, Throwable tr){
		if(DEBUG_MODE){
			Log.e(tag, msg, tr);
		}
	}

	/**
	 * @description 以Info方式向logcat中打印LOG
	 * @param tag LOG标记
	 * @param msg 打印的信息
	 */
	public static void i(String tag, String msg){
		if(DEBUG_MODE){
			Log.i(tag, msg);
		}
	}
	
	/**
	 * @description 以Info方式向logcat中打印LOG
	 * @param tag LOG标记
	 * @param msg 打印的信息
	 * @param tr  异常对象
	 */
	public static void i(String tag, String msg, Throwable tr){
		if(DEBUG_MODE){
			Log.i(tag, msg, tr);
		}
	}

	/**
	 * @description 以Debug方式向logcat中打印LOG
	 * @param tag LOG标记
	 * @param msg 打印的信息
	 */
	public static void d(String tag, String msg){
		if(DEBUG_MODE){
			Log.d(tag, msg);
		}
	}
	
	/**
	 * @description 以Debug方式向logcat中打印LOG
	 * @param tag LOG标记
	 * @param msg 打印的信息
	 * @param tr  异常对象
	 */
	public static void d(String tag, String msg, Throwable tr){
		if(DEBUG_MODE){
			Log.d(tag, msg, tr);
		}
	}
	
	/**
	 * @description 以Warn方式向logcat中打印LOG
	 * @param tag LOG标记
	 * @param msg 打印的信息
	 */
	public static void w(String tag, String msg){
		if(DEBUG_MODE){
			Log.w(tag, msg);
		}
	}
	
	/**
	 * @description 以Warn方式向logcat中打印LOG
	 * @param tag LOG标记
	 * @param msg 打印的信息
	 * @param tr  异常对象
	 */
	public static void w(String tag, String msg, Throwable tr){
		if(DEBUG_MODE){
			Log.w(tag, msg, tr);
		}
	}
}
