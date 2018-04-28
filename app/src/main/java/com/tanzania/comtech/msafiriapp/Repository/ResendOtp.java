package com.tanzania.comtech.msafiriapp.Repository;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;

import java.util.Map;

/**
 * Created by programing on 4/17/2018.
 */

public class ResendOtp {
    Context context;

    public ResendOtp(Context context) {
        this.context = context;
    }

    public void resendOtpGo(final Map<String, String> map){
        String cancel = "cancelOtp";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, BusApi.customerOtpResend, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };

        AppSingleton.getInstance(context).addToRequestQueue(stringRequest,cancel);
    }
}
