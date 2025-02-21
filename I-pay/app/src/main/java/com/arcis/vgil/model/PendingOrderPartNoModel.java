package com.arcis.vgil.model;

import java.io.Serializable;

/**
 * Created by Jaim on 1/27/2017.
 */

public class PendingOrderPartNoModel implements Serializable {

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getGIT() {
        return GIT;
    }

    public void setGIT(String GIT) {
        this.GIT = GIT;
    }

    private String ItemCode;
    private String Quantity;
    private String GIT;

}
