package com.tanzania.comtech.msafiriapp.Payment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.Helpers.ThanksActivity;
import com.tanzania.comtech.msafiriapp.R;

public class PaymentNormal extends AppCompatActivity implements View.OnClickListener {

    Button nexttopay;
    EditText phoneNumber;
    boolean isFirstStage = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_normal);
        new TextToPayFor(getApplicationContext(),PaymentNormal.this);

        phoneNumber = (EditText)findViewById(R.id.payment_normal_phone_number);
        phoneNumber.setText((String)new SharedPreferenceAppend(getApplicationContext()).readSharedPref(getString(R.string.shared_preference_session)).get(getString(R.string.shared_phone_number)));
        nexttopay = (Button)findViewById(R.id.payment_normal_button_next);
        nexttopay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (isFirstStage) {
            setContentView(R.layout.activity_payment_token);
            new TextToPayFor(getApplicationContext(),PaymentNormal.this);
            Button ok = (Button)findViewById(R.id.payment_token_button_next);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ThanksActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            isFirstStage = false;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        if (hasCapture) {
            nexttopay.setBackgroundResource(R.drawable.selected_payment);
        }else {
            nexttopay.setBackgroundResource(R.drawable.btn_bg_red);
        }
    }
}
