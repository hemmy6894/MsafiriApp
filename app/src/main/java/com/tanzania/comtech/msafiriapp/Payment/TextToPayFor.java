package com.tanzania.comtech.msafiriapp.Payment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TextToPayFor {
    Context context;
    Activity activity;
    private TextView fullname, seat, price, phone;
    public TextToPayFor(Context context,Activity activity) {
        this.context = context;
        this.activity = activity;
        textToPayFor();
    }

    private void textToPayFor() {
        String getRequiredText = new SharedPreferenceAppend(context).readSharedPrefNormal(context.getString(R.string.shared_preference_text_to_pay_for));
        fullname  = (TextView)activity.findViewById(R.id.pay_for_full_name);
        seat = (TextView)activity.findViewById(R.id.pay_for_total_seat);
        price = (TextView)activity.findViewById(R.id.pay_for_total_amount);
        phone = (TextView)activity.findViewById(R.id.pay_for_phone_number);

        try {
            JSONObject object = new JSONObject(getRequiredText);
            JSONArray array = object.getJSONArray("passengers");
            JSONObject primary_passenger = array.getJSONObject(0);

            fullname.setText(primary_passenger.getString("passenger_name"));
            phone.setText(primary_passenger.getString("passenger_phone"));
                StringBuilder seatsBuilder = new StringBuilder(array.length() + " Seat(s) ( ");
                for (int i = 0; i < array.length(); i++){
                         primary_passenger = array.getJSONObject(i);
                         seatsBuilder.append(primary_passenger.getString("seat_no")).append(" ");
                    }
                    seatsBuilder.append(" )");
                String seats = seatsBuilder.toString();
            seat.setText(seats);
            String toPay = String.valueOf(30000 * array.length());
            price.setText(toPay);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
