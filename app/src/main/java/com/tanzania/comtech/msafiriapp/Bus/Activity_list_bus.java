package com.tanzania.comtech.msafiriapp.Bus;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Adapter.BusAdapter;
import com.tanzania.comtech.msafiriapp.R;
import com.tanzania.comtech.msafiriapp.Repository.BusRepository;
import com.tanzania.comtech.msafiriapp.Time.TimeVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_list_bus extends AppCompatActivity {

    SharedPreferences busPreference, routeInformation;
    String jsonObj;

    ArrayList<BusRepository> busRepositories;
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
        date.setText(date_viewer(routeInformation.getInt("day_of_month",0),routeInformation.getInt("day_of_week",0),routeInformation.getInt("month",0),routeInformation.getInt("year",0)));

        busPreference = getSharedPreferences("MY_BUSES", Context.MODE_PRIVATE);
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
            busRepositories.add(new BusRepository(
                    buses.getString(getString(R.string.shared_bus_name)),
                    buses.getString(getString(R.string.shared_model)),
                    buses.getString(getString(R.string.shared_source)),
                    buses.getString(getString(R.string.shared_destination)),
                    buses.getString(getString(R.string.shared_phone_number)),
                    buses.getBoolean(getString(R.string.shared_visible)),
                    buses.getInt(getString(R.string.shared_left_seat)),
                    buses.getInt(getString(R.string.shared_right_seat)),
                    buses.getString(getString(R.string.shared_check_in)),
                    buses.getString(getString(R.string.shared_departure)),
                    buses.getString(getString(R.string.shared_fear_price)),
                    buses.getString(getString(R.string.shared_available_seat)),
                    buses.getString(getString(R.string.shared_bus_id))
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
