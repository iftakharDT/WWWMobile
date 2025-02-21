package com.arcis.vgil.trackapp.model;

/**
 * Created by Ram on 2/19/2020.
 */
public class SubSegmentType {
    private String SubSegmentName;
    private int SubSegmentID;
    private boolean isSubSegmentSelected;


    public String getSubSegmentName() {
        return SubSegmentName;
    }

    public void setSubSegmentName(String subSegmentName) {
        SubSegmentName = subSegmentName;
    }

    public int getSubSegmentID() {
        return SubSegmentID;
    }

    public void setSubSegmentID(int subSegmentID) {
        SubSegmentID = subSegmentID;
    }

    public boolean isSubSegmentSelected() {
        return isSubSegmentSelected;
    }

    public void setSubSegmentSelected(boolean subSegmentSelected) {
        isSubSegmentSelected = subSegmentSelected;
    }
}
