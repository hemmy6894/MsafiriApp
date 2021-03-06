package com.tanzania.comtech.msafiriapp.Bus;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Adapter.BusAdapter;
import com.tanzania.comtech.msafiriapp.Adapter.CompanyAdapter;
import com.tanzania.comtech.msafiriapp.Model.CompanyModel;
import com.tanzania.comtech.msafiriapp.R;
import com.tanzania.comtech.msafiriapp.Model.BusModel;
import com.tanzania.comtech.msafiriapp.Time.TimeVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_list_bus extends AppCompatActivity {

    SharedPreferences busPreference, routeInformation;
    String jsonObj, dateSend;

    ArrayList<CompanyModel> busRepositories;
    ListView busList;

    TextView source, destination, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_layout_list_bus);

        routeInformation = getSharedPreferences("routeInfo",Context.MODE_PRIVATE);

        source = (TextView)findViewById(R.id.bus_layout_list_bus_source);
        destination = (TextView)findViewById(R.id.bus_layout_list_bus_destination);
        date = (TextView)findViewById(R.id.bus_layout_list_bus_today);

        source.setText(routeInformation.getString("from","source"));
        destination.setText(routeInformation.getString("to","destination"));
        dateSend = routeInformation.getInt("day_of_month",0) + "-" + String.valueOf(routeInformation.getInt("month",0) + 1) + "-" + routeInformation.getInt("year",0);
        date.setText(date_viewer(routeInformation.getInt("day_of_month",0),routeInformation.getInt("day_of_week",0),routeInformation.getInt("month",0),routeInformation.getInt("year",0)));

        busPreference = getSharedPreferences("MY_BUSES_TWO", Context.MODE_PRIVATE);
        jsonObj = busPreference.getString("BusJson",null);

       // Log.e("My Json Data",jsonObj);
        busRepositories = new ArrayList<>();

        busList = (ListView)findViewById(R.id.bus_layout_list_bus_list);

        try {
            getBusList(jsonObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String date_viewer(int day_of_month, int day_of_week, int month, int year){
        return TimeVariables.weeksNames[day_of_week] + ", " + day_of_month + " " + TimeVariables.monthNames[month] + ", " + year;
    }

    public void getBusList(String datas) throws JSONException {
        JSONObject object = new JSONObject(datas);
        JSONArray jsonArray = object.getJSONArray("buses");

        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject buses = jsonArray.getJSONObject(i);
            busRepositories.add(new CompanyModel(
                    buses.getString(getString(R.string.shared_departure_time)),
                    buses.getString(getString(R.string.shared_arrival_time)),
                    buses.getString(getString(R.string.shared_fare)),
                    buses.getString(getString(R.string.shared_discount)),
                    buses.getString(getString(R.string.shared_processing_fee)),
                    buses.getBoolean(getString(R.string.shared_sch_visible)),
                    buses.getString(getString(R.string.shared_sch_id)),
                    buses.getString(getString(R.string.shared_session)),
                    buses.getString(getString(R.string.shared_estimated_time)),
                    buses.getString(getString(R.string.shared_min_booking_hrs)),
                    buses.getString(getString(R.string.shared_bus_name)),
                    buses.getString(getString(R.string.shared_seat_type)),
                    buses.getString(getString(R.string.shared_model)),
                    buses.getInt(getString(R.string.bus_max_seat_no)),
                    buses.getBoolean(getString(R.string.shared_bus_last_seat_filled)),
                    buses.getString(getString(R.string.shared_bus_driver_incharge)),
                    buses.getString(getString(R.string.shared_phone)),
                    buses.getBoolean(getString(R.string.shared_bus_visible)),
                    buses.getString(getString(R.string.json_status)),
                    buses.getString(getString(R.string.shared_bus_profile)),
                    buses.getString(getString(R.string.shared_bus_id)),
                    buses.getString(getString(R.string.shared_company))
            ));
        }

        BusAdapter flightAdapter = new BusAdapter(getApplicationContext(),R.layout.element_single_bus_view_og,busRepositories);
        busList.setAdapter(flightAdapter);
        //Log.e("Reached","Ok reached there");

        SharedPreferences.Editor clearPreference = busPreference.edit();
        clearPreference.clear();
        clearPreference.apply();
    }
}
