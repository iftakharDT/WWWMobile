package com.arcis.vgil.model;

public class GoodsReceiveInfo {

	private String id;
	private String productName;
	private int discpatchQty;
	private int receiveQty;
	private int damageQty;
	private int salereturn;
	private int netqty;
	private int shortQty;
	private String discOfDamage;
	
	public String getDiscOfDamage() {
		return discOfDamage;
	}
	public void setDiscOfDamage(String discOfDamage) {
		this.discOfDamage = discOfDamage;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getDiscpatchQty() {
		return discpatchQty;
	}
	public void setDiscpatchQty(int discpatchQty) {
		this.discpatchQty = discpatchQty;
	}
	public int getReceiveQty() {
		return receiveQty;
	}
	public void setReceiveQty(int receiveQty) {
		this.receiveQty = receiveQty;
	}
	public int getDamageQty() {
		return damageQty;
	}
	public void setDamageQty(int damageQty) {
		this.damageQty = damageQty;
	}
	public int getSalereturn() {
		return salereturn;
	}
	public void setSalereturn(int salereturn) {
		this.salereturn = salereturn;
	}
	public int getNetqty() {
		return netqty;
	}
	public void setNetqty(int netqty) {
		this.netqty = netqty;
	}
	public int getShortQty() {
		return shortQty;
	}
	public void setShortQty(int shortQty) {
		this.shortQty = shortQty;
	}
	
	
	
}
