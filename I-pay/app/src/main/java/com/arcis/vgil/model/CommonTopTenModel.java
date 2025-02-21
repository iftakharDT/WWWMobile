package com.arcis.vgil.model;

import java.io.Serializable;

/**
 * Created by jaim on 3/21/2017.
 */

public class CommonTopTenModel implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    private String name;
    private String city;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
}
