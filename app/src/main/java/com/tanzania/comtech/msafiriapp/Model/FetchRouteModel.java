package com.tanzania.comtech.msafiriapp.Model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.AppSingleton;
import com.tanzania.comtech.msafiriapp.Bus.Activity_list_bus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by programing on 4/6/2018.
 */

public class FetchRouteModel {
    private Context context;
    private int left_seat;
    private int right_seat;
    private SharedPreferences busJson;
    private JSONObject studentsObj;

    public FetchRouteModel(Context context) {
        this.context = context;
        busJson = context.getSharedPreferences("MY_BUSES", Context.MODE_PRIVATE);
    }

    public void requestJson(final String sendTextFrom, final String sendTextTo, final String sendPickedDate, final ProgressBar progressBar){
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
                        JSONObject bus1 = new JSONObject();
                        JSONArray jsonArray = new JSONArray();
                        JSONArray data = routeObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++){
                            JSONObject information = data.getJSONObject(i);
                            JSONObject sch = information.getJSONObject("sch_info");
                            JSONObject buses = information.getJSONObject("bus");
                            JSONObject company = information.getJSONObject("company");

                            left_right_seat(buses.getString("seat_type"));

                            bus1.put("bus_name", buses.getString("bus_name"));
                            bus1.put("bus_id", buses.getString("_id"));
                            bus1.put("left_seat", left_seat);
                            bus1.put("right_seat", right_seat);
                            bus1.put("model", buses.getString("model"));
                            bus1.put("phone_number", information.getString("help_line_no"));
                            bus1.put("visible", buses.getBoolean("visible"));
                            bus1.put("source", "no");
                            bus1.put("destination", "no");
                            bus1.put("departure", sch.getString("departure_time"));
                            bus1.put("check_in", sch.getString("arrival_time"));
                            bus1.put("fear_price", sch.getString("fare"));
                            bus1.put("available_seat", "20 seats");
                            jsonArray.put(bus1);
                        }
                        studentsObj = new JSONObject();
                        studentsObj.put("buses", jsonArray);
                        SharedPreferences.Editor editor = busJson.edit();
                        editor.putString("BusJson", String.valueOf(studentsObj));
                        editor.apply();
                        Intent select_bus_activity = new Intent(context, Activity_list_bus.class);
                        select_bus_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        progressBar.setVisibility(View.GONE);
                        context.startActivity(select_bus_activity);
                    }else{
                        Toast.makeText(context,"No Route",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences token = context.getSharedPreferences("msafiriAppSession",Context.MODE_PRIVATE);
                String stringToken = token.getString("token","");
                headers.put("Authorization", stringToken);
                return headers;
            }
        };

        AppSingleton.getInstance(context).addToRequestQueue(requestRoot,Canceltag);
    }

    public void left_right_seat(String seat_type){
        if(seat_type == "2_X_3"){
            left_seat = 2;
            right_seat = 3;
        }else if(seat_type == "2_X_2"){
            left_seat = 2;
            right_seat = 2;
        }else if(seat_type == "1_X_2"){
            left_seat = 1;
            right_seat = 2;
        }
    }
}
