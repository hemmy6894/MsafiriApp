package com.tanzania.comtech.msafiriapp.Bus;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.tanzania.comtech.msafiriapp.Adapter.BusAdapter;
import com.tanzania.comtech.msafiriapp.R;
import com.tanzania.comtech.msafiriapp.Repository.BusRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_list_bus extends AppCompatActivity {

    SharedPreferences busPreference;
    String jsonObj;

    ArrayList<BusRepository> busRepositories;
    ListView busList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_layout_list_bus);

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

    public void getBusList(String datas) throws JSONException {
        JSONObject object = new JSONObject(datas);
        JSONArray jsonArray = object.getJSONArray("buses");

        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject buses = jsonArray.getJSONObject(i);
            busRepositories.add(new BusRepository(
                    buses.getString("bus_name"),
                    buses.getString("model"),
                    buses.getString("source"),
                    buses.getString("destination"),
                    buses.getString("phone_number"),
                    buses.getBoolean("visible"),
                    buses.getInt("left_seat"),
                    buses.getInt("right_seat"),
                    buses.getString("check_in"),
                    buses.getString("departure"),
                    buses.getString("fear_price"),
                    buses.getString("available_seat"),
                    buses.getString("bus_id")
            ));
        }

        BusAdapter flightAdapter = new BusAdapter(getApplicationContext(),R.layout.element_single_bus_view_og,busRepositories);
        busList.setAdapter(flightAdapter);
        //Log.e("Reached","Ok reached there");
    }
}
