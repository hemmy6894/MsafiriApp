package com.tanzania.comtech.msafiriapp.Payment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tanzania.comtech.msafiriapp.R;

public class PaymentNormal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_normal);
        new TextToPayFor(getApplicationContext(),PaymentNormal.this);
    }
}
