package com.allrun.jdtecmanagesystem.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat simpleDateFormatZh = new SimpleDateFormat(" yyyy年MM月dd日");
	private static SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat simpleDateTimeFormatZh = new SimpleDateFormat(" yyyy年MM月dd日 HH:mm:ss");
	
	public static String GetNowDate(){
	    return  simpleDateFormat.format(new Date());
	} 

	public static String GetNowDateTime(){  
	    return simpleDateTimeFormat.format(new Date());  
	} 

	public static String GetDateString(Date date){  
	    return simpleDateFormat.format(date);  
	} 

	public static String GetDateTimeString(Date date){  
	    return simpleDateTimeFormat.format(date);  
	} 
	
	public static String GetNowDateZh(){
	    return  simpleDateFormatZh.format(new Date());
	} 

	public static String GetNowDateTimeZh(){  
	    return simpleDateTimeFormatZh.format(new Date());  
	} 

	public static String GetDateStringZh(Date date){  
	    return simpleDateFormatZh.format(date);  
	} 

	public static String GetDateTimeStringZh(Date date){  
	    return simpleDateTimeFormatZh.format(date);  
	} 
	
	public static Date GetDate(String strDate) throws ParseException{  
	    return simpleDateFormat.parse(strDate);  
	} 

	public static Date GetDateTime(String strDate) throws ParseException{  
	    return simpleDateTimeFormat.parse(strDate);  
	} 
	
	public static String GetDateZh(String strDate){  
		Date temp;
		try {
			temp = simpleDateFormat.parse(strDate);
		} catch (ParseException e) {
			return "     年      月     日 ";
		}
	    return simpleDateFormatZh.format(temp);  
	} 

	public static String GetDateTimeZh(String strDate){  
		Date temp;
		try {
			temp = simpleDateTimeFormat.parse(strDate);
		} catch (ParseException e) {
			return "     年      月     日 ";
		}
	    return simpleDateTimeFormatZh.format(temp);  
	} 
	
	
}
