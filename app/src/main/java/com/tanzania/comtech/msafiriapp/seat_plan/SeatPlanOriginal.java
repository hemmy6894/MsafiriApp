package com.tanzania.comtech.msafiriapp.seat_plan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.Payment.SeatInformation;
import com.tanzania.comtech.msafiriapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class SeatPlanOriginal extends AppCompatActivity {

    //Start of layout variables
    LinearLayout coveredLayout;
    private int left_column = 0;
    private int right_column = 0;
    int total_seats = 30;
    int total_column;
    int space_between;
    int total_seats_rows;
    int remain_seat;
    String rowNames[];
    String[] seatNo;
    int seat_id = 0;
    ///Layout variables ended heare
    private String seat_id_name;
    private int count_selected_seat = 0;
    ////Variable for storing selected seat by customers
    String[] selected_seat_by_customers;
    ImageView buttonChangeImage;

    SharedPreferences busData;

    Map<String, Object> busdataMap;
    JSONObject object;
    public void populate_bas_data(){
        coveredLayout = (LinearLayout)findViewById(R.id.seat_plan_layout_og);
        busData = getSharedPreferences(getString(R.string.shared_preference_bus_data_from_id), Context.MODE_PRIVATE);
        try {
            object = new JSONObject(busData.getString("bus","{}"));
            left_right_seat(object.getString(getString(R.string.shared_seat_type)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        busdataMap = new SharedPreferenceAppend(getApplicationContext()).readSharedPref(getString(R.string.shared_preference_bus_data_from_id));


        total_column = left_column + right_column + 1;
        space_between = left_column + 1;
        total_seats_rows = total_seats / (total_column - 1);
        remain_seat = total_seats % total_column;
    }

    public void left_right_seat(String seat_type){
        if(seat_type.equals("2_X_3")){
            left_column = 2;
            right_column = 3;
        }else if(seat_type.equals( "2_X_2")){
            left_column = 2;
            right_column = 2;
        }else if(seat_type.equals("1_X_2")){
            left_column = 1;
            right_column = 2;
        }
    }

    ArrayList seatAvailable;
    TextView textView;
    Map<String, Object> checkedMapExistence;
    Map<String, Object> mapHoldingValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_plan_original);
        checkedMapExistence = new SharedPreferenceAppend(getApplicationContext()).readSharedPref("booked_seat");
        Log.e("nyama za ulimi",checkedMapExistence.toString());
        rowNames = new String[]{"","A","B","C","D","E","F","G","H","K","L","M","N","O","P","Q","R","S","T","U","V","X","Y","Z"}; // rows seat name
        seatNo = new String[89]; // string for storing seats
        selected_seat_by_customers = new String[10];
        populate_bas_data();
        getChildLinearLayout();
    }

    private void getChildLinearLayout() {
        for (int i =  0; i <= total_seats_rows; i++){
            ViewGroup v = (ViewGroup) coveredLayout.getChildAt(i);
            LinearLayout layout = (LinearLayout)v;
            layout.setVisibility(View.VISIBLE);
            getChildImageView(layout,i);
        }
    }

    int setNumberIncrement = 0;
    private void getChildImageView(LinearLayout layout, int j) {
        int k = 1;
        for (int i = 1; i <= 6; i++){
            if(j != 0) {
                View view = (View) layout.getChildAt((i - 1));
                ImageView imageView = (ImageView) view;
                imageView.setImageResource(R.drawable.empty_seat);
                if (i > total_column) {
                    imageView.setVisibility(View.GONE);
                } else if (i == space_between) {
                    imageView.setVisibility(View.INVISIBLE);
                }else{
                    seatNo[setNumberIncrement] = "" + rowNames[j] + k;

                    if(isChecked(seatNo[setNumberIncrement])){
                        imageView.setImageResource(R.drawable.seat_taken);
                        imageView.setEnabled(false);
                    }
                    imageView.setId(setNumberIncrement);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onClickSeatImage(view);
                        }
                    });
                    k++;
                    setNumberIncrement++;
                }
            }else{
                View view = (View) layout.getChildAt((i - 1));
                ImageView imageView = (ImageView) view;
                if (i != total_column) {
                    imageView.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void onClickSeatImage(View view) {
        Toast.makeText(getApplicationContext(),seatNo[view.getId()] + "",Toast.LENGTH_SHORT).show();
    }

    //Check if seat is booked
    private boolean isChecked(String seatName){
        return checkedMapExistence.containsKey(seatName);
    }

    //Function followed whene next button clicked
    private void on_click_next_button() {
        if(!seatAvailable.isEmpty()){
            SharedPreferences seatPreference = getSharedPreferences("SEAT_BOOK",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = seatPreference.edit();

            editor.putString("seatSelected", String.valueOf(seatAvailable));
            editor.apply();
            startActivity(new Intent(getApplicationContext(), SeatInformation.class));
        }else{
            Toast.makeText(getApplicationContext(),"You must choose atleast 1 seat",Toast.LENGTH_SHORT).show();
        }
    }
}
