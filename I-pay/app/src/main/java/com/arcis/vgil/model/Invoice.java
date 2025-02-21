package com.arcis.vgil.model;

public class Invoice {

	
	/** Invoice data **/
	private int comulativeId;
	private String erpOrderNo;
	private int invoiceId;
	private String invoiceNo;
	private int cfaId;
	private String cfaName;
	private String dealerId;
	private String dealerName;
	private boolean iscformRequired;
	private String dealerLocation;
	private String invoiceStatus;
	private boolean ischeacked;
	
	
	/** Invoice Details Data **/
	
	private int discountsno;
	private int productId;
	private String productCode;
	private int quantity;
	private double unitPrice;
	private double basicPrice;
	private double exciseDuty;
	private double discount;
	private double pretaxamount;
	private double cd;
	private double cdvalue;
	private double cst;
	private double vat;
	private double vatvalue;
	private double pretaxvalue;
	private double netamount;
	private int state;
	/** -------------------**/
	
	public boolean isIscheacked() {
		return ischeacked;
	}
	public void setIscheacked(boolean ischeacked) {
		this.ischeacked = ischeacked;
	}
	public int getDiscountsno() {
		return discountsno;
	}
	public void setDiscountsno(int discountsno) {
		this.discountsno = discountsno;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getBasicPrice() {
		return basicPrice;
	}
	public void setBasicPrice(double basicPrice) {
		this.basicPrice = basicPrice;
	}
	public double getExciseDuty() {
		return exciseDuty;
	}
	public void setExciseDuty(double exciseDuty) {
		this.exciseDuty = exciseDuty;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getPretaxamount() {
		return pretaxamount;
	}
	public void setPretaxamount(double pretaxamount) {
		this.pretaxamount = pretaxamount;
	}
	public double getCd() {
		return cd;
	}
	public void setCd(double cd) {
		this.cd = cd;
	}
	public double getCdvalue() {
		return cdvalue;
	}
	public void setCdvalue(double cdvalue) {
		this.cdvalue = cdvalue;
	}
	public double getCst() {
		return cst;
	}
	public void setCst(double cst) {
		this.cst = cst;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	public double getVatvalue() {
		return vatvalue;
	}
	public void setVatvalue(double vatvalue) {
		this.vatvalue = vatvalue;
	}
	public double getPretaxvalue() {
		return pretaxvalue;
	}
	public void setPretaxvalue(double pretaxvalue) {
		this.pretaxvalue = pretaxvalue;
	}
	public double getNetamount() {
		return netamount;
	}
	public void setNetamount(double netamount) {
		this.netamount = netamount;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	
	public int getComulativeId() {
		return comulativeId;
	}
	public void setComulativeId(int comulativeId) {
		this.comulativeId = comulativeId;
	}
	public String getErpOrderNo() {
		return erpOrderNo;
	}
	public void setErpOrderNo(String erpOrderNo) {
		this.erpOrderNo = erpOrderNo;
	}
	public int getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public int getCfaId() {
		return cfaId;
	}
	public void setCfaId(int cfaId) {
		this.cfaId = cfaId;
	}
	public String getCfaName() {
		return cfaName;
	}
	public void setCfaName(String cfaName) {
		this.cfaName = cfaName;
	}
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public boolean isIscformRequired() {
		return iscformRequired;
	}
	public void setIscformRequired(boolean iscformRequired) {
		this.iscformRequired = iscformRequired;
	}
	public String getDealerLocation() {
		return dealerLocation;
	}
	public void setDealerLocation(String dealerLocation) {
		this.dealerLocation = dealerLocation;
	}
	public String getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	
	public void setIschecked(boolean b) {
		// TODO Auto-generated method stub
		ischeacked = b;
	}
	public boolean isIschecked() {
		// TODO Auto-generated method stub
		return ischeacked;
	}
	
	
	
}
