package com.arcis.vgil.model;

import java.io.Serializable;

/**
 * Created by jaim on 12/28/2016.
 */

public class DealerShipDetailsModel implements Serializable {


    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }


    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getGarageName() {
        return GarageName;
    }

    public void setGarageName(String garageName) {
        GarageName = garageName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private String ContactName;
    private String Mobile;
    private String GarageName;
    private String Address;
    private String City;
    private String Email;

}
