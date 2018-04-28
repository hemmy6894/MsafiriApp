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
import com.tanzania.comtech.msafiriapp.Bus.Activity_list_company;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;
import com.tanzania.comtech.msafiriapp.Bus.Activity_list_bus;
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

public class FetchRouteRepository {
    private Context context;
    private int left_seat;
    private int right_seat;
    private SharedPreferences busJson;
    private JSONObject studentsObj;

    public FetchRouteRepository(Context context) {
        this.context = context;
        busJson = context.getSharedPreferences("MY_BUSES", Context.MODE_PRIVATE);
    }

    public void requestJson(final String sendTextFrom, final String sendTextTo, final String sendPickedDate, final ProgressBar progressBar){
        final String Canceltag = "TagCancel";
        final String url = BusApi.routeInformationCompany + "/" + sendPickedDate + "/" + sendTextFrom + "/" + sendTextTo;
        StringRequest requestRoot = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.e(Canceltag,response);
                    Log.e(Canceltag,url);
                    JSONObject routeObject = new JSONObject(response);
                    boolean success = routeObject.getBoolean(context.getString(R.string.json_status));


                    if(success){

                        JSONArray jsonArray = new JSONArray();
                        JSONArray data = routeObject.getJSONArray("data");

                        for (int i = 0; i < data.length(); i++){
                            JSONObject information = data.getJSONObject(i);
                            JSONObject company = information.getJSONObject(context.getString(R.string.json_company_information));
                            JSONObject bus1 = new JSONObject();
                                bus1.put(context.getString(R.string.shared_help_line_no), company.getString(context.getString(R.string.shared_help_line_no)));
                                bus1.put(context.getString(R.string.shared_phone), company.getString(context.getString(R.string.shared_phone)));
                                bus1.put(context.getString(R.string.shared_email), company.getString(context.getString(R.string.shared_email)));
                                bus1.put(context.getString(R.string.shared_website_url), company.getString(context.getString(R.string.shared_website_url)));
                                bus1.put(context.getString(R.string.shared_profile), company.getString(context.getString(R.string.shared_profile)));
                                bus1.put(context.getString(R.string.shared_company_name), company.getString(context.getString(R.string.shared_company_name)));
                                bus1.put(context.getString(R.string.shared_company_id), company.getString(context.getString(R.string.shared_id)));
                                bus1.put(context.getString(R.string.shared_total_buses), information.getString(context.getString(R.string.shared_total_buses)));
                            jsonArray.put(bus1);

                            Map<String, String> append = new HashMap<String, String>();
                            append.put(context.getString(R.string.shared_company_id),company.getString(context.getString(R.string.shared_id)));
                            new SharedPreferenceAppend(context).appendSharedPref(append,context.getString(R.string.shared_preference_booking_info));
                        }
                        studentsObj = new JSONObject();
                        studentsObj.put("buses", jsonArray);
                        SharedPreferences.Editor editor = busJson.edit();
                        editor.putString("BusJson", String.valueOf(studentsObj));
                        editor.apply();
                        Intent select_bus_activity = new Intent(context, Activity_list_company.class);
                        select_bus_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        progressBar.setVisibility(View.GONE);
                        context.startActivity(select_bus_activity);
                    }else {
                        Toast.makeText(context,routeObject.getString("msg"),Toast.LENGTH_SHORT).show();
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
                SharedPreferences token = context.getSharedPreferences(context.getString(R.string.shared_preference_session),Context.MODE_PRIVATE);
                String stringToken = token.getString(context.getString(R.string.shared_token),"");
                headers.put(context.getString(R.string.map_header_parameter), stringToken);
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
