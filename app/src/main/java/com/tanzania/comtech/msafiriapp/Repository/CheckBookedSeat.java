package com.tanzania.comtech.msafiriapp.Repository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;
import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.R;
import com.tanzania.comtech.msafiriapp.seat_plan.SeatPlanOriginal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckBookedSeat {
    private Context context;
    public CheckBookedSeat(Context context) {
        this.context = context;
    }

    private Map<String, Object> values;
    public void checkBookedSeat(){
        String cancelString = context.getString(R.string.app_name) + "/error";
        SharedPreferences share = context.getSharedPreferences(context.getString(R.string.shared_preference_booking_info),Context.MODE_PRIVATE);
        Map<String, ?> map = share.getAll();

        final String company_id = context.getString(R.string.shared_company_id);
        String bus_id = context.getString(R.string.shared_bus_id);
        SharedPreferences share_two = context.getSharedPreferences(context.getString(R.string.shared_preference_route_info),Context.MODE_PRIVATE);

        Map<String, ?> map2 = share_two.getAll();
        String from = context.getString(R.string.from);
        String to = context.getString(R.string.to);
        String day = context.getString(R.string.day_of_month);
        String month = context.getString(R.string.month);
        String year = context.getString(R.string.year);


        int mooo = Integer.parseInt(String.valueOf(map2.get(month))) + 1;
        final String link = BusApi.viewBusInformation + map.get(company_id) + "/" + map.get(bus_id) + "/" + map2.get(day) + "-" + mooo + "-" + map2.get(year) + "/" +  map2.get(from) + "/" + map2.get(to);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, link, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.e("StringReturned","SMS " + response);
                Map<String, Object> map = new HashMap<>();
                try {
                    map.put(context.getString(R.string.map_sch_date_id),response.getString(context.getString(R.string.map_sch_date_id)));
                    map.put(context.getString(R.string.map_sch_pool_id),response.getString(context.getString(R.string.map_sch_pool_id)));
                    map.put(context.getString(R.string.map_booking_ref),response.getString("_id"));
                    map.put("company",response.getString("company"));

                    new SharedPreferenceAppend(context).appendSharedPref(map,context.getString(R.string.shared_preference_route));


                    JSONArray a = response.getJSONArray("booked_seat");
                    Map<String, Object> mapb = new HashMap<>();
                    for (int i = 0; i < a.length(); i++){
                        JSONObject o = a.getJSONObject(i);
                        Log.e("nuyama za ulimi",o.toString());
                        mapb.put(o.getString("seat_no"),o.getString("on_hold"));
                    }
                    new SharedPreferenceAppend(context).newSharedPref(mapb,"booked_seat");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent seat_plan = new Intent(context, SeatPlanOriginal.class);
                seat_plan.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(seat_plan);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
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
        AppSingleton.getInstance(context).addToRequestQueue(objectRequest,cancelString);
    }
}
