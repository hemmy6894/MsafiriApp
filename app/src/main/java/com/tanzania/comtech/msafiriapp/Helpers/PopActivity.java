package com.tanzania.comtech.msafiriapp.Helpers;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.tanzania.comtech.msafiriapp.R;

public class PopActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_payment);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(.8 *width),(int)(.7*height));
    }

}
