package com.arcis.vgil.model;

public class DashboardModel {
	
	
//	{ 'Root': { 'table': [{'AMName': 'Sanjoy Roy','AMMobileNo': '9333025114',
		//'AMEmail': 'sanjoy.roy@anandgroupindia.com','LoginTime': ' 11:21AM','Calls': '3','Color': 'Y'}] }}
	String AMName;
	String AMMobileNo;
	
	String AMEmail;
	String LoginTime;
	String Calls;
	String Color;
	public String getAMName() {
		return AMName;
	}
	public void setAMName(String aMName) {
		AMName = aMName;
	}
	public String getAMMobileNo() {
		return AMMobileNo;
	}
	public void setAMMobileNo(String aMMobileNo) {
		AMMobileNo = aMMobileNo;
	}
	public String getAMEmail() {
		return AMEmail;
	}
	public void setAMEmail(String aMEmail) {
		AMEmail = aMEmail;
	}
	public String getLoginTime() {
		return LoginTime;
	}
	public void setLoginTime(String loginTime) {
		LoginTime = loginTime;
	}
	public String getCalls() {
		return Calls;
	}
	public void setCalls(String calls) {
		Calls = calls;
	}
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
		Color = color;
	}
	

}
