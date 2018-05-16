package com.tanzania.comtech.msafiriapp.Auth;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Helpers.CheckIfIsLogin;
import com.tanzania.comtech.msafiriapp.R;

public class StartApplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_application);
        final TextView one = (TextView) findViewById(R.id.welcome_txt);
        final TextView two = (TextView) findViewById(R.id.msafiri_txt);
        one.setTranslationX(-1000f);
        two.setTranslationX(1000f);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new CountDownTimer(3000, 10)  {

            public void onTick (long millisUntilFinished) {

                if (millisUntilFinished == 1000) {

                }
            }

            public void onFinish() {
                one.animate().translationXBy(1000f).setDuration(1000);
                two.animate().translationXBy(-1000f).setDuration(1000);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new CheckIfIsLogin(getApplicationContext());
                    }
                }, 1000);
            }
        }.start();


    }

    @Override
    protected void onResume() {
        super.onResume();

        final TextView one = (TextView) findViewById(R.id.welcome_txt);
        final TextView two = (TextView) findViewById(R.id.msafiri_txt);
        one.setTranslationX(-1000f);
        two.setTranslationX(1000f);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        new CountDownTimer(3000, 10)  {

            public void onTick (long millisUntilFinished) {

                if (millisUntilFinished == 1000) {

                }
            }

            public void onFinish() {
                one.animate().translationXBy(1000f).setDuration(1000);
                two.animate().translationXBy(-1000f).setDuration(1000);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new CheckIfIsLogin(getApplicationContext());
                    }
                }, 1000);
            }
        }.start();

    }
}
