package com.tanzania.comtech.msafiriapp.Auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.tanzania.comtech.msafiriapp.API.BusApi;
import com.tanzania.comtech.msafiriapp.Helpers.ContDownTimer;
import com.tanzania.comtech.msafiriapp.Model.LinkTest;
import com.tanzania.comtech.msafiriapp.Model.VerifyUser;
import com.tanzania.comtech.msafiriapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
