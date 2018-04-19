package com.tanzania.comtech.msafiriapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Model.ResendOtp;
import com.tanzania.comtech.msafiriapp.Model.VerifyUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by programing on 4/16/2018.
 */

public class ContDownTimer {
    private Button sendOpt, resendOpt;
    private EditText editTextOpt;

    private SharedPreferences preferences;
    private CountDownTimer count = null;
    private TextView myText;
    Context context;

    private boolean resending;
    private boolean failToVerify = true;

    public ContDownTimer(final Button sendOpt, final EditText editTextOpt, final TextView myText, final Context context, final boolean resending, final  Button resendOpt) {
        this.sendOpt = sendOpt;
        this.editTextOpt = editTextOpt;
        this.myText = myText;
        this.context = context;
        this.resending = resending;
        this.resendOpt = resendOpt;

        preferences = this.context.getSharedPreferences("ReceiveSms",MODE_PRIVATE);

        sendOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VerifyUser verifyUser = new VerifyUser(context);
                verifyUser.verifyUser(readJsonMap(),sendOpt,editTextOpt,myText,context,resending);
            }
        });

        resendOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResendOtp sendOtpAgain = new ResendOtp(context);
                sendOtpAgain.resendOtpGo(resendOtp());
            }
        });
    }

    long timeremain = 0;
    boolean stopCount = false;
    public void countDownCheck(){
        count = new CountDownTimer(50000,1000){

            @Override
            public void onTick(long l) {
                if(!stopCount)
                myText.setText("Remaining Second " + l / 1000);
                else {
                    myText.setText("Verifying...");
                }

                String code = preferences.getString("smsCode","noCode");
                if (!code.equals("noCode")){
                    editTextOpt.setText(code);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    if (!editTextOpt.getText().toString().equals("")) {
                        resending = false;
                        failToVerify = false;
                        onFinish();
                    }
                }
            }

            @Override
            public void onFinish() {
                if(resending) {
                    try_to_resend();
                    resending = false;
                }else if(failToVerify) {
                    myText.setText("Fail to verify");
                    timeFinished();
                }else{
                    myText.setText("Verifying...");
                    VerifyUser verifyUser = new VerifyUser(context);
                    verifyUser.verifyUser(readJsonMap(),sendOpt,editTextOpt,myText,context,resending);
                }
            }
        }.start();
    }

    private void try_to_resend(){
        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long l) {
                myText.setText("Resending code in " + l / 1000);
            }

            @Override
            public void onFinish() {
                ResendOtp sendOtpAgain = new ResendOtp(context);
                sendOtpAgain.resendOtpGo(resendOtp());
                countDownCheck();
                timeRenew();
            }
        }.start();
    }

    private void timeFinished(){
        sendOpt.setClickable(false);
        editTextOpt.setFocusable(false);
    }

    private void timeRenew(){
        sendOpt.setClickable(true);
        editTextOpt.setFocusable(true);
    }

    public Map<String, String> readJsonMap(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("SignUp", Context.MODE_PRIVATE);
        String shared = sharedPreferences.getString("data","{}");
        Map<String, String> signUpData = new HashMap<String, String>();
        try {
            JSONObject customerObject = new JSONObject(shared);
            signUpData.put("_id",customerObject.getString("_id"));
            signUpData.put("Authorization",customerObject.getString("token"));
            signUpData.put("customer_id",customerObject.getString("customer_id"));
            signUpData.put("first_name",customerObject.getString("first_name"));
            signUpData.put("last_name",customerObject.getString("last_name"));
            signUpData.put("phone_no",customerObject.getString("phone_no"));
            signUpData.put("email",customerObject.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return signUpData;
    }

    public Map<String, String> resendOtp(){
        Map<String, String> customer = new HashMap<>();
        customer.put("customer_id",readJsonMap().get("customer_id"));
        return customer;
    }
}
