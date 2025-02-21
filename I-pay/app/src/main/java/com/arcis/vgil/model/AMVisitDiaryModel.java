package com.arcis.vgil.model;

import java.io.Serializable;

/**
 * Created by jaim on 2/7/2017.
 */

public class AMVisitDiaryModel implements Serializable {



    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getVisitLogID() {
        return VisitLogID;
    }

    public void setVisitLogID(String visitLogID) {
        VisitLogID = visitLogID;
    }

    public String getCallTime() {
        return CallTime;
    }

    public void setCallTime(String callTime) {
        CallTime = callTime;
    }

    public String getMeetingNotes() {
        return MeetingNotes;
    }

    public void setMeetingNotes(String meetingNotes) {
        MeetingNotes = meetingNotes;
    }

    public String getActionNotes() {
        return ActionNotes;
    }

    public void setActionNotes(String actionNotes) {
        ActionNotes = actionNotes;
    }
    private String VisitLogID;
    private String Date;
    private String CustomerName;
    private String CallTime;
    private String MeetingNotes;
    private String ActionNotes;
}
