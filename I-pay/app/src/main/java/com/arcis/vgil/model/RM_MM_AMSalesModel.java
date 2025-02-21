package com.arcis.vgil.model;

import java.io.Serializable;

/**
 * Created by Jaim on 1/28/2017.
 */

public class RM_MM_AMSalesModel implements Serializable {

    public String getAMID() {
        return AMID;
    }

    public void setAMID(String AMID) {
        this.AMID = AMID;
    }

    public String getAMName() {
        return AMName;
    }

    public void setAMName(String AMName) {
        this.AMName = AMName;
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

    private String AMID;
    private String AMName;
    private String LYValue;
    private String TYValue;
}
