package com.tanzania.comtech.msafiriapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Bus.Activity_list_bus;
import com.tanzania.comtech.msafiriapp.Time.TimeVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences busJson;
    private TextView mTextMessage;
    JSONObject studentsObj;
    Button goNext;
    ImageView swapper;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    EditText fromEditText, toEditText;

    LinearLayout datePickerLayout ;

    TextView datePickerDay, datePickerMonth,datePickerDayName,datePickerYear;


    String sendTextFrom, sendTextTo, sendPickedDate;
    public void create_datePickers(){
        datePickerDay = (TextView)findViewById(R.id.select_source_destination_date_picker_day);
        datePickerMonth = (TextView)findViewById(R.id.select_source_destination_date_picker_month);
        datePickerDayName = (TextView)findViewById(R.id.select_source_destination_date_picker_day_name);
        datePickerYear = (TextView)findViewById(R.id.select_source_destination_date_picker_year);
        myCalendar = Calendar.getInstance();
    }

    public void  initiate_datePickers(){
        int day_of_month = myCalendar.get(Calendar.DAY_OF_MONTH);
        int month = myCalendar.get(Calendar.MONTH);
        int year = myCalendar.get(Calendar.YEAR);

        datePickerDay.setText(String.format("%s", day_of_month));
        datePickerDayName.setText(String.format("%s", TimeVariables.weeksNames[myCalendar.get(Calendar.DAY_OF_WEEK)]));
        datePickerMonth.setText(String.format("%s", TimeVariables.monthNames[month]));
        datePickerYear.setText(String.format("%s", year));

        sendPickedDate = "" + append_zero(day_of_month) + "-" + append_zero(month + 1) +"-"+ append_zero(year);
    }

    public String append_zero(int num){
        if(num < 10){
            return "0" + num;
        }
        return "" + num;
    }


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

        swapper = (ImageView)findViewById(R.id.select_source_destination_swapper);
        fromEditText = (EditText)findViewById(R.id.select_source_destination_source);
        toEditText = (EditText)findViewById(R.id.select_source_destination_destination);
        swapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSwapper();
            }
        });

        busJson = getSharedPreferences("MY_BUSES", Context.MODE_PRIVATE);


        create_datePickers();
        initiate_datePickers();
        populate_layout_date();

        goNext = (Button)findViewById(R.id.select_source_destination_next_button);
        goNext.setOnClickListener(this);
    }


    public void populate_layout_date(){
        datePickerLayout = (LinearLayout) findViewById(R.id.select_source_destination_date_picker);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                initiate_datePickers();
            }

        };

        datePickerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    public void requestJson(final String sendTextFrom, final String sendTextTo,final String sendPickedDate){
        final String Canceltag = "TagCancel";
        final String url = BusApi.routeInformation + "/" + sendPickedDate + "/" + sendTextFrom + "/" + sendTextTo;
        StringRequest requestRoot = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.e(Canceltag,response);
                    Log.e(Canceltag,url);
                    JSONObject routeObject = new JSONObject(response);
                    boolean success = routeObject.getBoolean("success");
                    if(success){
                        JSONArray data = routeObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++){

                            }

//                        bus1.put("bus_name", "Bus name "  + i);
//                        bus1.put("bus_id", i);
//                        bus1.put("left_seat", 2);
//                        bus1.put("right_seat", 3);
//                        bus1.put("model", "U Tong");
//                        bus1.put("phone_number", "0685646765");
//                        bus1.put("visible", false);
//                        bus1.put("source", "Dar es salaam");
//                        bus1.put("destination", "Dar es salaam");
//                        bus1.put("departure", "07:00 AM");
//                        bus1.put("check_in", "06:00 AM");
//                        bus1.put("fear_price", "150,00 Tzs");
//                        bus1.put("available_seat", "20 seats");

                    }
                    //JSONArray routeArray = routeObject.getJSONArray("buses");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences token = getSharedPreferences("msafiriAppSession",Context.MODE_PRIVATE);
                String stringToken = token.getString("token","");
                headers.put("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImVtYWlsIjoiZ3JhbmRAZ21haWwuY29tIiwicGFzc3dvcmQiOiJwYXNzd29yZCJ9LCJpYXQiOjE1MjI5NDQ2ODEsImV4cCI6MjE3NTIyOTQ0NjgxfQ.Q8RDGnAvG0CG3XLTVtTm9wIhtYY-LYY8zb-oH6LNnFI");
                Log.e(Canceltag,stringToken);
                return headers;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(requestRoot,Canceltag);
        /*
        Log.e("Test","Tested");
        fill_array_buses();
        Log.e("This a json datas " , String.valueOf(studentsObj));

        SharedPreferences.Editor editor = busJson.edit();
        editor.putString("BusJson", String.valueOf(studentsObj));
        editor.apply();
        */
    }

    public void setSwapper(){
        String swapper = "";
        String fromText = fromEditText.getText().toString();
        String toText = toEditText.getText().toString();

        swapper = fromText;
        fromText = toText;
        toText = swapper;

        fromEditText.setText(fromText);
        toEditText.setText(toText);
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
                sendTextFrom = fromEditText.getText().toString();
                sendTextTo = toEditText.getText().toString();

                requestJson(sendTextFrom,sendTextTo,sendPickedDate);
                Intent select_bus_activity = new Intent(getApplicationContext(), Activity_list_bus.class);
                //startActivity(select_bus_activity);
                break;
        }
    }
}
