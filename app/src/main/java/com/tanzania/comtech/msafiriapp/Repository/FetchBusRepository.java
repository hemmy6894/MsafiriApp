package com.tanzania.comtech.msafiriapp.Repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;
import com.tanzania.comtech.msafiriapp.Helpers.ReadJsonToMap;
import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.R;
import com.tanzania.comtech.msafiriapp.seat_plan.MainActivity;
import com.tanzania.comtech.msafiriapp.seat_plan.SeatPlanOriginal;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FetchBusRepository {
    Context context;

    public FetchBusRepository(Context context) {
        this.context = context;
       }

    public void fetchBusInfo(final String busId){
        String cancelTag = "failToFetchBusData";
        Map<String, Object> append = new HashMap<>();
        append.put(context.getString(R.string.shared_bus_id),busId);
        new SharedPreferenceAppend(context).appendSharedPref(append,context.getString(R.string.shared_preference_booking_info));

        String url = BusApi.busInformation + busId;
        StringRequest objectRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject busObject = new JSONObject(response);
                    new SharedPreferenceAppend(context).newSharedPref(new ReadJsonToMap(context).readJsonToMap(busObject),context.getString(R.string.shared_preference_bus_data_from_id));
                    new CheckBookedSeat(context).checkBookedSeat();
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

        AppSingleton.getInstance(context).addToRequestQueue(objectRequest,cancelTag);
    }
}
