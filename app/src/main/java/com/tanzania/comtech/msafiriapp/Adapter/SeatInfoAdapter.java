package com.tanzania.comtech.msafiriapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tanzania.comtech.msafiriapp.R;
import com.tanzania.comtech.msafiriapp.Model.FillSeatInfo;

import java.util.ArrayList;


/**
 * Created by programing on 4/6/2018.
 */

public class SeatInfoAdapter extends ArrayAdapter<FillSeatInfo> {
    private Context context;
    private int resource;
    private ArrayList<FillSeatInfo> seatInfo;


    public SeatInfoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FillSeatInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        seatInfo = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater in = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = in.inflate(R.layout.element_single_bus_view_og, null, true);
        }


        return convertView;
    }
}
