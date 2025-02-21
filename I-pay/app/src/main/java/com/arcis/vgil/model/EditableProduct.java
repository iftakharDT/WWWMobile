package com.arcis.vgil.model;

public class EditableProduct {

	private String saleid;
	private String productid;
	private String productcode;
	private String saleamount;
	private String saletype;
	private String quantity;
	private boolean ischecked;
	private String salesDetailId;
	
	public String getSalesDetailId() {
		return salesDetailId;
	}
	public void setSalesDetailId(String salesDetailId) {
		this.salesDetailId = salesDetailId;
	}
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
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getSaleamount() {
		return saleamount;
	}
	public void setSaleamount(String saleamount) {
		this.saleamount = saleamount;
	}
	public String getSaletype() {
		return saletype;
	}
	public void setSaletype(String saletype) {
		this.saletype = saletype;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
}
