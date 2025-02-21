package com.arcis.vgil.model;


public class CouponCode {

	private String code;
	private String partNo;
	
	private double amount;
	private String status;
	private int eventcode;
	private int earnedpoint;
	private int bonus;
	private int effort;
	
	public int getEffort() {
		return effort;
	}
	public void setEffort(int effort) {
		this.effort = effort;
	}
	private boolean isredeemedSucessful;

	
	public boolean isIsredeemedSucessful() {
		return isredeemedSucessful;
	}
	public void setIsredeemedSucessful(boolean isredeemedSucessful) {
		this.isredeemedSucessful = isredeemedSucessful;
	}
	
	public int gettotal(){
		return earnedpoint+bonus;
	}
	public int getBonus() {
		return bonus;
	}
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	public int getEarnedpoint() {
		return earnedpoint;
	}
	public void setEarnedpoint(int earnedpoint) {
		this.earnedpoint = earnedpoint;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getEventcode() {
		return eventcode;
	}
	public void setEventcode(int eventcode) {
		this.eventcode = eventcode;
	}
	
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
}
