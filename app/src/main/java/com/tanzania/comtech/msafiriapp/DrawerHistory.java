package com.tanzania.comtech.msafiriapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Adapter.DrawableAdapter;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;
import com.tanzania.comtech.msafiriapp.Helpers.JavaStringOperation;
import com.tanzania.comtech.msafiriapp.Model.DrawerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DrawerHistory extends AppCompatActivity {

    ListView listView;
    TextView textView;
    TextView drawer_title;
    ProgressBar progressBar;

    ArrayList<DrawerModel> populatedMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_history);

        progressBar = (ProgressBar)findViewById(R.id.drawer_progress);
        textView = (TextView)findViewById(R.id.drawer_text);
        drawer_title = (TextView)findViewById(R.id.drawer_title);
        listView = (ListView) findViewById(R.id.list_drawer_item);

        populatedMode = new ArrayList<>();

        String string = getIntent().getStringExtra("EXTRA_TYPE");
        drawer_title.setText(JavaStringOperation.replaceCapitalize(string));
        loadHistoryData(string);
    }

    public void loadHistoryData(final String string){
        String cancel = "CancelLoadingData";
        final SharedPreferences token = getSharedPreferences(getString(R.string.shared_preference_session), Context.MODE_PRIVATE);
        String customerId = token.getString(getString(R.string.shared_customer_id),"");;
        String link = BusApi.customerHistoryBooking  + customerId + "/" + string;
        StringRequest requestData = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Log.e("response","my " + response);
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean status = jsonObject.getBoolean(getString(R.string.json_status));
                    if(status){
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        int totalNumber = dataArray.length();
                        Log.e("Date ", "Num " + totalNumber + " data : " + dataArray.toString());
                        if(totalNumber > 0){
                            listView.setVisibility(View.VISIBLE);
                            for (int i = 0; i < totalNumber; i++){
                                JSONObject objectData =  dataArray.getJSONObject(i);
                                JSONObject booked_seat = objectData.getJSONObject("booked_seat");
                                JSONObject bus = objectData.getJSONObject("bus");
                                JSONObject company = objectData.getJSONObject("company");
                                populatedMode.add(new DrawerModel(
                                        objectData.getString("for_date"),
                                        objectData.getString("arrival_time"),
                                        booked_seat.getString("fare"),
                                        booked_seat.getString("discount"),
                                        bus.getBoolean("visible"),
                                        "20 hours",
                                        "Ticket No : " + booked_seat.getString("ticket_no"),
                                        bus.getString("bus_name"),
                                        bus.getString("model"),
                                        bus.getInt("max_seat_no"),
                                        "Ticket No : " + booked_seat.getString("ticket_no"),
                                        bus.getBoolean("visible"),
                                        objectData.getString("from"),
                                        objectData.getString("to"),
                                        booked_seat.getString("_id"),
                                        objectData.getString("_id")
                                ));

                                DrawableAdapter drawableAdapter = new DrawableAdapter(getApplicationContext(),R.layout.drawable_element_view,populatedMode);
                                listView.setAdapter(drawableAdapter);
                            }
                        }else {
                            textView.setVisibility(View.VISIBLE);
                        }
                    }else{
                        textView.setVisibility(View.VISIBLE);
                        textView.setText("Error");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                textView.setText("Error in app " + string);
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
