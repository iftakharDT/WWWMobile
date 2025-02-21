package com.arcis.vgil.trackapp.model;

public class TerettoryListModel {


    private String ID;
    private String Name;
    private String FirmName;
    private String ContactType;
    private String AreaID;
    private String CityID;
    private String City;
    private String State;
    private String MobileNo;
    private String Planned;
    private String Address;
    private double latitude2;
    private double longitude2;
    private String distance ="";
    private String isCalled;

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    private String Area;
    private String StateID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFirmName() {
        return FirmName;
    }

    public void setFirmName(String firmName) {
        FirmName = firmName;
    }

    public String getContactType() {
        return ContactType;
    }

    public void setContactType(String contactType) {
        ContactType = contactType;
    }

    public String getAreaID() {
        return AreaID;
    }

    public void setAreaID(String areaID) {
        AreaID = areaID;
    }

    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStateID() {
        return StateID;
    }

    public void setStateID(String stateID) {
        StateID = stateID;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getPlanned() {
        return Planned;
    }

    public void setPlanned(String planned) {
        Planned = planned;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getLatitude2() {
        return latitude2;
    }

    public void setLatitude2(double latitude2) {
        this.latitude2 = latitude2;
    }

    public double getLongitude2() {
        return longitude2;
    }

    public void setLongitude2(double longitude2) {
        this.longitude2 = longitude2;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIsCalled() {
        return isCalled;
    }

    public void setIsCalled(String isCalled) {
        this.isCalled = isCalled;
    }



}
