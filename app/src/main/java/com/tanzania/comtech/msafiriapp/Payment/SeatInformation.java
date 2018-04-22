package com.tanzania.comtech.msafiriapp.Payment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class SeatInformation extends AppCompatActivity implements View.OnClickListener {

    public int total_seat;
    LinearLayout seat_one, seat_two, seat_three, seat_four, seat_five;
    EditText p_one, p_two, p_three, p_four, p_five;
    TextView seat_name1, seat_name2, seat_name3, seat_name4, seat_name5;

    LinearLayout seat_layout[] = {seat_one,seat_two,seat_three,seat_four,seat_five};
    EditText p_editText[] = {p_one,p_two,p_three,p_four,p_five};
    TextView s_viewText[] = {seat_name1, seat_name2, seat_name3, seat_name4, seat_name5};

    AutoCompleteTextView boarding, dropping;
    EditText phoneNumber;

    int seats[] = {R.id.seat_1,R.id.seat_2,R.id.seat_3,R.id.seat_4,R.id.seat_5};
    int namesId[] = {R.id.seat_information_passenger_name1,R.id.seat_information_passenger_name2,R.id.seat_information_passenger_name3,R.id.seat_information_passenger_name4,R.id.seat_information_passenger_name1};
    int namesSeat[] = {R.id.seat_information_seat1,R.id.seat_information_seat2,R.id.seat_information_seat3,R.id.seat_information_seat4,R.id.seat_information_seat5};

    public void show_seats(){
        SharedPreferences sharedPreferences = getSharedPreferences("SEAT_BOOK", Context.MODE_PRIVATE);
        String string = sharedPreferences.getString("seatSelected","{}");

        ArrayList list = new ArrayList();
        try {
            JSONArray array = new JSONArray(string);
            total_seat = array.length();
            for (int i = 0; i < total_seat; i++){
                list.add(i,array.getString(i));
            }
            Log.e("ArrayReturn : ", String.valueOf(list));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        boarding = (AutoCompleteTextView)findViewById(R.id.seat_info_boarding_point);
        dropping = (AutoCompleteTextView)findViewById(R.id.seat_info_dropping_point);

        phoneNumber = (EditText)findViewById(R.id.seat_info_phone_no);
        phoneNumber.setText(new SharedPreferenceAppend(getApplicationContext()).readSharedPref(getString(R.string.shared_preference_session)).get(getString(R.string.shared_phone_number)));
        for (int i = 0; i <total_seat;i++){
            seat_layout[i] = (LinearLayout)findViewById(seats[i]);
            seat_layout[i].setVisibility(View.VISIBLE);
            p_editText[i] = (EditText)findViewById(namesId[i]);
            s_viewText[i] = (TextView) findViewById(namesSeat[i]);
            s_viewText[i].setText("Seat : " + list.get(i));
        }
    }

    Button payBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_seat_information);
        show_seats();

        payBy = (Button)findViewById(R.id.set_seat_info_button);
        payBy.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.set_seat_info_button:

                JSONObject object = new JSONObject();
                try {
                    Map<String, String> map = new SharedPreferenceAppend(getApplicationContext()).readSharedPref(getString(R.string.shared_preference_booking_info));
                    object.put(getString(R.string.shared_customer_id),map.get(getString(R.string.shared_customer_id)));
                    object.put(getString(R.string.json_boarding_point),boarding.getText().toString());
                    object.put(getString(R.string.json_dropping_point),dropping.getText().toString());

                    JSONArray passengers = new JSONArray();
                    for (int i = 0; i < total_seat; i++){
                        JSONObject p = new JSONObject();
                        p.put("seat_no",s_viewText[i].getText().toString());
                        p.put("passenger_gender","UNKNOWN");
                        p.put("passenger_phone",phoneNumber.getText().toString());
                        p.put("passenger_name",p_editText[i].getText().toString());
                        passengers.put(p);
                    }

                    object.put("passengers",passengers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("object","emm" + object.toString());
                break;
        }
    }
}
