package com.tanzania.comtech.msafiriapp.Auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Helpers.DirectUserByRole;
import com.tanzania.comtech.msafiriapp.Repository.LoginModel;
import com.tanzania.comtech.msafiriapp.R;

public class Activity_Login extends AppCompatActivity implements View.OnClickListener {

    EditText username, password;
    AppCompatEditText user;
    TextView hiddenSms, btnRegister;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_login);


        username = (EditText)findViewById(R.id.layout_login_edittext_enter_email);
        password = (EditText)findViewById(R.id.layout_login_edittext_enter_password);

        hiddenSms = (TextView)findViewById(R.id.layout_login_hidden_sms);

        btnLogin = (Button)findViewById(R.id.layout_login_button_login);
        btnRegister = (TextView) findViewById(R.id.layout_login_button_regiter);

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_login_button_login:
                    String textUsername = username.getText().toString();
                    String textPassword = password.getText().toString();
                ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);
                    LoginModel loginModel = new LoginModel(getApplicationContext());
                    loginModel.logInToMsafiriApi(textUsername,textPassword,hiddenSms,progressBar);
                    hiddenSms.setVisibility(View.GONE);
                    hiddenSms.setText(R.string.error_login_sms);
                break;
            case R.id.layout_login_button_regiter:
                    new DirectUserByRole(getApplicationContext(),"register");
                break;
        }
    }


}
