package com.arcis.vgil.model;

public class DealerProduct {


	private String code;
	private String id;
	private String productName;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.code;
	}
	
}
