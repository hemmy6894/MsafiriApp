package com.tanzania.comtech.msafiriapp.Auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tanzania.comtech.msafiriapp.Model.RegisterUser;
import com.tanzania.comtech.msafiriapp.R;

import java.util.HashMap;
import java.util.Map;

public class Register_Page extends AppCompatActivity implements View.OnClickListener {

    EditText editTextFirstName, editTextLastName, editTextEmail, editTextPhone, editTextAddress, editTextPassword, editTextCoPassword;
    Button buttonLogin, buttonRegister;
    Map<String, String> sendTextToDb;
    private void startEditText(){
        editTextFirstName = (EditText)findViewById(R.id.register_first_name);
        editTextLastName = (EditText)findViewById(R.id.register_last_name);
        editTextEmail = (EditText)findViewById(R.id.register_email);
        editTextPhone = (EditText)findViewById(R.id.register_phone);
        editTextAddress = (EditText)findViewById(R.id.register_address);
        editTextPassword = (EditText)findViewById(R.id.register_password);
        editTextCoPassword = (EditText)findViewById(R.id.register_re_password);
    }

    private Map<String, String> getTextFrom(){
        sendTextToDb = new  HashMap<String, String>();
        sendTextToDb.put("first_name",getTextE(editTextFirstName));
        sendTextToDb.put("last_name",getTextE(editTextLastName));
        sendTextToDb.put("email",getTextE(editTextEmail));
        sendTextToDb.put("password",getTextE(editTextPassword));
        sendTextToDb.put("phone_no",getTextE(editTextPhone));
        sendTextToDb.put("address",getTextE(editTextAddress));
        sendTextToDb.put("gender","UNKNOWN");
        return sendTextToDb;
    }

    private String getTextE(EditText editText){
        return editText.getText().toString();
    }
    private void startButton(){
        buttonLogin = (Button)findViewById(R.id.register_button_login);
        buttonLogin.setOnClickListener(this);

        buttonRegister = (Button)findViewById(R.id.register_button_register);
        buttonRegister.setOnClickListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register__page_layout);

        startEditText();
        startButton();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_button_login:
                    doLoginToTheSystemNow();
                break;
            case R.id.register_button_register:
                    doRegisterToSystemNow();
                break;
        }
    }

    private void doRegisterToSystemNow() {
        RegisterUser registerUser = new RegisterUser(getApplicationContext());
        registerUser.registerUserInSystem(getTextFrom());
    }

    private void doLoginToTheSystemNow() {
        finish();
        startActivity(new Intent(getApplicationContext(),Activity_Login.class));
    }
}
