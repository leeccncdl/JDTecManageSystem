package com.allrun.jdtecmanagesystem.model;

public class User {

	private String USERCODE;
	private String USERNAME;
	private String PASSWORD;
	
	public String getCode() {
		return USERCODE;
	}
	public void setCode(String code) {
		this.USERCODE = code;
	}
	
	public String getName() {
		return USERNAME;
	}
	public void setName(String name) {
		this.USERNAME = name;
	}
	
	public String getPassword(){
		return PASSWORD;
	}
	
	public void setPassword(String password){
		PASSWORD = password;
	}
}
