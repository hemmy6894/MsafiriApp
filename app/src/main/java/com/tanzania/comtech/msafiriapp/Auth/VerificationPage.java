package com.tanzania.comtech.msafiriapp.Auth;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Helpers.ContDownTimer;
import com.tanzania.comtech.msafiriapp.R;

public class VerificationPage extends AppCompatActivity {

    Button sendOpt, resendOpt;
    EditText editTextOpt;

    CountDownTimer count = null;
    TextView myText;

    private boolean resending = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_page_layout);

        sendOpt = (Button)findViewById(R.id.verification_submit);
        resendOpt = (Button)findViewById(R.id.verification_resend);
        editTextOpt = (EditText) findViewById(R.id.verification_otp);

        myText = (TextView)findViewById(R.id.count_down_time);

       ContDownTimer countDownTimer = new ContDownTimer(sendOpt,editTextOpt,myText,getApplicationContext(),resending,resendOpt);
       countDownTimer.countDownCheck();
    }
}
