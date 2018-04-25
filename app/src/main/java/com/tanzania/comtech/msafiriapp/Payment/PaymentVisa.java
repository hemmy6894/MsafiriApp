package com.tanzania.comtech.msafiriapp.Payment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tanzania.comtech.msafiriapp.R;

public class PaymentVisa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_visa);
        new TextToPayFor(getApplicationContext(),PaymentVisa.this);
        new InputVisa(getApplicationContext(),PaymentVisa.this);
    }
}
