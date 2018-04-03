package com.tanzania.comtech.msafiriapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tanzania.comtech.msafiriapp.Bus.Activity_list_bus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences busJson;
    private TextView mTextMessage;
    JSONObject studentsObj;
    Button goNext;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_bus_ticket);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_ship_ticket);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_train_ticket);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.select_source_destination);

        mTextMessage = (TextView) findViewById(R.id.textView2);
      //  BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        busJson = getSharedPreferences("MY_BUSES", Context.MODE_PRIVATE);


        Log.e("Test","Tested");
        fill_array_buses();
        Log.e("This a json datas " , String.valueOf(studentsObj));

        SharedPreferences.Editor editor = busJson.edit();
        editor.putString("BusJson", String.valueOf(studentsObj));
        editor.apply();
        goNext = (Button)findViewById(R.id.select_source_destination_next_button);
        goNext.setOnClickListener(this);
    }

    public void requestJson(){
        String Canceltag = "TagCancel";

        JSONObject object = null;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, "", object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
        });
    }

    public  void fill_array_buses(){
        JSONObject bus1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i <= 10 ; i++) {
            try {
                bus1.put("bus_name", "Bus name "  + i);
                bus1.put("bus_id", i);
                bus1.put("left_seat", 2);
                bus1.put("right_seat", 3);
                bus1.put("model", "U Tong");
                bus1.put("phone_number", "0685646765");
                bus1.put("visible", false);
                bus1.put("source", "Dar es salaam");
                bus1.put("destination", "Dar es salaam");
                bus1.put("departure", "07:00 AM");
                bus1.put("check_in", "06:00 AM");
                bus1.put("fear_price", "150,00 Tzs");
                bus1.put("available_seat", "20 seats");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(bus1);
        }

         studentsObj = new JSONObject();
        try {
            studentsObj.put("buses", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_source_destination_next_button:
                Intent select_bus_activity = new Intent(getApplicationContext(), Activity_list_bus.class);
                startActivity(select_bus_activity);
                break;
        }
    }
}
