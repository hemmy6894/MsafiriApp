package com.tanzania.comtech.msafiriapp.Model;

import com.tanzania.comtech.msafiriapp.API.Links;

/**
 * Created by programing on 3/31/2018.
 */

public class CompanyModel {
    private String sch_departure_time;
    private String sch_arrival_time;
    private double sch_fare;
    private double sch_discount;
    private double sch_tax;
    private String sch_processing_fee;
    private boolean sch_visible;
    private String sch_id;
    private String sch_session;

    private String estimated_time;
    private String min_booking_hrs;
    private String help_line_no;

    private String bus_name;
    private String bus_seat_type;
    private String bus_model;
    private int bus_max_seat_no;
    private boolean bus_last_seat_filled;
    private String bus_driver_incharge;
    private String bus_phone;
    private String bus_schedulling_type;
    private boolean bus_visible;
    private String bus_status;
    private String bus_profile;
    private String bus_id;
    private String bus_company;
    private String bus__v;

    public CompanyModel(String sch_departure_time, String sch_arrival_time, double sch_fare, double sch_discount, String sch_processing_fee, boolean sch_visible, String sch_id, String sch_session, String estimated_time, String min_booking_hrs, String bus_name, String bus_seat_type, String bus_model, int bus_max_seat_no, boolean bus_last_seat_filled, String bus_driver_incharge, String bus_phone, boolean bus_visible, String bus_status, String bus_profile, String bus_id, String bus_company) {
        this.sch_departure_time = sch_departure_time;
        this.sch_arrival_time = sch_arrival_time;
        this.sch_fare = sch_fare;
        this.sch_discount = sch_discount;
        this.sch_processing_fee = sch_processing_fee;
        this.sch_visible = sch_visible;
        this.sch_id = sch_id;
        this.sch_session = sch_session;
        this.estimated_time = estimated_time;
        this.min_booking_hrs = min_booking_hrs;
        this.bus_name = bus_name;
        this.bus_seat_type = bus_seat_type;
        this.bus_model = bus_model;
        this.bus_max_seat_no = bus_max_seat_no;
        this.bus_last_seat_filled = bus_last_seat_filled;
        this.bus_driver_incharge = bus_driver_incharge;
        this.bus_phone = bus_phone;
        this.bus_visible = bus_visible;
        this.bus_status = bus_status;
        this.bus_profile = bus_profile;
        this.bus_id = bus_id;
        this.bus_company = bus_company;
    }

    public String getSch_departure_time() {
        return sch_departure_time;
    }

    public String getSch_arrival_time() {
        return sch_arrival_time;
    }

    public double getSch_fare() {
        return sch_fare;
    }

    public double getSch_discount() {
        return sch_discount;
    }

    public double getSch_tax() {
        return sch_tax;
    }

    public String getSch_processing_fee() {
        return sch_processing_fee;
    }

    public boolean isSch_visible() {
        return sch_visible;
    }

    public String getSch_id() {
        return sch_id;
    }

    public String getSch_session() {
        return sch_session;
    }

    public String getEstimated_time() {
        return estimated_time;
    }

    public String getMin_booking_hrs() {
        return min_booking_hrs;
    }

    public String getHelp_line_no() {
        return help_line_no;
    }

    public String getBus_name() {
        return bus_name;
    }

    public String getBus_seat_type() {
        return bus_seat_type;
    }

    public String getBus_model() {
        return bus_model;
    }

    public int getBus_max_seat_no() {
        return bus_max_seat_no;
    }

    public boolean isBus_last_seat_filled() {
        return bus_last_seat_filled;
    }

    public String getBus_driver_incharge() {
        return bus_driver_incharge;
    }

    public String getBus_phone() {
        return bus_phone;
    }

    public String getBus_schedulling_type() {
        return bus_schedulling_type;
    }

    public boolean isBus_visible() {
        return bus_visible;
    }

    public String getBus_status() {
        return bus_status;
    }

    public String getBus_profile() {
        return bus_profile;
    }

    public String getBus_id() {
        return bus_id;
    }

    public String getBus_company() {
        return bus_company;
    }

    public String getBus__v() {
        return bus__v;
    }
}
