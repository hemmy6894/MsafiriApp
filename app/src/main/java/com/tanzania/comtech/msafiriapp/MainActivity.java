package com.tanzania.comtech.msafiriapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Repository.FetchRouteRepository;
import com.tanzania.comtech.msafiriapp.Time.TimeVariables;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextMessage;
    Button goNext;
    ImageView swapper;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    SharedPreferences routeInfo;
    SharedPreferences.Editor editRoute;
    AutoCompleteTextView fromEditText, toEditText;

    LinearLayout datePickerLayout ;

    TextView datePickerDay, datePickerMonth,datePickerDayName,datePickerYear;

    public int left_seat, right_seat;

    String sendTextFrom, sendTextTo, sendPickedDate;
    public void create_datePickers(){
        datePickerDay = (TextView)findViewById(R.id.select_source_destination_date_picker_day);
        datePickerMonth = (TextView)findViewById(R.id.select_source_destination_date_picker_month);
        datePickerDayName = (TextView)findViewById(R.id.select_source_destination_date_picker_day_name);
        datePickerYear = (TextView)findViewById(R.id.select_source_destination_date_picker_year);
        myCalendar = Calendar.getInstance();
    }

    public void  initiate_datePickers(){
        int day_of_month = myCalendar.get(Calendar.DAY_OF_MONTH);
        int month = myCalendar.get(Calendar.MONTH);
        int year = myCalendar.get(Calendar.YEAR);
        int day_of_week = myCalendar.get(Calendar.DAY_OF_WEEK);

        datePickerDay.setText(String.format("%s", day_of_month));
        datePickerDayName.setText(String.format("%s", TimeVariables.weeksNames[day_of_week]));
        datePickerMonth.setText(String.format("%s", TimeVariables.monthNames[month]));
        datePickerYear.setText(String.format("%s", year));

        editRoute = routeInfo.edit();
        editRoute.putInt("day_of_month",day_of_month);
        editRoute.putInt("month",month);
        editRoute.putInt("day_of_week",day_of_week);
        editRoute.putInt("year",year);

        sendPickedDate = "" + append_zero(day_of_month) + "-" + append_zero(month + 1) +"-"+ append_zero(year);
    }

    public String append_zero(int num){
        if(num < 10){
            return "0" + num;
        }
        return "" + num;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_bus_ticket);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_ship_ticket);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_train_ticket);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.select_source_destination);

        //mTextMessage = (TextView) findViewById(R.id.textView2);
      //  BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //  navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        swapper = (ImageView)findViewById(R.id.select_source_destination_swapper);
        fromEditText = (AutoCompleteTextView) findViewById(R.id.select_source_destination_source);
        toEditText = (AutoCompleteTextView) findViewById(R.id.select_source_destination_destination);

        String[] countries = getResources().getStringArray(R.array.regions_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        fromEditText.setAdapter(adapter);
        toEditText.setAdapter(adapter);

        swapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSwapper();
            }
        });

        routeInfo = getSharedPreferences("routeInfo",Context.MODE_PRIVATE);
        create_datePickers();
        initiate_datePickers();
        populate_layout_date();

        goNext = (Button)findViewById(R.id.select_source_destination_next_button);
        goNext.setOnClickListener(this);
    }


    public void populate_layout_date(){
        datePickerLayout = (LinearLayout) findViewById(R.id.select_source_destination_date_picker);
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                initiate_datePickers();
            }

        };

        datePickerLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void setSwapper(){
        String swapper = "";
        String fromText = fromEditText.getText().toString();
        String toText = toEditText.getText().toString();

        swapper = fromText;
        fromText = toText;
        toText = swapper;

        fromEditText.setText(fromText);
        toEditText.setText(toText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_source_destination_next_button:
                sendTextFrom = fromEditText.getText().toString();
                sendTextTo = toEditText.getText().toString();
                editRoute.putString("from",sendTextFrom);
                editRoute.putString("to",sendTextTo);
                editRoute.apply();

                ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);
                FetchRouteRepository fetchRouteRepository = new FetchRouteRepository(getApplicationContext());
                fetchRouteRepository.requestJson(sendTextFrom,sendTextTo,sendPickedDate,progressBar);
                break;
        }
    }
}
