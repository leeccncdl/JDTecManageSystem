package com.allrun.jdtecmanagesystem.model;

import java.util.List;


public class BaseResult {

	private String LOGIN; 
	private String USERCODE; 
	private String UPDATEPASSWORD; 
	private String PRINTBYADD; 
	private String PRINTBYMODIFY; 
	private String PRINTBYCHARGE; 
	
	private List<Mission> MISSIONLIST; //任务列表
	private List<MissionInfo> MISSIONINFO;
	public String getLOGIN() {
		return LOGIN;
	}
	public void setLOGIN(String lOGIN) {
		LOGIN = lOGIN;
	}
	public String getUPDATEPASSWORD() {
		return UPDATEPASSWORD;
	}
	public void setUPDATEPASSWORD(String uPDATEPASSWORD) {
		UPDATEPASSWORD = uPDATEPASSWORD;
	}
	public String getPRINTBYADD() {
		return PRINTBYADD;
	}
	public void setPRINTBYADD(String pRINTBYADD) {
		PRINTBYADD = pRINTBYADD;
	}
	public String getPRINTBYMODIFY() {
		return PRINTBYMODIFY;
	}
	public void setPRINTBYMODIFY(String pRINTBYMODIFY) {
		PRINTBYMODIFY = pRINTBYMODIFY;
	}
	public String getPRINTBYCHARGE() {
		return PRINTBYCHARGE;
	}
	public void setPRINTBYCHARGE(String pRINTBYCHARGE) {
		PRINTBYCHARGE = pRINTBYCHARGE;
	}
	public List<Mission> getMISSIONLIST() {
		return MISSIONLIST;
	}
	public void setMISSIONLIST(List<Mission> mISSIONLIST) {
		MISSIONLIST = mISSIONLIST;
	}
	public List<MissionInfo> getMISSIONINFO() {
		return MISSIONINFO;
	}
	public void setMISSIONINFO(List<MissionInfo> mISSIONINFO) {
		MISSIONINFO = mISSIONINFO;
	}
	public String getUSERCODE() {
		return USERCODE;
	}
	public void setUSERCODE(String uSERCODE) {
		USERCODE = uSERCODE;
	}
	
	
}
