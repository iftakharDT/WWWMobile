package com.arcis.vgil.model;
/**
 * 
 * @author muni Jai Mishra
 *
 */
public class ProductDetails {
    	 
	private String MRP;
	private int netsellingprice;
	private String RYGstatus;
	private String red;
	private String yellow;
	private String green;
	private String seq;
	private int stockquantity;
	private int productId;
	
	
	private String productCode;
	private String procuctName;
	private int saleid;
	private String partyName;
	private int quantity;
	
	
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public int getSaleid() {
		return saleid;
	}
	public void setSaleid(int saleid) {
		this.saleid = saleid;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProcuctName() {
		return procuctName;
	}
	public void setProcuctName(String procuctName) {
		this.procuctName = procuctName;
	}
	
	
	
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	private String classification;
	
	public String getMRP() {
		return MRP;
	}
	public void setMRP(String mRP) {
		MRP = mRP;
	}
	public int getNetsellingprice() {
		return netsellingprice;
	}
	public void setNetsellingprice(int netsellingprice) {
		this.netsellingprice = netsellingprice;
	}
	public String getRYGstatus() {
		return RYGstatus;
	}
	public void setRYGstatus(String rYGstatus) {
		RYGstatus = rYGstatus;
	}
	public String getRed() {
		return red;
	}
	public void setRed(String red) {
		this.red = red;
	}
	public String getYellow() {
		return yellow;
	}
	public void setYellow(String yellow) {
		this.yellow = yellow;
	}
	public String getGreen() {
		return green;
	}
	public void setGreen(String green) {
		this.green = green;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public int getStockquantity() {
		return stockquantity;
	}
	public void setStockquantity(int stockquantity) {
		this.stockquantity = stockquantity;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Integer.parseInt(productCode);
	}
}
