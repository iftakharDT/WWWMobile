package com.arcis.vgil.model;

import java.io.Serializable;

/**
 * Created by Jaim on 1/28/2017.
 */

public class RM_MM_DealerSalesModel implements Serializable {
    public String getDealerID() {
        return DealerID;
    }

    public void setDealerID(String dealerID) {
        DealerID = dealerID;
    }

    public String getDealerShipName() {
        return DealerShipName;
    }

    public void setDealerShipName(String dealerShipName) {
        DealerShipName = dealerShipName;
    }

    public String getDealerCode() {
        return DealerCode;
    }

    public void setDealerCode(String dealerCode) {
        DealerCode = dealerCode;
    }

    public String getLYValue() {
        return LYValue;
    }

    public void setLYValue(String LYValue) {
        this.LYValue = LYValue;
    }

    public String getTYValue() {
        return TYValue;
    }

    public void setTYValue(String TYValue) {
        this.TYValue = TYValue;
    }

    private String DealerID;
    private String DealerShipName;
    private String DealerCode;
    private String LYValue;
    private String TYValue;

   }
