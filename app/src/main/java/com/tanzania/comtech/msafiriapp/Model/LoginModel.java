package com.tanzania.comtech.msafiriapp.Model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;
import com.tanzania.comtech.msafiriapp.Helpers.DirectUserByRole;
import com.tanzania.comtech.msafiriapp.MainActivity;
import com.tanzania.comtech.msafiriapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by programing on 4/6/2018.
 */

public class LoginModel {
    private Context context;

    private SharedPreferences userSharedPreference;
    public LoginModel(Context context) {
        this.context = context;
        userSharedPreference = context.getSharedPreferences(context.getString(R.string.shared_preference_session), Context.MODE_PRIVATE);
    }

    public void logInToMsafiriApi(final String textUsername, final String textPassword, final TextView hiddenSms,final ProgressBar progressBar) {
        final String cancelTag = "onLoginFailure";

        StringRequest requestLogin = new StringRequest(Request.Method.POST, BusApi.loginToSystem, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(cancelTag,response);
                try {
                    JSONObject loginJsonObject = new JSONObject(response);
                    String status = loginJsonObject.getString(context.getString(R.string.json_status));

                    if(status.equals("OK")) {
                        JSONObject dataDetails = loginJsonObject.getJSONObject("data");
                        JSONObject userDetails = dataDetails.getJSONObject("user");
                        String tokenDetails = dataDetails.getString(context.getString(R.string.shared_token));

                        String firstName = userDetails.getString(context.getString(R.string.shared_first_name));
                        String lastName = userDetails.getString(context.getString(R.string.shared_last_name));
                        String email = userDetails.getString(context.getString(R.string.shared_email));
                        String gender = userDetails.getString(context.getString(R.string.shared_gender));
                        String address = userDetails.getString(context.getString(R.string.shared_address));
                        String role = userDetails.getString(context.getString(R.string.shared_role));
                        String customer_id = userDetails.getString(context.getString(R.string.shared_id));
                        String phone_no = userDetails.getString(context.getString(R.string.shared_phone_number));
                        boolean phone_verified = userDetails.getBoolean(context.getString(R.string.shared_phone_verified));


                        SharedPreferences.Editor userEditor = userSharedPreference.edit();
                        userEditor.putString(context.getString(R.string.shared_first_name), firstName);
                        userEditor.putString(context.getString(R.string.shared_last_name), lastName);
                        userEditor.putString(context.getString(R.string.shared_email), email);
                        userEditor.putString(context.getString(R.string.shared_gender), gender);
                        userEditor.putString(context.getString(R.string.shared_address), address);
                        userEditor.putString(context.getString(R.string.shared_role), role);
                        userEditor.putString(context.getString(R.string.shared_phone_number), phone_no);
                        userEditor.putBoolean(context.getString(R.string.shared_phone_verified), phone_verified);
                        userEditor.putString(context.getString(R.string.shared_token), tokenDetails);
                        userEditor.apply();

                        SharedPreferences booking = context.getSharedPreferences(context.getString(R.string.shared_preference_booking_info),Context.MODE_PRIVATE);
                        SharedPreferences.Editor editorBook = booking.edit();
                        editorBook.putString(context.getString(R.string.shared_customer_id), customer_id);
                        editorBook.apply();


                        progressBar.setVisibility(View.GONE);
                        new DirectUserByRole(context,role);
                        hidden_text_view("Success Login",hiddenSms);
                    }else if(status.equals("ERROR")){
                        String message = loginJsonObject.getString("message");
                        progressBar.setVisibility(View.GONE);
                        hidden_text_view(message,hiddenSms);
                    }else{
                        progressBar.setVisibility(View.GONE);
                        hidden_text_view("Application Error",hiddenSms);
                    }
                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    hidden_text_view("Application Error",hiddenSms);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                hidden_text_view("Application Error",hiddenSms);
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(context.getString(R.string.map_email),textUsername);
                params.put(context.getString(R.string.map_password),textPassword);
                return params;
            }
        };
        AppSingleton.getInstance(context).addToRequestQueue(requestLogin,cancelTag);
    }


    private void hidden_text_view(String text,TextView hiddenSms){
        hiddenSms.setVisibility(View.VISIBLE);
        hiddenSms.setTextColor(Color.RED);
        hiddenSms.setText(text);
    }
}
