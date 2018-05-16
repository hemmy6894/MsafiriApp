package com.tanzania.comtech.msafiriapp.Helpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tanzania.comtech.msafiriapp.ChooseTransportType;
import com.tanzania.comtech.msafiriapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class ThanksActivity extends Activity implements View.OnClickListener {

    protected WebView mWebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thanks);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.downloading_ticket);
        progressBar.setProgress(0);
        progressBar.setVisibility(View.INVISIBLE);

        final Button download = (Button) findViewById(R.id.download_ticket);
        final LinearLayout e_ticket = (LinearLayout) findViewById(R.id.e_ticket);
        final ImageView pdf = (ImageView) findViewById(R.id.pdf_downloaded);


        e_ticket.setTranslationY(-1000f);
        e_ticket.setVisibility(View.GONE);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
                new CountDownTimer(3000, 30) {

                    public void onTick(long millisUntilFinished) {

                        progressBar.incrementProgressBy(8);

                        if (millisUntilFinished == 1000) {

                            download.animate().alpha(0).setDuration(700);

                        }
                    }

                    public void onFinish() {

                        progressBar.setVisibility(View.INVISIBLE);
                        download.setVisibility(View.GONE);
                        e_ticket.setVisibility(View.VISIBLE);
                        e_ticket.animate().translationYBy(1000f).setDuration(300);

                    }
                }.start();
            }
        });

        String passengers = new SharedPreferenceAppend(getApplicationContext()).readSharedPrefNormal(getString(R.string.shared_preference_text_to_pay_for));
        Log.d("Thanks Activity", passengers.toString());
        try {
            JSONObject jsonObject = new JSONObject(passengers);
            String customer_id = jsonObject.getString(getString(R.string.shared_customer_id));
            String boarding_point = jsonObject.getString(getString(R.string.json_boarding_point));
            String dropping_point = jsonObject.getString(getString(R.string.json_dropping_point));

            JSONArray ticket_seats = jsonObject.getJSONArray("passengers");

            TextView textView_customer_id = (TextView) findViewById(R.id.ticket_customer_id);
            TextView textView_boarding_point = (TextView) findViewById(R.id.ticket_boarding_point);
            TextView textView_dropping_point = (TextView) findViewById(R.id.ticket_dropping_point);

            textView_customer_id.setText(customer_id);
            textView_boarding_point.setText(boarding_point);
            textView_dropping_point.setText(dropping_point);

            for (int i=0;i<ticket_seats.length();i++){

                JSONObject ticket = ticket_seats.getJSONObject(i);

                String json_name = ticket.getString("passenger_name");
                String json_phone = ticket.getString("passenger_phone");
                String json_seat = ticket.getString("seat_no");

                TextView name = new TextView(this);
                name.setTextColor(Color.parseColor("#000000"));
                name.setText(json_name);
                TextView phone = new TextView(this);
                phone.setTextColor(Color.parseColor("#000000"));
                phone.setText(json_phone);
                TextView seat = new TextView(this);
                seat.setTextColor(Color.parseColor("#000000"));
                seat.setText(json_seat);

                LinearLayout names = (LinearLayout) findViewById(R.id.ticket_name);
                LinearLayout numbers = (LinearLayout) findViewById(R.id.ticket_number);
                LinearLayout seats = (LinearLayout) findViewById(R.id.ticket_seat);

                names.addView(name);
                numbers.addView(phone);
                seats.addView(seat);

            }

            Log.d("Thanks Activity", ticket_seats.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        String array[] = {getString(R.string.shared_preference_route),getString(R.string.shared_preference_route_info),getString(R.string.shared_preference_text_to_pay_for),getString(R.string.shared_preference_bus_data_from_id)};
        new SharedPreferenceAppend(getApplicationContext()).clearSharedPref(array);
        finish();
        startActivity(new Intent(getApplicationContext(), ChooseTransportType.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}
