package com.tanzania.comtech.msafiriapp.Auth;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.tanzania.comtech.msafiriapp.Helpers.CheckIfIsLogin;
import com.tanzania.comtech.msafiriapp.R;

public class StartApplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_application);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new CountDownTimer(3000, 10)  {

            public void onTick (long millisUntilFinished) {

            }

            public void onFinish() {
                new CheckIfIsLogin(getApplicationContext());
            }
        }.start();


    }

    @Override
    protected void onResume() {
        super.onResume();
        System.exit(0);
    }
}
