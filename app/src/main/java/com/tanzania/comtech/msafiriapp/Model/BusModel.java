package com.tanzania.comtech.msafiriapp.Model;

import com.tanzania.comtech.msafiriapp.API.Links;

/**
 * Created by programing on 3/31/2018.
 */

public class BusModel {
    private String company_id;
    private String companyName;
    private int total_buses;
    private String website_url;
    private String email;
    private String call;
    private String profile;

    private String source, destination, date;

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDate() {
        return date;
    }

    public BusModel(String company_id, String companyName, int total_buses, String website_url, String email, String call, String profile, String source, String destination, String date) {
        this.company_id = company_id;
        this.companyName = companyName;
        this.total_buses = total_buses;
        this.website_url = website_url;
        this.email = email;
        this.call = call;
        this.profile = profile;
        this.source = source;
        this.destination = destination;
        this.date = date;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public int getTotal_buses() {
        return total_buses;
    }

    public void setTotal_buses(int total_buses) {
        this.total_buses = total_buses;
    }

    public String getWebsite_url() {
        return website_url;
    }

    public void setWebsite_url(String website_url) {
        this.website_url = website_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCall() {
        return call;
    }

    public void setCall(String call) {
        this.call = call;
    }
}
