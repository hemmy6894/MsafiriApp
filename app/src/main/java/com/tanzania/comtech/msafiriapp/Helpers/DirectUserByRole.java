package com.tanzania.comtech.msafiriapp.Helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tanzania.comtech.msafiriapp.Auth.Activity_Login;
import com.tanzania.comtech.msafiriapp.Auth.Register_Page;
import com.tanzania.comtech.msafiriapp.ChooseTransportType;
import com.tanzania.comtech.msafiriapp.MainActivity;

/**
 * Created by programing on 4/18/2018.
 */

public class DirectUserByRole {
    private Context context;

    public DirectUserByRole(Context context, String role) {
        this.context = context;
        directUserByRole(role);
    }

    private void directUserByRole(String role) {
        Intent intent;
        switch (role) {
            case "USER":
                intent = new Intent(context, ChooseTransportType.class);
                openNewActivity(intent);
                break;
            case "register":
                intent = new Intent(context, Register_Page.class);
                openNewActivity(intent);
                break;
            case "login":
                intent = new Intent(context, Activity_Login.class);
                openNewActivity(intent);
                break;
            case "select_source":
                intent = new Intent(context, ChooseTransportType.class);
                openNewActivity(intent);
                break;
            default:
                Toast.makeText(context, "Can't Login try again", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void openNewActivity(Intent intent){
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }
}
