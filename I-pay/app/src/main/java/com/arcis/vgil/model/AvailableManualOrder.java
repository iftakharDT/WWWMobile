package com.arcis.vgil.model;

public class AvailableManualOrder {
	private String ProductID;
	private String Qty;
	// used as a product available or no
	private String availability;
    private String pending_order;
    private String Description;
    private String AvailableQty;

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getQty() {
        return Qty;
    }

    public void setQty(String qty) {
        Qty = qty;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPending_order() {
        return pending_order;
    }

    public void setPending_order(String pending_order) {
        this.pending_order = pending_order;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAvailableQty() {
        return AvailableQty;
    }

    public void setAvailableQty(String availableQty) {
        AvailableQty = availableQty;
    }
}
