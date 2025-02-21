package com.arcis.vgil.model;


public class DealerDealerSale {
	
	
	private String code;
	private String pending_order;
	private String gitQuantity;
	private String inventory;
	private String orderQty;
	private String unitPrice;
	private String ProductID;
	private String Description;
	private String DealerSKU;

	public String getDealerSKU() {
		return DealerSKU;
	}

	public void setDealerSKU(String dealerSKU) {
		DealerSKU = dealerSKU;
	}

	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPending_order() {
		return pending_order;
	}
	public void setPending_order(String pending_order) {
		this.pending_order = pending_order;
	}
	public String getGitQuantity() {
		return gitQuantity;
	}
	public void setGitQuantity(String gitQuantity) {
		this.gitQuantity = gitQuantity;
	}
	public String getInventory() {
		return inventory;
	}
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	public String getOrderQty() {
		return orderQty;
	}
	public void setOrderQty(String orderQty) {
		this.orderQty = orderQty;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}



	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
}
