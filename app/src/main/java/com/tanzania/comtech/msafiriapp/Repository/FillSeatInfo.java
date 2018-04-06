package com.tanzania.comtech.msafiriapp.Repository;

/**
 * Created by programing on 4/6/2018.
 */

public class FillSeatInfo {
    private String passengerName;
    private String labelPassengerName;
    private String seatNo;

    public FillSeatInfo(String passengerName, String labelPassengerName, String seatNo) {
        this.passengerName = passengerName;
        this.labelPassengerName = labelPassengerName;
        this.seatNo = seatNo;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getLabelPassengerName() {
        return labelPassengerName;
    }

    public String getSeatNo() {
        return seatNo;
    }
}
