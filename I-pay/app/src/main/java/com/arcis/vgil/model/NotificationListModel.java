package com.arcis.vgil.model;

import java.io.Serializable;

/**
 * Created by jaim on 5/22/2017.
 */

public class NotificationListModel implements Serializable {

    public String getNotificationType() {
        return NotificationType;
    }

    public void setNotificationType(String notificationType) {
        NotificationType = notificationType;
    }

    public String getContactID() {
        return ContactID;
    }

    public void setContactID(String contactID) {
        ContactID = contactID;
    }

    public String getContactTypeID() {
        return ContactTypeID;
    }

    public void setContactTypeID(String contactTypeID) {
        ContactTypeID = contactTypeID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRowCount() {
        return RowCount;
    }

    public void setRowCount(String rowCount) {
        RowCount = rowCount;
    }

    //'NotificationType': '1','ContactID': '3209','ContactTypeID': '14',
    // 'Message': 'testing in Jamnagar','RowCount': '3'
    private String NotificationType;
    private String ContactID;
    private String ContactTypeID;
    private String Message;
    private String RowCount;

}
