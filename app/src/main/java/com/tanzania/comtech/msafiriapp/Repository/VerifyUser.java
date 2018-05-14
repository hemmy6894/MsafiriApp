package com.tanzania.comtech.msafiriapp.Repository;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Auth.Activity_Login;
import com.tanzania.comtech.msafiriapp.Auth.VerificationPage;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;
import com.tanzania.comtech.msafiriapp.Helpers.ClearSharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by programing on 4/16/2018.
 */

public class VerifyUser {
    Context context;

    public VerifyUser(Context context) {
        this.context = context;
    }

    public void verifyUser(final Map<String, String> data, Button sendOpt, final EditText editTextOpt, TextView myText, final Context context, boolean resending){
        String cancel = "UserVerificationFail";

        Log.e("Response String" , "res " + cancel);
        StringRequest requestVerification = new StringRequest(Request.Method.POST, BusApi.customerVerification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response String" , "res " + response);
                try {
                    JSONObject object = new JSONObject(response);

                    boolean verification = object.getBoolean("verification");
                    if (verification){
                        Intent i = new Intent(context, Activity_Login.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);

                        //Clean all SignUp Data
                        String[] deleted = new String[]{"SignUp","ReceiveSms"};
                        new ClearSharedPreference(context,deleted,Context.MODE_PRIVATE);
                    }else {
                        Intent i = new Intent(context, VerificationPage.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })  {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("_id",data.get("_id"));
                params.put("customer_id",data.get("customer_id"));
                params.put("otp_secrete",editTextOpt.getText().toString());
                Log.e("Response String" , "res " +params);
                return params;
            }
        };

        AppSingleton.getInstance(context).addToRequestQueue(requestVerification,cancel);
    }
}
