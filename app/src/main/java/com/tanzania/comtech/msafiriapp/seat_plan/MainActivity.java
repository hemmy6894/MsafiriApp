package com.tanzania.comtech.msafiriapp.seat_plan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.Repository.CheckBookedSeat;
import com.tanzania.comtech.msafiriapp.Repository.HoldUnHoldSeat;
import com.tanzania.comtech.msafiriapp.Payment.SeatInformation;
import com.tanzania.comtech.msafiriapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Start of layout variables
    LinearLayout Layout;
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

    JSONObject object;
    public void populate_bas_data(){
        busData = getSharedPreferences(getString(R.string.shared_preference_bus_data_from_id),Context.MODE_PRIVATE);
        try {
            object = new JSONObject(busData.getString("bus","{}"));
            left_right_seat(object.getString(getString(R.string.shared_seat_type)));
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        setContentView(R.layout.seat_plan_layout);
        seatAvailable = new ArrayList();


        mapHoldingValue = new SharedPreferenceAppend(getApplicationContext()).readSharedPref(getString(R.string.shared_preference_route));
        new CheckBookedSeat(getApplicationContext()).checkBookedSeat();
        checkedMapExistence = new SharedPreferenceAppend(getApplicationContext()).readSharedPref("booked_seat");

        populate_bas_data();
        rowNames = new String[]{"","A","B","C","D","E","F","G","H","K","L","M","N","O","P"}; // rows seat name
        seatNo = new String[89]; // string for storing seats
        selected_seat_by_customers = new String[10];

        Layout  = (LinearLayout)findViewById(R.id.seat_plan_laout);

        LinearLayout.LayoutParams myLayouts = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT,
          LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout.LayoutParams myLayouts2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setId(R.id.first_id);
        //layout.setBackgroundColor(Color.rgb(255,255,245));
        //layout.setBackgroundResource(R.drawable.background_blue_one);
        layout.setOrientation(LinearLayout.VERTICAL);



        //Creating button for driver seat
        ImageView btn1 = new ImageView(MainActivity.this);
        btn1.setId(seat_id);
        btn1.setImageResource(R.drawable.seat_taken);
        LinearLayout.LayoutParams buttonParam = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        buttonParam.gravity = Gravity.RIGHT;

        LinearLayout layout2 = new LinearLayout(MainActivity.this);
        layout2.setId(R.id.second_id);
        layout2.setBackgroundColor(Color.rgb(255, 255, 245));
        layout2.setLayoutParams(buttonParam);
        layout2.setOrientation(LinearLayout.HORIZONTAL);

        layout2.addView(btn1);
        layout.addView(layout2);
        //End of driver seat button

        creating_customer_seat(layout,myLayouts,myLayouts2);



        //Adding Coded layout to real UI xml UI
        Layout.addView(layout,myLayouts);
        ArrayArr = new String[67];
    }


    String[] ArrayArr ;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case 67:

                break;
            default:
            String array_searched = seatNo[view.getId()];
            Toast.makeText(getApplicationContext(), "Seat No : " + array_searched, Toast.LENGTH_SHORT).show();
            addSeatAvailable(array_searched, array_searched, view.getId());
            Log.e("Seat Selected ", String.valueOf(seatAvailable));
            break;
        }
    }

    int totalSeatSelected = 0;
    public void addSeatAvailable(String key, Object value, int ImageID){
        int d = seatAvailable.indexOf(value);

        mapHoldingValue.put(getString(R.string.shared_customer_id),new SharedPreferenceAppend(getApplicationContext()).readSharedPref(getString(R.string.shared_preference_booking_info)).get(getString(R.string.shared_customer_id)));
        HoldUnHoldSeat holdUnHoldSeat = new HoldUnHoldSeat(getApplicationContext());
        if(d >= 0){
            seatAvailable.remove(d);
            totalSeatSelected--;
            //Change seat image and pop up seat no selected
            mapHoldingValue.put("seat_no",value);
            holdUnHoldSeat.unHoldingSeat(mapHoldingValue);
            buttonChangeImage = (ImageView)findViewById(ImageID);
            buttonChangeImage.setImageResource(R.drawable.empty_seat);
        }else if(totalSeatSelected < 5){
            seatAvailable.add(value);
            totalSeatSelected++;
            //Change seat image and pop up seat no selected
            mapHoldingValue.put("seat_no",value);
            holdUnHoldSeat.holdingSeat(mapHoldingValue);
            buttonChangeImage = (ImageView)findViewById(ImageID);
            buttonChangeImage.setImageResource(R.drawable.seat_taken);
        }else {
            Toast.makeText(getApplicationContext(),"The maximum number of seats that can be selected is 5",Toast.LENGTH_SHORT).show();
        }
    }
    ///////Creating customers seat layout

    public void creating_customer_seat(final LinearLayout layout,final LinearLayout.LayoutParams myLayouts,final LinearLayout.LayoutParams myLayouts2){
        for(int j = 1; j <= total_seats_rows; j++) {
            ///Creating layout for rows
            LinearLayout layout3 = new LinearLayout(MainActivity.this);
            layout3.setBackgroundColor(Color.rgb(255, 255, 245));
            layout3.setOrientation(LinearLayout.HORIZONTAL);
            //ended here

            ///Continue seat number count
            int seat_numbering = 1;
            boolean continue_count_seat_no = true;
            //end here

            for (int i = 1; i <= total_column; i++) {

                ImageView btn = new ImageView(MainActivity.this); // iniitiate button for seat
                btn.setMinimumHeight(80);
                btn.setMinimumWidth(80);
                if (space_between != i || (j==total_seats_rows && remain_seat == 1)) { //creating space beteen two columns
                    seat_id_name = "" + rowNames[j]+""+seat_numbering; // generating seat name in latter and no


                    seat_id = seat_id + 1; // increment seat value
                    seatNo[seat_id] = seat_id_name;
                    btn.setId(seat_id);
                    if(!isChecked(seat_id_name)) {
                        btn.setEnabled(isChecked(seat_id_name));
                        btn.setImageResource(R.drawable.seat_taken);
                    }else{
                        btn.setImageResource(R.drawable.empty_seat);
                    }
                } else {
                    btn.setMinimumWidth(80);
                    btn.setVisibility(View.INVISIBLE);
                }

                //check if counting seat no or not
                if((seat_numbering == space_between) && continue_count_seat_no){
                    continue_count_seat_no = false;
                }else{
                    seat_numbering++;
                }
                //end checking if counting seat number or not

                //setting button parameters
                btn.setOnClickListener(this);

                //setting button to rows
                layout3.addView(btn, myLayouts2);
            }

            //setting rows to layout
            layout.addView(layout3, myLayouts);
        }
        layout.addView(add_view_for_next_button_inv(),myLayouts);
        layout.addView(add_view_for_next_button(),myLayouts);
    }

    private boolean isChecked(String seatName){
        return !checkedMapExistence.containsKey(seatName);
    }
    ///Creating next button
    public LinearLayout add_view_for_next_button(){
        LinearLayout nextLayout = new LinearLayout(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT,
              LinearLayout.LayoutParams.MATCH_PARENT
            );
            params.gravity = Gravity.RIGHT;

            nextLayout.setLayoutParams(params);

            Button myNextButton = new Button(MainActivity.this);
            myNextButton.setText("Book Now");
            myNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    on_click_next_button();
                }
            });

            nextLayout.addView(myNextButton);
        return nextLayout;
    }

    public LinearLayout add_view_for_next_button_inv(){
        LinearLayout nextLayout = new LinearLayout(MainActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        params.gravity = Gravity.RIGHT;

        nextLayout.setLayoutParams(params);

        Button myNextButton = new Button(MainActivity.this);
        myNextButton.setText("Book Now");
        myNextButton.setVisibility(View.INVISIBLE);
        myNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        nextLayout.addView(myNextButton);
        return nextLayout;
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
