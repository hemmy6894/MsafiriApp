package com.tanzania.comtech.msafiriapp.Payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tanzania.comtech.msafiriapp.Helpers.AppSingleton;
import com.tanzania.comtech.msafiriapp.Helpers.ThanksActivity;
import com.tanzania.comtech.msafiriapp.R;

import java.util.HashMap;
import java.util.Map;

public class InputVisa implements View.OnClickListener {
    private Context context;
    private Activity activity;
    private EditText holderName, cardNumber, passport, expireMonth, expireYear, cvv;

    InputVisa(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        inputVisa();
    }

    private void inputVisa(){
        holderName  =    (EditText)activity.findViewById(R.id.input_visa_holder_name);
        cardNumber  =    (EditText)activity.findViewById(R.id.input_visa_card_number);
        passport    =    (EditText)activity.findViewById(R.id.input_visa_passport);
        expireMonth =    (EditText)activity.findViewById(R.id.input_visa_expire_month);
        expireYear  =    (EditText)activity.findViewById(R.id.input_visa_expire_year);
        cvv         =    (EditText)activity.findViewById(R.id.input_visa_cvv);
        Button pay  = (Button) activity.findViewById(R.id.input_visa_pay);
        pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Map<String, String> paymentDetails = new HashMap<String, String>();
            paymentDetails.put("holder_name",holderName.getText().toString());
            paymentDetails.put("card_number",cardNumber.getText().toString());
            paymentDetails.put("passport",passport.getText().toString());
            paymentDetails.put("expire_month",expireMonth.getText().toString());
            paymentDetails.put("expire_year",expireYear.getText().toString());
            paymentDetails.put("cvv",cvv.getText().toString());

            sendDetailsToServer(paymentDetails);

        Intent intent = new Intent(context, ThanksActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void sendDetailsToServer(final Map<String, String> paymentDetails) {
        String cancel = "paymentCancel";
        StringRequest request = new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
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
                return paymentDetails;
            }
        };
        AppSingleton.getInstance(context).addToRequestQueue(request,cancel);
    }
}
