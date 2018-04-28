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
import com.tanzania.comtech.msafiriapp.Auth.VerificationPage;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by programing on 4/16/2018.
 */

public class RegisterUser {
    private Context context;

    public RegisterUser(Context context) {
        this.context = context;
    }

    private JSONObject signUpData;
    public void registerUserInSystem(final Map<String,String> data){
        String registerUserCancel = "OnRegisterFail";
        StringRequest request = new StringRequest(Request.Method.POST, BusApi.customerRegistration, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Log.e("response","res" + response);
                    signUpData = new JSONObject();
                    signUpData.put("_id",jsonObject.getString("otp_id"));
                    signUpData.put("token",jsonObject.getString("token"));

                    JSONObject customerObject = jsonObject.getJSONObject("customer");
                    signUpData.put("customer_id",customerObject.getString("_id"));
                    signUpData.put("first_name",customerObject.getString("first_name"));
                    signUpData.put("last_name",customerObject.getString("last_name"));
                    signUpData.put("phone_no",customerObject.getString("phone_no"));
                    signUpData.put("email",customerObject.getString("email"));

                    SharedPreferences shareSignUp = context.getSharedPreferences("SignUp",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = shareSignUp.edit();
                    editor.putString("data",signUpData.toString());
                    editor.apply();

                    Intent i = new Intent(context, VerificationPage.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

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
            protected Map<String, String> getParams() throws AuthFailureError {
                return data;
            }
        };

        AppSingleton.getInstance(context).addToRequestQueue(request,registerUserCancel);
    }
}
