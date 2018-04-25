package com.tanzania.comtech.msafiriapp.Helpers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.tanzania.comtech.msafiriapp.Payment.PaymentNormal;
import com.tanzania.comtech.msafiriapp.Payment.PaymentVisa;
import com.tanzania.comtech.msafiriapp.R;

public class PopActivity extends Activity implements View.OnClickListener {

    ImageView code_mpesa, code_tigo, code_airtel, api_tigo, api_nmb, api_visa, api_master, api_master_two;

    ImageView imageViews[] = {code_mpesa, code_tigo, code_airtel, api_tigo, api_nmb, api_visa, api_master, api_master_two};
    private void startButtons(){
        code_mpesa = (ImageView) findViewById(R.id.pay_by_vodacom);
        code_tigo = (ImageView) findViewById(R.id.pay_by_tigo_pesa);
        code_airtel = (ImageView) findViewById(R.id.pay_by_airtel);

        api_tigo = (ImageView) findViewById(R.id.pay_by_api_tigo_pesa);
        api_nmb = (ImageView) findViewById(R.id.pay_by_api_nmp);

        api_visa = (ImageView) findViewById(R.id.pay_by_visa);
        api_master = (ImageView) findViewById(R.id.pay_by_master);
        api_master_two = (ImageView) findViewById(R.id.pay_by_master_two);

        code_mpesa.setOnClickListener(this);
        code_tigo.setOnClickListener(this);
        code_airtel.setOnClickListener(this);

        api_tigo.setOnClickListener(this);
        api_nmb.setOnClickListener(this);

        api_visa.setOnClickListener(this);
        api_master.setOnClickListener(this);
        api_master_two.setOnClickListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_payment);
        startButtons();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(.8 *width),(int)(.7*height));
    }

    @Override
    public void onClick(View view) {
        finish();
        switch (view.getId()){
            case R.id.pay_by_vodacom:
            case R.id.pay_by_tigo_pesa:
            case R.id.pay_by_airtel:
                startActivity(new Intent(getApplicationContext(),PaymentNormal.class));
                break;
            case R.id.pay_by_api_tigo_pesa:
            case R.id.pay_by_api_nmp:
                Toast.makeText(getApplicationContext(), "Under construction", Toast.LENGTH_SHORT).show();
                break;
            case R.id.pay_by_visa:
            case R.id.pay_by_master:
            case R.id.pay_by_master_two:
                startActivity(new Intent(getApplicationContext(), PaymentVisa.class));
                break;
            default:
                Toast.makeText(this, "fail to discover btn", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
