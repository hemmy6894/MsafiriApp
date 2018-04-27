package com.tanzania.comtech.msafiriapp.Auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Model.RegisterUser;
import com.tanzania.comtech.msafiriapp.R;

import java.util.HashMap;
import java.util.Map;

public class Register_Page extends AppCompatActivity implements View.OnClickListener {

    EditText editTextFirstName, editTextLastName, editTextEmail, editTextPhone, editTextAddress, editTextPassword, editTextCoPassword;
    String stringFirstName, stringLastName, stringTextEmail, stringTextPhone, stringTextAddress, stringTextPassword, stringCoPassword;
    TextView buttonLogin;
    Button buttonRegister;
    Map<String, String> sendTextToDb;
    private Button buttonNext,buttonPrev;

    private void startEditText(){
        editTextFirstName = (EditText)findViewById(R.id.register_first_name);
        editTextLastName = (EditText)findViewById(R.id.register_last_name);
        editTextEmail = (EditText)findViewById(R.id.register_email);
        editTextPhone = (EditText)findViewById(R.id.register_phone);
        editTextAddress = (EditText)findViewById(R.id.register_address);
        editTextPassword = (EditText)findViewById(R.id.register_password);
        editTextCoPassword = (EditText)findViewById(R.id.register_re_password);
    }

    private void getEditText(){
        stringFirstName = getTextE(editTextFirstName);
        stringLastName = getTextE(editTextLastName);
        stringTextEmail = getTextE(editTextEmail);
        stringTextPhone = getTextE(editTextPhone);
        stringTextAddress = getTextE(editTextAddress);
        stringTextPassword = getTextE(editTextPassword);
        stringCoPassword = getTextE(editTextCoPassword);
    }


    private Map<String, String> getTextFrom(){
        getEditText();
        sendTextToDb = new  HashMap<String, String>();
        sendTextToDb.put("first_name",stringFirstName);
        sendTextToDb.put("last_name",stringLastName);
        sendTextToDb.put("email",stringTextEmail);
        sendTextToDb.put("password",stringTextPassword);
        sendTextToDb.put("phone_no",stringTextPhone);
        sendTextToDb.put("address",stringTextAddress);
        sendTextToDb.put("gender","UNKNOWN");
        return sendTextToDb;
    }

    LinearLayout registerOne, registerTwo;
    private String getTextE(EditText editText){
        return editText.getText().toString();
    }
    private void startButton(){
        registerOne = (LinearLayout)findViewById(R.id.register_one);
        registerTwo = (LinearLayout)findViewById(R.id.register_two);

        registerOne.setVisibility(View.VISIBLE);
        buttonLogin = (TextView)findViewById(R.id.register_button_login);
        buttonLogin.setOnClickListener(this);

        buttonNext = (Button) findViewById(R.id.register_page_one_next);
        buttonNext.setOnClickListener(this);

        buttonRegister = (Button)findViewById(R.id.register_button_register);
        buttonRegister.setOnClickListener(this);

        buttonPrev = (Button) findViewById(R.id.register_page_two_prev);
        buttonPrev.setOnClickListener(this);
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
            case R.id.register_page_one_next:
                registerOne.setVisibility(View.GONE);
                registerTwo.setVisibility(View.VISIBLE);
                break;
            case R.id.register_page_two_prev:
                registerOne.setVisibility(View.VISIBLE);
                registerTwo.setVisibility(View.GONE);
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
