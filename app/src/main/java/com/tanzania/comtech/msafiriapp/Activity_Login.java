package com.tanzania.comtech.msafiriapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.API.BusApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_Login extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    TextView hiddenSms;
    Button btnLogin, btnRegister;

    SharedPreferences userSharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        username = (EditText)findViewById(R.id.layout_login_edittext_enter_email);
        password = (EditText)findViewById(R.id.layout_login_edittext_enter_password);

        hiddenSms = (TextView)findViewById(R.id.layout_login_hidden_sms);

        btnLogin = (Button)findViewById(R.id.layout_login_button_login);
        btnRegister = (Button)findViewById(R.id.layout_login_button_regiter);

        userSharedPreference = getSharedPreferences("msafiriAppSession", Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_login_button_login:
                    String textUsername = username.getText().toString();
                    String textPassword = password.getText().toString();
                    logInToMsafiriApi(textUsername,textPassword);
                    hiddenSms.setVisibility(View.VISIBLE);
                    hiddenSms.setText("Under Login");
                break;
            case R.id.layout_login_button_regiter:
                    hiddenSms.setVisibility(View.VISIBLE);
                    hiddenSms.setText("Under construction");
                break;
        }
    }

    private void logInToMsafiriApi(final String textUsername, final String textPassword) {
        final String cancelTag = "onLoginFailure";

        StringRequest requestLogin = new StringRequest(Request.Method.POST, BusApi.loginToSystem, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(cancelTag,response);
                try {
                    if(response != "Error: null") {
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
                        userDetails.put("first_name", firstName);
                        userDetails.put("last_name", lastName);
                        userDetails.put("email", email);
                        userDetails.put("gender", gender);
                        userDetails.put("address", address);
                        userDetails.put("role", role);
                        userDetails.put("phone_no", phone_no);
                        userDetails.put("phone_verified", phone_verified);
                        userDetails.put("token", tokenDetails);

                        Intent chooseRoute = new Intent(getApplicationContext(),MainActivity.class);
                        finish();
                        startActivity(chooseRoute);
                    }else{
                        hiddenSms.setVisibility(View.VISIBLE);
                        hiddenSms.setText("Invalid User name or password");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(cancelTag,"Error : " + error.getMessage());
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
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(requestLogin,cancelTag);
    }
}
