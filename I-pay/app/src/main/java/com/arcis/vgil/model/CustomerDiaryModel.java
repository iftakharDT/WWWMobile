package com.arcis.vgil.model;

import java.io.Serializable;

/**
 * Created by jaim on 3/4/2017.
 */

public class CustomerDiaryModel implements Serializable {


    public String getMasterID() {
        return MasterID;
    }

    public void setMasterID(String masterID) {
        MasterID = masterID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String customerID) {
        CustomerID = customerID;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerTypeID() {
        return CustomerTypeID;
    }

    public void setCustomerTypeID(String customerTypeID) {
        CustomerTypeID = customerTypeID;
    }

    private String MasterID;
    private String CustomerID;
    private String CustomerName;
    private String CustomerTypeID;

}
