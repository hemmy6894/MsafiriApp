package com.tanzania.comtech.msafiriapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.tanzania.comtech.msafiriapp.R;

public class CheckIfIsLogin {
    private Context context;

    public CheckIfIsLogin(Context context) {
        this.context = context;
        checkIfIsLogin();
    }

    private void checkIfIsLogin(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preference_session),Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(context.getString(R.string.shared_first_name),"empty").equals("empty")){
            new DirectUserByRole(context,"select_source");
        }
    }
}
