package com.arcis.vgil.model;

public class RM_MM_Stock_Model {
	
	//{ 'Root': { 'data': [{'ID': '19','Code': 'DLU','ProductID': '991',
	//'ProductCode': '2PR261CSDSTD','CurrentStock': '0','PendingOrder': '12','GIT': '0'},
	private String ProductCode;
	private String CurrentStock;
	private String GIT;
	public String getProductCode() {
		return ProductCode;
	}
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}
	public String getCurrentStock() {
		return CurrentStock;
	}
	public void setCurrentStock(String currentStock) {
		CurrentStock = currentStock;
	}
	public String getGIT() {
		return GIT;
	}
	public void setGIT(String gIT) {
		GIT = gIT;
	}
	public String getPendingOrder() {
		return PendingOrder;
	}
	public void setPendingOrder(String pendingOrder) {
		PendingOrder = pendingOrder;
	}
	private String PendingOrder;

}
