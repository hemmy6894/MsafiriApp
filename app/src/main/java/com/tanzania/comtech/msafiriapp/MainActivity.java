package com.tanzania.comtech.msafiriapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

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

        setContentView(R.layout.bus_layout_list_bus);

        mTextMessage = (TextView) findViewById(R.id.textView2);
      //  BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
