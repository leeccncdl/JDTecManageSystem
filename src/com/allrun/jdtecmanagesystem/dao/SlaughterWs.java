package com.allrun.jdtecmanagesystem.dao;

import com.allrun.jdtecmanagesystem.App;
import com.allrun.jdtecmanagesystem.AppLogger;
import com.allrun.jdtecmanagesystem.dao.DataAccessSoap.PropertyType;
import com.allrun.jdtecmanagesystem.model.BaseResult;
import com.allrun.jdtecmanagesystem.utils.EasyLogger;
import com.google.gson.JsonParseException;

/** 
* @ClassName: SlaughterWs 
* @Description: WebService访问类
* @author 香格李拉   limingdl@yeah.net
* @date 2013-6-5 上午09:04:17  
*/
public class SlaughterWs extends BaseWs {
	
	private static AppLogger log = AppLogger.getLogger(SlaughterWs.class);

	/**
	 * @description 登录校验
	 * @param code 帐户
	 * @param name 密码
	 * @return 校验通过返回登录成功信息，失败返回用户名或密码错误信息或软件未知错误信息
	 */
	public static String checkLogin(String strUserName, String strPassWord){
		buildGsonInstance();
		System.out.println(App.BASE_NAMESPACE+":" +App.SERVER_URL+":"+"CheckLoginUser");
		DataAccessSoap ksoap2 = new DataAccessSoap(App.BASE_NAMESPACE, App.SERVER_URL, "CheckLoginUser");
		
		ksoap2.setProperty("strUserName", strUserName, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strPassWord", strPassWord, PropertyType.TYPE_STRING);
		
		String result = "";
		try {
			result = ksoap2.request();
			if(log.isDebugEnabled()) {
				log.debug("CheckLoginUser请求返回结果:" +result);
			}
			
			BaseResult loginResult = gson.fromJson(result, BaseResult.class);
			
			if(!validateUser(loginResult)){
				return App.USER_VALIDITY_FAILURE;
			}

			result = loginResult.getLOGIN();
			App.appLoginUserCode = loginResult.getUSERCODE();
			
		} catch (Exception e) {
			EasyLogger.e("CheckLoginUser", "exception", e);
			result = App.SERVEREXCEPTION;
		}
		
		return result;
	}
	
	
	/** 
	* @Title: updateUserPassword 
	* @Description: 更新密码 
	* @param @param strUserName
	* @param @param strOldPassWord
	* @param @param strNewPassWord
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static String updateUserPassword(String strUserName,String strOldPassWord,String strNewPassWord) {
		buildGsonInstance();
		
		DataAccessSoap ksoap2 = new DataAccessSoap(App.BASE_NAMESPACE, App.SERVER_URL, "UpdateUserPassword");
		
		ksoap2.setProperty("strUserName", strUserName, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strOldPassWord", strOldPassWord, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strNewPassWord", strNewPassWord, PropertyType.TYPE_STRING);
		
		String result = "";
		
		try {
			result = ksoap2.request();
			if(log.isDebugEnabled()) {
				log.debug("UpdateUserPassword请求返回结果:" +result);
			}
			
			BaseResult updateResult = gson.fromJson(result, BaseResult.class);
			result = updateResult.getUPDATEPASSWORD();
			if(result.equals("FAILURE")) {
				result = updateResult.getMESSAGE();
			}
		} catch (Exception e) {
			EasyLogger.e("UpdateUserPassword", "exception", e);
			result = App.SERVEREXCEPTION;
		}
		return result;
	}
	
	/** 
	* @Title: getMissionList 
	* @Description: 获得任务列表 
	* @param @param strUserCode
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static BaseResult getMissionList(String strUserCode) {
		
		buildGsonInstance();
		
		DataAccessSoap ksoap2 = new DataAccessSoap(App.BASE_NAMESPACE, App.SERVER_URL, "getMissionList");
		ksoap2.setProperty("strUserCode", strUserCode, PropertyType.TYPE_STRING);
		
		BaseResult missionListResult = null;
		
		try {
			String result = ksoap2.request();
			EasyLogger.e("getMissionList result:", result);
			
			missionListResult = gson.fromJson(result, BaseResult.class);
			
		} catch (JsonParseException e) {
			return new BaseResult();
		}
		
		catch (Exception e) {
			EasyLogger.e("getMissionList", "exception", e);
			missionListResult = null;
		}
		return missionListResult;
	}
	
	/** 
	* @Title: getMissionInfo 
	* @Description: 获得任务信息
	* @param @return    设定文件 
	* @return BaseResult    返回类型 
	* @throws 
	*/
	public static BaseResult getMissionInfo(String strMissionGuid,String strUserCode) {
		buildGsonInstance();
		
		DataAccessSoap ksoap2 = new DataAccessSoap(App.BASE_NAMESPACE, App.SERVER_URL, "getMissionInfo");
		ksoap2.setProperty("strMissionGuid", strMissionGuid, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strUserCode", strUserCode, PropertyType.TYPE_STRING);
		
		BaseResult missionInfo = null;
		
		try {
			String result = ksoap2.request();
			EasyLogger.e("getMissionInfo  result:", result);
			
			missionInfo = gson.fromJson(result, BaseResult.class);
		} catch (Exception e) {
			EasyLogger.e("getMissionInfo", "exception", e);
			missionInfo = null;
		}
		return missionInfo;
	}
	
	/** 
	* @Title: printMissionInfoByAdd 
	* @Description: 安装打印 
	* @param @param strMissionGuid
	* @param @param strUserCode
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static String printMissionInfoByAdd(String strMissionGuid,String strUserCode) {
		buildGsonInstance();
		
		DataAccessSoap ksoap2 = new DataAccessSoap(App.BASE_NAMESPACE, App.SERVER_URL, "printMissionInfoByAdd");
		ksoap2.setProperty("strMissionGuid", strMissionGuid, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strUserCode", strUserCode, PropertyType.TYPE_STRING);
		
		String result = "";
		try {
			result = ksoap2.request();
			EasyLogger.e("printMissionInfoByAdd", result);
			
			BaseResult missionInfoByAdd = gson.fromJson(result, BaseResult.class);
			result = missionInfoByAdd.getPRINTBYADD();
			if(result.equals("FAILURE")) {
				result = missionInfoByAdd.getMESSAGE();
			}
		} catch (Exception e) {
			EasyLogger.e("printMissionInfoByAdd", "exception", e);
			result = App.SERVEREXCEPTION;
		}
		return result;
	}
	
	/** 
	* @Title: printMissionInfoByCharge 
	* @Description: 缴费打印
	* @param @param strMissionGuid
	* @param @param strUserCode
	* @param @param strExprationDate
	* @param @param dCost
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static String printMissionInfoByCharge(String strMissionGuid,String strUserCode,String strExprationDate,String dCost) {
		buildGsonInstance();
		
		DataAccessSoap ksoap2 = new DataAccessSoap(App.BASE_NAMESPACE, App.SERVER_URL, "printMissionInfoByCharge");
		ksoap2.setProperty("strMissionGuid", strMissionGuid, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strUserCode", strUserCode, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strExprationDate", strExprationDate, PropertyType.TYPE_STRING);
		ksoap2.setProperty("dCost", dCost, PropertyType.TYPE_STRING);
		String result = "";
		
		try {
			result = ksoap2.request();
			EasyLogger.e("printMissionInfoByCharge", result);
			
			BaseResult missionInfoByCharge = gson.fromJson(result, BaseResult.class);
			result = missionInfoByCharge.getPRINTBYCHARGE();
			if(result.equals("FAILURE")) {
				result = missionInfoByCharge.getMESSAGE();
			}
		} catch (Exception e) {
			EasyLogger.e("printMissionInfoByCharge", "exception", e);
			result = App.SERVEREXCEPTION;
		}
		return result;
	}
	
	/** 
	* @Title: printMissionInfoByModify 
	* @Description: 维修打印 
	* @param @param strMissionGuid
	* @param @param strUserCode
	* @param @param strDeviceManufacture
	* @param @param strDeviceType
	* @param @param strDeviceNumber
	* @param @param strCommunictionCard
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static String printMissionInfoByModify(String strMissionGuid,String strUserCode,String strDeviceManufacture,String strDeviceType,String strDeviceNumber,String strCommunictionCard) {
		buildGsonInstance();
		
		DataAccessSoap ksoap2 = new DataAccessSoap(App.BASE_NAMESPACE, App.SERVER_URL, "printMissionInfoByModify");
		ksoap2.setProperty("strMissionGuid", strMissionGuid, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strUserCode", strUserCode, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strDeviceManufacture", strDeviceManufacture, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strDeviceType", strDeviceType, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strDeviceNumber", strDeviceNumber, PropertyType.TYPE_STRING);
		ksoap2.setProperty("strCommunictionCard", strCommunictionCard, PropertyType.TYPE_STRING);
		
		
		String result = "";
		try {
			result = ksoap2.request();
			EasyLogger.e("printMissionInfoByModify", result);
			
			BaseResult missionInfoByModify = gson.fromJson(result, BaseResult.class);
			result = missionInfoByModify.getPRINTBYMODIFY();
			if(result.equals("FAILURE")) {
				result = missionInfoByModify.getMESSAGE();
			}
		} catch (Exception e) {
			EasyLogger.e("printMissionInfoByModify", "exception", e);
			result = App.SERVEREXCEPTION;
		}
		return result;
	}
	

}
