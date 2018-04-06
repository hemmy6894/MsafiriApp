package com.tanzania.comtech.msafiriapp.Helpers;

/**
 * Created by programing on 4/6/2018.
 */

public class LeftRightSeat {
    private int right_seat;
    private int left_seat;

    public void left_right_seat(String seat_type){
        if(seat_type == "2_X_3"){
            left_seat = 2;
            right_seat = 3;
        }else if(seat_type == "2_X_2"){
            left_seat = 2;
            right_seat = 2;
        }else if(seat_type == "1_X_2"){
            left_seat = 1;
            right_seat = 2;
        }
    }
}
