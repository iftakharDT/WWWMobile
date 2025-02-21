package com.arcis.vgil.model;

public class AM_DealerManualOrder {
	


//	order.setProductCode(partNo.getText().toString());
	//order.setPending(pending_position.getText().toString());
//	order.setgit(git_position.getText().toString());
	//order.setInventory(inventry_position.getText().toString());
//	order.setQuantity(quantity_position.getText().toString());
	
	private String ProductCode;
	private String Pending;
	private String git;
	private String description;
	private String Inventory;
	private String Quantity;
	private String productId;
	private String sku;
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return ProductCode;
	}

	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}

	public String getPending() {
		return Pending;
	}

	public void setPending(String pending) {
		Pending = pending;
	}

	public String getGit() {
		return git;
	}

	public void setGit(String git) {
		this.git = git;
	}

	public String getInventory() {
		return Inventory;
	}

	public void setInventory(String inventory) {
		Inventory = inventory;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return Integer.parseInt(ProductCode);
	}
	
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		boolean isequal = false;
		if(o instanceof AM_DealerManualOrder){
			String id = ((AM_DealerManualOrder)o).getProductCode();
			if(id.equals(ProductCode)){
				isequal = true;
			}else{
				isequal = false;
			}
		}
		return isequal;
	}

	
}
