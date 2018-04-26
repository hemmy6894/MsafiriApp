package com.tanzania.comtech.msafiriapp.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.tanzania.comtech.msafiriapp.ChooseTransportType;
import com.tanzania.comtech.msafiriapp.R;

public class ThanksActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(.8 *width),(int)(.7*height));

        Button button = (Button)findViewById(R.id.thank_go_home);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String arrray[] = {getString(R.string.shared_preference_route),getString(R.string.shared_preference_route_info),getString(R.string.shared_preference_text_to_pay_for),getString(R.string.shared_preference_bus_data_from_id)};
        new SharedPreferenceAppend(getApplicationContext()).clearSharedPref(arrray);
        finish();
        startActivity(new Intent(getApplicationContext(), ChooseTransportType.class));
    }
}
