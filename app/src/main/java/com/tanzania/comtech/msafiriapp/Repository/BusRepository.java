package com.tanzania.comtech.msafiriapp.Repository;

/**
 * Created by programing on 3/31/2018.
 */

public class BusRepository {
    private String bus_id;
    private String bus_name;
    private String bus_model;
    private String source;
    private String destination;
    private String phone_number;
    private boolean is_bus_visible;
    private int left_seat;
    private int right_seat;
    private String check_in;
    private String departure_time;
    private String fear_price = "500,000 Tzs";
    private String available_seat = "30 seats";

    public BusRepository(String bus_name, String bus_model, String source, String destination, String phone_number, boolean is_bus_visible, int left_seat, int right_seat,String check_in,String departure_time,String fear_price,String available_seat,String bus_id) {
        this.bus_name = bus_name;
        this.bus_model = bus_model;
        this.source = source;
        this.destination = destination;
        this.phone_number = phone_number;
        this.is_bus_visible = is_bus_visible;
        this.left_seat = left_seat;
        this.right_seat = right_seat;
        this.check_in = check_in;
        this.departure_time = departure_time;
        this.available_seat = available_seat;
        this.fear_price = fear_price;
        this.bus_id = bus_id;
    }

    public String getBus_name() {
        return bus_name;
    }
    public String getCheck_in() {
        return check_in;
    }

    public String getDeparture_time() {
        return departure_time;
    }
    public String getBus_model() {
        return bus_model;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public boolean isIs_bus_visible() {
        return is_bus_visible;
    }

    public int getLeft_seat() {
        return left_seat;
    }

    public int getRight_seat() {
        return right_seat;
    }

    public String getBus_id() {
        return bus_id;
    }

    public String getFear_price() {
        return fear_price;
    }

    public String getAvailable_seat() {
        return available_seat;
    }
}
