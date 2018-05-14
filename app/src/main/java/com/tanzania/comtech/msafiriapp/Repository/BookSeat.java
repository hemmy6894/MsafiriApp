package com.tanzania.comtech.msafiriapp.Repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;
import com.tanzania.comtech.msafiriapp.Helpers.PopActivity;
import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.Helpers.ThanksActivity;
import com.tanzania.comtech.msafiriapp.Payment.SeatInformation;
import com.tanzania.comtech.msafiriapp.R;

import java.util.HashMap;
import java.util.Map;

public class BookSeat {
    Context context;

    public BookSeat(Context context) {
        this.context = context;
    }

    public void bookSeat(final String map_string){
        Map<String, Object> var = new SharedPreferenceAppend(context).readSharedPref(context.getString(R.string.shared_preference_route));
        String link = BusApi.placeBusBooking + var.get(context.getString(R.string.map_sch_pool_id)) + "/" + var.get(context.getString(R.string.map_sch_date_id)) + "/" + var.get(context.getString(R.string.map_booking_ref)) ;
        StringRequest request = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("response ", "taifa "  + response);
                Intent intent = new Intent(context, ThanksActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("passengers",map_string);
                return map;
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
        AppSingleton.getInstance(context).addToRequestQueue(request,"cancel_booking");
    }
}
