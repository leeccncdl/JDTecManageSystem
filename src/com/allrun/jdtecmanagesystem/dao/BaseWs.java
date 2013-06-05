package com.allrun.jdtecmanagesystem.dao;

import com.allrun.jdtecmanagesystem.model.BaseResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseWs {

	protected static Gson gson = null;
	protected static void buildGsonInstance(){
		if(gson == null){
			gson = new GsonBuilder().setPrettyPrinting()
			.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		}
	}
	
	protected static boolean validateUser(BaseResult result){
	
		if(result == null || result.getLOGIN().equals("FAILURE")){
			return false;
		}
		
		return true;
	}
}
