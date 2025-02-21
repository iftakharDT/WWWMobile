package com.arcis.vgil.model;

import java.io.Serializable;

/**
 * Created by Jaim on 1/30/2017.
 */

public class RM_MM_SalesPartModel implements Serializable {
    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getLYQuantity() {
        return LYQuantity;
    }

    public void setLYQuantity(String LYQuantity) {
        this.LYQuantity = LYQuantity;
    }

    public String getTYQuantity() {
        return TYQuantity;
    }

    public void setTYQuantity(String TYQuantity) {
        this.TYQuantity = TYQuantity;
    }

    //{ 'Root': { 'data': [{'ItemCode': 'SABD2149D200','LYQuantity': '0','TYQuantity': '148'},
    private String ItemCode;
    private String LYQuantity;
    private String TYQuantity;
}
