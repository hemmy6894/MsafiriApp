package com.tanzania.comtech.msafiriapp.Repository;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by programing on 4/17/2018.
 */

public class LinkTest {
    Context context;
    private String link_url;
    private int method;

    public LinkTest(Context context, String link_url, int method) {
        this.context = context;
        this.link_url = link_url;
        this.method = method;
    }

    public void testUrl(){
        StringRequest request = new StringRequest(method, link_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Returned SMS","sms " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("_id","hajjakjajyshh7asuiwdn");
                map.put("customer_id","hajjakjajyshh7asuiwdn");
                map.put("otp_secrete","hajjakjajyshh7asuiwdn");
                return map;
            }
        };
        AppSingleton.getInstance(context).addToRequestQueue(request,"Done");
    }
}
