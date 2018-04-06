package com.tanzania.comtech.msafiriapp.Model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.AppSingleton;
import com.tanzania.comtech.msafiriapp.MainActivity;

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

    SharedPreferences userSharedPreference;
    public LoginModel(Context context) {
        this.context = context;
        userSharedPreference = context.getSharedPreferences("msafiriAppSession", Context.MODE_PRIVATE);
    }

    public void logInToMsafiriApi(final String textUsername, final String textPassword, final TextView hiddenSms,final ProgressBar progressBar) {
        final String cancelTag = "onLoginFailure";

        StringRequest requestLogin = new StringRequest(Request.Method.POST, BusApi.loginToSystem, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(cancelTag,response);
                try {
                    if(!Objects.equals(response, "Error: null")) {
                        JSONObject loginJsonObject = new JSONObject(response);
                        JSONObject userDetails = loginJsonObject.getJSONObject("user");
                        String tokenDetails = loginJsonObject.getString("token");

                        String firstName = userDetails.getString("first_name");
                        String lastName = userDetails.getString("last_name");
                        String email = userDetails.getString("email");
                        String gender = userDetails.getString("gender");
                        String address = userDetails.getString("address");
                        String role = userDetails.getString("role");
                        String phone_no = userDetails.getString("role");
                        boolean phone_verified = userDetails.getBoolean("phone_verified");


                        SharedPreferences.Editor userEditor = userSharedPreference.edit();
                        userEditor.putString("first_name", firstName);
                        userEditor.putString("last_name", lastName);
                        userEditor.putString("email", email);
                        userEditor.putString("gender", gender);
                        userEditor.putString("address", address);
                        userEditor.putString("role", role);
                        userEditor.putString("phone_no", phone_no);
                        userEditor.putBoolean("phone_verified", phone_verified);
                        userEditor.putString("token", tokenDetails);
                        userEditor.apply();

                        progressBar.setVisibility(View.GONE);
                        Intent chooseRoute = new Intent(context,MainActivity.class);
                        chooseRoute.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(chooseRoute);
                        hidden_text_view("Success Login",hiddenSms);
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
                params.put("email",textUsername);
                params.put("password",textPassword);
                return params;
            }
        };
        AppSingleton.getInstance(context).addToRequestQueue(requestLogin,cancelTag);
    }


    private void hidden_text_view(String text,TextView hiddenSms){
        hiddenSms.setVisibility(View.VISIBLE);
        hiddenSms.setTextColor(Color.WHITE);
        hiddenSms.setText(text);
    }
}
