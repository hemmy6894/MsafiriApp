package com.tanzania.comtech.msafiriapp.Repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;
import com.tanzania.comtech.msafiriapp.R;

import java.util.HashMap;
import java.util.Map;

public class HoldUnHoldSeat {
    Context context;

    String cancelTag = "holdingUnHoldingSeat";
    public HoldUnHoldSeat(Context context) {
        this.context = context;
    }

    public void holdingSeat(final Map<String, String> holdingValue){
        String link = BusApi.holdingSeat + holdingValue.get(context.getString(R.string.map_sch_pool_id)) + "/" + holdingValue.get(context.getString(R.string.map_sch_date_id)) + "/" + holdingValue.get(context.getString(R.string.map_booking_ref));
        StringRequest request = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("StringReturned","sms " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(context.getString(R.string.shared_customer_id),holdingValue.get(context.getString(R.string.shared_customer_id)));
                params.put("seat_no",holdingValue.get("seat_no"));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                SharedPreferences token = context.getSharedPreferences(context.getString(R.string.shared_preference_session),Context.MODE_PRIVATE);
                String stringToken = token.getString(context.getString(R.string.shared_token),"");
                headers.put(context.getString(R.string.map_header_parameter), stringToken);
                return headers;
            }
        };
        AppSingleton.getInstance(context).addToRequestQueue(request,cancelTag);
    }

    public void unHoldingSeat(final Map<String, String> unHoldingValue){
        String link = BusApi.unHoldingSeat + unHoldingValue.get(context.getString(R.string.map_booking_ref)) + "/" + unHoldingValue.get(context.getString(R.string.shared_customer_id)) + "/" + unHoldingValue.get("seat_no");
        StringRequest request = new StringRequest(Request.Method.DELETE, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("StringReturned","sms " + response);
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
        AppSingleton.getInstance(context).addToRequestQueue(request,cancelTag);
    }
}
