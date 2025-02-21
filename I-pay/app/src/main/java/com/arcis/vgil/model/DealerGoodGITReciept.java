package com.arcis.vgil.model;


public class DealerGoodGITReciept {
	
	private String inVoiceNo;
	private String date;
	private String quantity;
	private String DispatchID;
	private String LRRefNo;
	private String InvoiceID;
	private String Amount;
	
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
	public String getInvoiceID() {
		return InvoiceID;
	}
	public void setInvoiceID(String invoiceID) {
		InvoiceID = invoiceID;
	}
	public String getLRRefNo() {
		return LRRefNo;
	}
	public void setLRRefNo(String lRRefNo) {
		LRRefNo = lRRefNo;
	}
	public String getDispatchID() {
		return DispatchID;
	}
	public void setDispatchID(String dispatchID) {
		DispatchID = dispatchID;
	}
	public String getInVoiceNo() {
		return inVoiceNo;
	}
	public void setInVoiceNo(String inVoiceNo) {
		this.inVoiceNo = inVoiceNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	
	

}
