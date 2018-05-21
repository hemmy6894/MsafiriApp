package com.tanzania.comtech.msafiriapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;

import java.util.HashMap;
import java.util.Map;

public class DrawerHistory extends AppCompatActivity {

    protected String type = "";
    DrawerHistory(String type){
        this.type = type;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_history);
        loadHistoryData();
    }

    public void loadHistoryData(){
        String cancel = "CancelLoadingData";
        final SharedPreferences token = getSharedPreferences(getString(R.string.shared_preference_session), Context.MODE_PRIVATE);
        String customerId = token.getString(getString(R.string.shared_customer_id),"");;
        String link = BusApi.customerHistoryBooking  + customerId;
        StringRequest requestData = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response","my " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String stringToken = token.getString(getString(R.string.shared_token),"");
                headers.put(getString(R.string.map_header_parameter), stringToken);
                return headers;
            }
        };

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(requestData,cancel);
    }
}
