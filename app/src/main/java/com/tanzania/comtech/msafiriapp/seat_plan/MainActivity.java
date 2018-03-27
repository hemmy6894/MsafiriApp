package com.tanzania.comtech.msafiriapp.seat_plan;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tanzania.comtech.msafiriapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout Layout;
    final int left_column = 2;
    final int right_column = 2;
    int total_seats = 30;
    int total_column = left_column + right_column + 1;
    int space_between = left_column + 1;
    int total_seats_rows = total_seats / (total_column - 1);
    int remain_seat = total_seats % total_column;
    String rowNames[];

    int seat_id = 0;
    private String seat_id_name;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_plan_layout);

        rowNames = new String[]{"","A","B","C","D","E","F","G","H","K","L","M","N","O","P"};
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
        layout.setBackgroundColor(Color.rgb(255,255,245));
        layout.setOrientation(LinearLayout.VERTICAL);



        ImageButton btn1 = new ImageButton(MainActivity.this);
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


        int no = seat_id + 1;
        for(int j = 1; j <= total_seats_rows; j++) {
            LinearLayout layout3 = new LinearLayout(MainActivity.this);
            layout3.setBackgroundColor(Color.rgb(255, 255, 245));
            layout3.setOrientation(LinearLayout.HORIZONTAL);

            for (int i = 1; i <= total_column; i++) {
                ImageButton btn = new ImageButton(MainActivity.this);
                if (space_between != i || (j==total_seats_rows && remain_seat == 1)) {
                    btn.setImageResource(R.drawable.empty_seat);
                    seat_id_name = "" + rowNames[j]+""+i;
                    btn.setId(seat_id);
                } else {
                    btn.setMinimumWidth(70);
                    btn.setVisibility(View.INVISIBLE);
                }
                btn.setMaxHeight(70);
                btn.setMaxWidth(70);
                btn.setOnClickListener(this);
                layout3.addView(btn, myLayouts2);
            }

            layout.addView(layout3, myLayouts);
        }
        Layout.addView(layout,myLayouts);
    }


    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(),"Seat Number " + view.getId(),Toast.LENGTH_SHORT).show();
    }
}
