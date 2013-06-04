package com.yfmandroid.log;

public class LoggerConfigure {
	
	public enum LogLevel{
		TRACE(0),
		DEBUG(1),
		INFO(2),
		WARN(3),
		ERROR(4),
		FATAL(5),
		OFF(6);
		
		public int levelValue;
		
		LogLevel(int aLevelValue){
			levelValue = aLevelValue;
		}
	}

	public static LogLevel logLevel = LogLevel.TRACE;
	
	public static String logTag = "APPLOG";
	
	public static String logLocation = null;
	
	public static String fileName = "log_";
	
	public static String currentLogFile;
	
	public static boolean shouldLog(LogLevel level){
		return logLevel.levelValue <= level.levelValue;
	}
	
	public static void initialize(){
		shutdown();
		FileAppender.initialize();
	}
	
	public static void shutdown(){
		FileAppender.shutdown();
	}
}
