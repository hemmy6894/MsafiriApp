package com.tanzania.comtech.msafiriapp.seat_plan;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tanzania.comtech.msafiriapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Start of layout variables
    LinearLayout Layout;
    final int left_column = 2;
    final int right_column = 2;
    int total_seats = 30;
    int total_column = left_column + right_column + 1;
    int space_between = left_column + 1;
    int total_seats_rows = total_seats / (total_column - 1);
    int remain_seat = total_seats % total_column;
    String rowNames[];
    String[] seatNo;
    int seat_id = 0;
    ///Layout variables ended heare
    private String seat_id_name;
    private int count_selected_seat = 0;
    ////Variable for storing selected seat by customers
    String[] selected_seat_by_customers;
    ImageButton buttonChangeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seat_plan_layout);

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
        layout.setBackgroundColor(Color.rgb(255,255,245));
        layout.setOrientation(LinearLayout.VERTICAL);



        //Creating button for driver seat
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
        //End of driver seat button

        creating_customer_seat(layout,myLayouts,myLayouts2);



        //Adding Coded layout to real UI xml UI
        Layout.addView(layout,myLayouts);
        ArrayArr = new String[67];
    }


    String[] ArrayArr ;
    @Override
    public void onClick(View view) {

        String array_searched = seatNo[view.getId()];
        //Change seat image and pop up seat no selected
        buttonChangeImage = (ImageButton)findViewById(view.getId());
        buttonChangeImage.setImageResource(R.drawable.seat_taken);
        Toast.makeText(getApplicationContext(),"Seat No : " + array_searched,Toast.LENGTH_SHORT).show();
        selected_seat_by_customers[view.getId()] = array_searched; //Storing selected seat by customer

        /*
         * Suppless the codes
         *
        for (String search : selected_seat_by_customers){
            try {
                if(array_searched.contains(search)){
                    Toast.makeText(getApplicationContext(),"" + array_searched,Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"not found",Toast.LENGTH_LONG).show();
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        /*
        To here
         */

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

                ImageButton btn = new ImageButton(MainActivity.this); // iniitiate button for seat
                if (space_between != i || (j==total_seats_rows && remain_seat == 1)) { //creating space beteen two columns
                    btn.setImageResource(R.drawable.empty_seat);
                    seat_id_name = "" + rowNames[j]+""+seat_numbering; // generating seat name in latter and no
                    seat_id = seat_id + 1; // increment seat value
                    seatNo[seat_id] = seat_id_name;
                    btn.setId(seat_id);
                } else {
                    btn.setMinimumWidth(70);
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
                btn.setMaxHeight(70);
                btn.setMaxWidth(70);
                btn.setOnClickListener(this);

                //setting button to rows
                layout3.addView(btn, myLayouts2);
            }

            //setting rows to layout
            layout.addView(layout3, myLayouts);
        }
    }
    ///Creating next button
    public LinearLayout add_view_for_next_button(){
        LinearLayout nextLayout = new LinearLayout(MainActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
              LinearLayout.LayoutParams.MATCH_PARENT,
              LinearLayout.LayoutParams.MATCH_PARENT
            );
            params.gravity = Gravity.CENTER;

            nextLayout.setLayoutParams(params);

            Button myNextButton = new Button(MainActivity.this);
            myNextButton.setText("Next");
            myNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    on_click_next_button();
                }
            });

            nextLayout.addView(myNextButton);
        return nextLayout;
    }

    //Function followed whene next button clicked
    private void on_click_next_button() {

    }


}
