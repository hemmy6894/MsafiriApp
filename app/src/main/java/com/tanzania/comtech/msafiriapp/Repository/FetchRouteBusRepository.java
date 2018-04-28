package com.tanzania.comtech.msafiriapp.Repository;

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
import com.tanzania.comtech.msafiriapp.Bus.Activity_list_bus;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;
import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by programing on 4/6/2018.
 */

public class FetchRouteBusRepository {
    private Context context;
    private int left_seat;
    private int right_seat;
    private SharedPreferences busJson;
    private JSONObject studentsObj;

    public FetchRouteBusRepository(Context context) {
        this.context = context;
        busJson = context.getSharedPreferences("MY_BUSES_TWO", Context.MODE_PRIVATE);
    }

    public void requestJson(final String sendTextFrom, final String sendTextTo, final String sendPickedDate, String busId){
        final String Canceltag = "TagCancel";

        final String url = BusApi.routeInformation + "/"+ busId + "/" + sendPickedDate + "/" + sendTextFrom + "/" + sendTextTo;
        Log.e(Canceltag,url);
        StringRequest requestRoot = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e(Canceltag,response);
                    System.out.println("Herer we go man how are you gays a grad to see you here we cerebrate our richness");
                    JSONObject routeObject = new JSONObject(response);
                    boolean success = routeObject.getBoolean(context.getString(R.string.json_status));


                    if(success){

                        JSONArray jsonArray = new JSONArray();
                        JSONArray data = routeObject.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++){
                            JSONObject information = data.getJSONObject(i);
                            JSONObject company = information.getJSONObject(context.getString(R.string.json_company_information));
                            JSONObject sch = information.getJSONObject(context.getString(R.string.json_sch_information));
                            JSONObject bus = information.getJSONObject(context.getString(R.string.json_bus_information));
                            JSONObject bus1 = new JSONObject();
                                bus1.put(context.getString(R.string.shared_departure_time), sch.getString(context.getString(R.string.shared_departure_time)));
                                bus1.put(context.getString(R.string.shared_arrival_time), sch.getString(context.getString(R.string.shared_arrival_time)));
                                bus1.put(context.getString(R.string.shared_fare), sch.getString(context.getString(R.string.shared_fare)));
                                bus1.put(context.getString(R.string.shared_discount), sch.getString(context.getString(R.string.shared_discount)));
                                bus1.put(context.getString(R.string.shared_tax), sch.getString(context.getString(R.string.shared_tax)));
                                bus1.put(context.getString(R.string.shared_processing_fee), sch.getString(context.getString(R.string.shared_processing_fee)));
                                bus1.put(context.getString(R.string.shared_sch_visible), sch.getString(context.getString(R.string.shared_visible)));
                                bus1.put(context.getString(R.string.shared_sch_id), sch.getString(context.getString(R.string.shared_id)));
                                bus1.put(context.getString(R.string.shared_session), sch.getString(context.getString(R.string.shared_session)));

                                bus1.put(context.getString(R.string.shared_estimated_time), information.getString(context.getString(R.string.shared_estimated_time)));
                                bus1.put(context.getString(R.string.shared_min_booking_hrs), information.getString(context.getString(R.string.shared_min_booking_hrs)));
                                bus1.put(context.getString(R.string.shared_help_line_no), information.getString(context.getString(R.string.shared_help_line_no)));

                                bus1.put(context.getString(R.string.shared_bus_name), bus.getString(context.getString(R.string.shared_bus_name)));
                                bus1.put(context.getString(R.string.shared_seat_type), bus.getString(context.getString(R.string.shared_seat_type)));
                                bus1.put(context.getString(R.string.shared_model), bus.getString(context.getString(R.string.shared_model)));
                                bus1.put(context.getString(R.string.bus_max_seat_no), bus.getString(context.getString(R.string.bus_max_seat_no)));
                                bus1.put(context.getString(R.string.shared_bus_last_seat_filled), bus.getString(context.getString(R.string.shared_bus_last_seat_filled)));
                                bus1.put(context.getString(R.string.shared_bus_driver_incharge), bus.getString(context.getString(R.string.shared_bus_driver_incharge)));
                                bus1.put(context.getString(R.string.shared_phone), bus.getString(context.getString(R.string.shared_phone)));
                                bus1.put(context.getString(R.string.json_status), bus.getString(context.getString(R.string.json_status)));
                                bus1.put(context.getString(R.string.shared_bus_visible), bus.getString(context.getString(R.string.shared_visible)));
                                bus1.put(context.getString(R.string.shared_bus_profile), bus.getString(context.getString(R.string.shared_profile)));
                                bus1.put(context.getString(R.string.shared_bus_id), bus.getString(context.getString(R.string.shared_id)));
                                bus1.put(context.getString(R.string.shared_company), bus.getString(context.getString(R.string.shared_company)));
                                left_right_seat(bus.getString(context.getString(R.string.shared_seat_type)));
                                bus1.put(context.getString(R.string.shared_left_seat), left_seat);
                                bus1.put(context.getString(R.string.shared_right_seat), right_seat);
                            jsonArray.put(bus1);
                        }
                        studentsObj = new JSONObject();
                        studentsObj.put("buses", jsonArray);
                        SharedPreferences.Editor editor = busJson.edit();
                        editor.putString("BusJson", String.valueOf(studentsObj));
                        editor.apply();
                        Intent select_bus_activity = new Intent(context, Activity_list_bus.class);
                        select_bus_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(select_bus_activity);
                    }else {
                        Toast.makeText(context,routeObject.getString("msg"),Toast.LENGTH_SHORT).show();
                    }
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
                SharedPreferences token = context.getSharedPreferences(context.getString(R.string.shared_preference_session),Context.MODE_PRIVATE);
                String stringToken = token.getString(context.getString(R.string.shared_token),"");
                headers.put(context.getString(R.string.map_header_parameter), stringToken);
                return headers;
            }
        };

        AppSingleton.getInstance(context).addToRequestQueue(requestRoot,Canceltag);
    }

    private void left_right_seat(String seat_type){
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
