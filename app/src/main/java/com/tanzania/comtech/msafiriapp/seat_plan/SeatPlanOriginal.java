package com.tanzania.comtech.msafiriapp.seat_plan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.Payment.SeatInformation;
import com.tanzania.comtech.msafiriapp.R;
import com.tanzania.comtech.msafiriapp.Repository.HoldUnHoldSeat;

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
    boolean last_seat_field = true;
    String rowNames[];
    String[] seatNo;
    double priceToUse, totalPrice;
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
        busdataMap = new SharedPreferenceAppend(getApplicationContext()).readSharedPref(getString(R.string.shared_preference_bus_data_from_id));


        left_right_seat(String.valueOf(busdataMap.get(getString(R.string.shared_seat_type))));
        total_seats = (int) busdataMap.get(getString(R.string.bus_max_seat_no));
        last_seat_field = (boolean) busdataMap.get(getString(R.string.shared_bus_last_seat_filled));

        TextView bus_name = (TextView)findViewById(R.id.seat_plan_layout_bus_name);
        bus_name.setText((CharSequence) busdataMap.get(getString(R.string.shared_bus_name)));

        TextView price = (TextView) findViewById(R.id.seat_plan_layout_plate_no);

        Map<String, Object> priceMap = new SharedPreferenceAppend(getApplicationContext()).readSharedPref(getString(R.string.shared_fare));

        Log.e("priceMap","price "+priceMap);
        double busPrice = Double.parseDouble((String)priceMap.get(getString(R.string.shared_fare)));
        double busDiscount = Double.parseDouble((String)priceMap.get(getString(R.string.shared_discount)));

        if((busPrice) == (busPrice - busDiscount)){
            price.setText(String.valueOf(busPrice) + " Tzs");
        }else{
            price.setText(String.valueOf(busPrice - busDiscount) + " (--" + String.valueOf(busPrice) + "--) TZS");
        }

        priceToUse = busPrice - busDiscount;
        total_column = left_column + right_column + 1;
        space_between = left_column + 1;

        total_seats_rows = total_seats / (total_column - 1);
        remain_seat = total_seats % total_column;
    }

    public void left_right_seat(String seat_type){
        switch (seat_type) {
            case "2_X_3":
                left_column = 2;
                right_column = 3;
                break;
            case "2_X_2":
                left_column = 2;
                right_column = 2;
                break;
            case "1_X_2":
                left_column = 1;
                right_column = 2;
                break;
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

        seatAvailable = new ArrayList();
        mapHoldingValue = new SharedPreferenceAppend(getApplicationContext()).readSharedPref(getString(R.string.shared_preference_route));

        checkedMapExistence = new SharedPreferenceAppend(getApplicationContext()).readSharedPref("booked_seat");
        new SharedPreferenceAppend(getApplicationContext()).clearSharedPref(new String[]{"booked_seat"});

        rowNames = new String[]{"","A","B","C","D","E","F","G","H","K","L","M","N","O","P","Q","R","S","T","U","V","X","Y","Z"}; // rows seat name
        seatNo = new String[89]; // string for storing seats
        selected_seat_by_customers = new String[10];
        populate_bas_data();
        getChildLinearLayout();

        Button btn = (Button)findViewById(R.id.seat_plan_layout_confirm_seat);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on_click_next_button();
            }
        });
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
                imageView.setImageResource(R.mipmap.seat_empty);
                if (i > total_column) {
                    imageView.setVisibility(View.GONE);
                } else if (i == space_between) {
                    if(j == total_seats_rows && last_seat_field){
                        imageView.setVisibility(View.VISIBLE);
                    }else {
                        imageView.setVisibility(View.INVISIBLE);
                    }
                }else{
                    giveSeatId(j,k,imageView);
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

    private void giveSeatId(int j, int k, ImageView imageView){
        seatNo[setNumberIncrement] = "" + rowNames[j] + k;

        imageView.setId(setNumberIncrement);
        int isChecked = isChecked(seatNo[setNumberIncrement]);
        if(isChecked > 0){
            imageView.setImageResource(R.mipmap.seat_taken);
            if(isChecked == 1) {
                imageView.setEnabled(true);
                String array_searched = seatNo[setNumberIncrement];
                addSeatAvailable(false, array_searched, setNumberIncrement);
            } if(isChecked == 2 || isChecked == 3) imageView.setEnabled(false);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onClickSeatImage(view); }
        });
    }
    private void onClickSeatImage(View view) {
        String array_searched = seatNo[view.getId()];
        addSeatAvailable(true, array_searched, view.getId());
    }

    //Check if seat is booked
    private int isChecked(String seatName){
        if(checkedMapExistence.containsKey(seatName)) {
            String jsonData = String.valueOf(checkedMapExistence.get(seatName));
            boolean paid = false;
            boolean hold = false;
            String customer = "";
            SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preference_session), MODE_PRIVATE);
            final String customer_id = sharedPreferences.getString("customer_id", "");
            try {
                JSONObject returned = new JSONObject(jsonData);
                paid = returned.getBoolean("paid");
                hold = returned.getBoolean("on_hold");
                customer = returned.getString("customer");
            } catch (JSONException e) {
                e.printStackTrace();
            }
                if(customer.equals(customer_id) && hold){ return 1;}else if (paid){ return 2; }else if(hold){ return 3;}
        }
        return 0;
    }

    //Function followed whene next button clicked
    private void on_click_next_button() {
        if(!seatAvailable.isEmpty()){
            SharedPreferences seatPreference = getSharedPreferences("SEAT_BOOK",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = seatPreference.edit();

            editor.putString("seatSelected", String.valueOf(seatAvailable));
            editor.putString("totalPrice", String.valueOf(totalPrice));
            editor.apply();
            //Toast.makeText(getApplicationContext(),"Price " + totalPrice,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), SeatInformation.class));
        }else{
            Toast.makeText(getApplicationContext(),"You must choose atleast 1 seat",Toast.LENGTH_SHORT).show();
        }
    }


    ///selected seat by user
    int totalSeatSelected = 0;
    public void addSeatAvailable(boolean holdSeatYes, Object value, int ImageID){
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
            buttonChangeImage.setImageResource(R.mipmap.seat_empty);
        }else if(totalSeatSelected < 5){
            seatAvailable.add(value);
            totalSeatSelected++;
            //Change seat image and pop up seat no selected
            mapHoldingValue.put("seat_no",value);
            if (holdSeatYes)
            holdUnHoldSeat.holdingSeat(mapHoldingValue);
            buttonChangeImage = (ImageView)findViewById(ImageID);
            buttonChangeImage.setImageResource(R.mipmap.seat_taken);
        }else {
            Toast.makeText(getApplicationContext(),"The maximum number of seats that can be selected is 5",Toast.LENGTH_SHORT).show();
        }

        TextView textView = (TextView)findViewById(R.id.seat_plan_layout_view_selected_seat);
        totalPrice = priceToUse * totalSeatSelected;
        textView.setText("Seat(s) " + seatAvailable + "\n" + "Price " + String.valueOf(totalPrice) + "TZS");
    }
}
