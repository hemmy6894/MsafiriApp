package com.tanzania.comtech.msafiriapp.Repository;

import android.content.Context;

import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.R;

import org.json.JSONObject;

import java.util.Map;

public class PlaceBusBooking {
    Context context;

    public PlaceBusBooking(Context context) {
        this.context = context;
    }

    public void placeBusBooking(JSONObject object){
        String can = "cancelString";
        Map<String, String> routeInfo = new SharedPreferenceAppend(context).readSharedPref(context.getString(R.string.shared_preference_booking_info));
        String link = BusApi.placeBusBooking + "/";
    }
}
