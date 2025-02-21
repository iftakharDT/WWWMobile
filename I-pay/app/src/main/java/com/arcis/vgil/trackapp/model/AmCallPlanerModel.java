package com.arcis.vgil.trackapp.model;

public class AmCallPlanerModel {
	String ID;
	String ContactType;
	
	String FirmName;
	String Name;
	String State;
	String City;
	String Area;
	String MobileNo;
	String LastVisited;
	String NoOfCalls_Last30Days;
	String BusinessTrend;
	boolean selected = false;
	String Planned;
	
	public String getPlanned() {
		return Planned;
	}
	public void setPlanned(String planned) {
		Planned = planned;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getContactType() {
		return ContactType;
	}
	public void setContactType(String contactType) {
		ContactType = contactType;
	}
	
	public String getFirmName() {
		return FirmName;
	}
	public void setFirmName(String firmName) {
		FirmName = firmName;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public String getMobileNo() {
		return MobileNo;
	}
	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}
	public String getLastVisited() {
		return LastVisited;
	}
	public void setLastVisited(String lastVisited) {
		LastVisited = lastVisited;
	}
	public String getNoOfCalls_Last30Days() {
		return NoOfCalls_Last30Days;
	}
	public void setNoOfCalls_Last30Days(String noOfCalls_Last30Days) {
		NoOfCalls_Last30Days = noOfCalls_Last30Days;
	}
	public String getBusinessTrend() {
		return BusinessTrend;
	}
	public void setBusinessTrend(String businessTrend) {
		BusinessTrend = businessTrend;
	}


}
