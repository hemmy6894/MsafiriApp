package com.tanzania.comtech.msafiriapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.tanzania.comtech.msafiriapp.Repository.FetchRouteRepository;
import com.tanzania.comtech.msafiriapp.Time.TimeVariables;

import java.util.Calendar;

public class ChooseTransportType extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView mTextMessage;
    Button goNext;
    CardView swapper;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    SharedPreferences routeInfo;
    SharedPreferences.Editor editRoute;
    AutoCompleteTextView fromEditText, toEditText;

    LinearLayout datePickerLayout ;

    TextView datePickerDay, datePickerMonth,datePickerDayName,datePickerYear;

    public int left_seat, right_seat;

    Animation fade_in, fade_out;
    ViewFlipper viewFlipper;

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

/*
        String[] testData = new String[]{"firstFiled","secondField","thirdString"};
        MsafiriDatabase m = new MsafiriDatabase(getApplicationContext());
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("first_name","Hemedi");
            jsonObject.put("lastname","Manyi");
            jsonArray.put(jsonObject);
            jsonObject = new JSONObject();
            jsonObject.put("first_name","Hamisa");
            jsonObject.put("lastname","Hey");
            jsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] headerssss = new String[]{"first_name","last_name"};
        String hemedi = m.valuesReader(jsonArray,headerssss);
        Log.e("RETURNED_VALUE", "Test " + hemedi);
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //mTextMessage = (TextView) findViewById(R.id.textView2);
        //  BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //  navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        swapper = (CardView) findViewById(R.id.select_source_destination_swapper);
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

        viewFlipper = findViewById(R.id.viewFlipper);
        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        viewFlipper.setInAnimation(fade_in);
        viewFlipper.setOutAnimation(fade_out);

        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.startFlipping();

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            ///Double click back button to exit from {R.string.app_name}
            /// 10/08/2018
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Click again to Exist from " + R.string.app_name, Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

            //End  here
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
            Toast.makeText(getApplicationContext(),"Settings under construction",Toast.LENGTH_LONG).show();
            return true;
        }
        if(id == R.id.action_logout){
            Toast.makeText(getApplicationContext(),"Log out under construction",Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent drawable = new Intent(getBaseContext(),DrawerHistory.class);
        if (id == R.id.nav_ticket) {
            drawable.putExtra("EXTRA_TYPE", "ticket");
            startActivity(drawable);
        } else if (id == R.id.nav_history) {
            drawable.putExtra("EXTRA_TYPE", "history");
            startActivity(drawable);
        } else if (id == R.id.nav_on_hold) {
            drawable.putExtra("EXTRA_TYPE", "on_hold");
            startActivity(drawable);
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
                FetchRouteRepository fetchRouteRepository = new FetchRouteRepository(getApplicationContext());
                fetchRouteRepository.requestJson(sendTextFrom,sendTextTo,sendPickedDate,progressBar);
                break;
        }
    }
}
