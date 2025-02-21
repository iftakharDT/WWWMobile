package com.arcis.vgil.model;

public class Product {

	private String saleid;
	private String saleNo;
	private String partyName;
	private String saleDate;
	private  int saleQty;
	private int saleAmount;
	private int returnqQty;
	private int totalAmount;
	private int totalQty;
	private int noOfItem;
	private String entryDate;
	private boolean ischecked;
	
	public boolean isIschecked() {
		return ischecked;
	}
	public void setIschecked(boolean ischecked) {
		this.ischecked = ischecked;
	}
	public String getSaleid() {
		return saleid;
	}
	public void setSaleid(String saleid) {
		this.saleid = saleid;
	}
	public String getSaleNo() {
		return saleNo;
	}
	public void setSaleNo(String saleNo) {
		this.saleNo = saleNo;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public int getSaleQty() {
		return saleQty;
	}
	public void setSaleQty(int saleQty) {
		this.saleQty = saleQty;
	}
	public int getSaleAmount() {
		return saleAmount;
	}
	public void setSaleAmount(int saleAmount) {
		this.saleAmount = saleAmount;
	}
	public int getReturnqQty() {
		return returnqQty;
	}
	public void setReturnqQty(int returnqQty) {
		this.returnqQty = returnqQty;
	}
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}
	public int getNoOfItem() {
		return noOfItem;
	}
	public void setNoOfItem(int noOfItem) {
		this.noOfItem = noOfItem;
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	
}
