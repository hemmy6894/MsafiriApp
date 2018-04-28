package com.tanzania.comtech.msafiriapp.Auth;

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
        new Thread() {
            public void run() {
                try {
                    ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_bar);
                    progressBar.setVisibility(View.VISIBLE);
                    sleep(20000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finish();
                    new CheckIfIsLogin(getApplicationContext());
                }
            }
        }.start();
    }
}
