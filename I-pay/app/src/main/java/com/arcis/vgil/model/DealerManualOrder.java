package com.arcis.vgil.model;

public class DealerManualOrder {

	private String productCode;
	private String productNAme;
	private String quantity;
	private String mobileno;
	private String city;
	private String dealerName;
	private double unitPrice;
	private double discountpercentage;
	private String dealerID;
	private String productId;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getDealerID() {
		return dealerID;
	}
	public void setDealerID(String dealerID) {
		this.dealerID = dealerID;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getDiscountpercentage() {
		return discountpercentage;
	}
	public void setDiscountpercentage(double discountpercentage) {
		this.discountpercentage = discountpercentage;
	}
	
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductNAme() {
		return productNAme;
	}
	public void setProductNAme(String productNAme) {
		this.productNAme = productNAme;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Integer.parseInt(productCode);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		boolean isequal = false;
		if(o instanceof DealerManualOrder){
			String id = ((DealerManualOrder)o).getProductCode();
			if(id.equals(productCode)){
				isequal = true;
			}else{
				isequal = false;
			}
		}
		return isequal;
	}
	
}
