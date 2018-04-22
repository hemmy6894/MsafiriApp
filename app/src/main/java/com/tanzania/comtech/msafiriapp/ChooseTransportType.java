package com.tanzania.comtech.msafiriapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.Model.FetchRouteModel;
import com.tanzania.comtech.msafiriapp.Time.TimeVariables;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChooseTransportType extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

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
        editRoute.putInt(getString(R.string.day_of_month),day_of_month);
        editRoute.putInt(getString(R.string.month),month);
        editRoute.putInt(getString(R.string.day_of_week),day_of_week);
        editRoute.putInt(getString(R.string.year),year);

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
        setContentView(R.layout.choose_transport_type_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mTextMessage = (TextView) findViewById(R.id.textView2);
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

        routeInfo = getSharedPreferences(getString(R.string.shared_preference_route_info), Context.MODE_PRIVATE);
        create_datePickers();
        initiate_datePickers();
        populate_layout_date();

        goNext = (Button)findViewById(R.id.select_source_destination_next_button);
        goNext.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_transport_type, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                new DatePickerDialog(ChooseTransportType.this, date, myCalendar
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
                editRoute.putString(getString(R.string.from),sendTextFrom);
                editRoute.putString(getString(R.string.to),sendTextTo);
                editRoute.apply();

                ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.VISIBLE);
                FetchRouteModel fetchRouteModel = new FetchRouteModel(getApplicationContext());
                fetchRouteModel.requestJson(sendTextFrom,sendTextTo,sendPickedDate,progressBar);
                break;
        }
    }
}
