package com.tanzania.comtech.msafiriapp.Bus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Adapter.CompanyAdapter;
import com.tanzania.comtech.msafiriapp.Model.BusModel;
import com.tanzania.comtech.msafiriapp.R;
import com.tanzania.comtech.msafiriapp.Time.TimeVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_list_company extends AppCompatActivity {

    SharedPreferences busPreference, routeInformation;
    String jsonObj, dateSend;

    ArrayList<BusModel> busRepositories;
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
            busRepositories.add(new BusModel(
                    buses.getString(getString(R.string.shared_company_id)),
                    buses.getString(getString(R.string.shared_company_name)),
                    buses.getInt(getString(R.string.shared_total_buses)),
                    buses.getString(getString(R.string.shared_website_url)),
                    buses.getString(getString(R.string.shared_email)),
                    buses.getString(getString(R.string.shared_phone)),
                    buses.getString(getString(R.string.shared_profile)),
                    source.getText().toString(),
                    destination.getText().toString(),
                    dateSend
            ));
        }

        CompanyAdapter companyAdapter = new CompanyAdapter(getApplicationContext(),R.layout.element_single_bus_view_og,busRepositories);
        busList.setAdapter(companyAdapter);
        //Log.e("Reached","Ok reached there");

        SharedPreferences.Editor clearPreference = busPreference.edit();
        clearPreference.clear();
        clearPreference.apply();
    }
}
