package com.arcis.vgil.model;


/**
 * 
 * @author munim
 *
 */
public class DealerSale {

	
	
	private String code;
	private String productName;
	private String classification;
	private double unitPrice;
	private String id;
	private String productID;
	private String currentStock;
	private String minStock;
	private String targetStock;
	private String rlevel;
	private String ylevel;
	private String glevel;
	private String saleoftheday;
	private int salequantity;
	
	
	public int getSalequantity() {
		return salequantity;
	}
	public void setSalequantity(int salequantity) {
		this.salequantity = salequantity;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getCurrentStock() {
		return currentStock;
	}
	public void setCurrentStock(String currentStock) {
		this.currentStock = currentStock;
	}
	public String getMinStock() {
		return minStock;
	}
	public void setMinStock(String minStock) {
		this.minStock = minStock;
	}
	public String getTargetStock() {
		return targetStock;
	}
	public void setTargetStock(String targetStock) {
		this.targetStock = targetStock;
	}
	public String getRlevel() {
		return rlevel;
	}
	public void setRlevel(String rlevel) {
		this.rlevel = rlevel;
	}
	public String getYlevel() {
		return ylevel;
	}
	public void setYlevel(String ylevel) {
		this.ylevel = ylevel;
	}
	public String getGlevel() {
		return glevel;
	}
	public void setGlevel(String glevel) {
		this.glevel = glevel;
	}
	public String getSaleoftheday() {
		return saleoftheday;
	}
	public void setSaleoftheday(String saleoftheday) {
		this.saleoftheday = saleoftheday;
	}
	
	public double getAmount(){
		
		return this.salequantity*unitPrice;
	}
	
}
