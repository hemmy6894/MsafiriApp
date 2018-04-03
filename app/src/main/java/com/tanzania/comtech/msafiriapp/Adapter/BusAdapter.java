package com.tanzania.comtech.msafiriapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tanzania.comtech.msafiriapp.Repository.BusRepository;

import java.util.ArrayList;

import com.tanzania.comtech.msafiriapp.R;
/**
 * Created by programing on 3/31/2018.
 */

public class BusAdapter extends ArrayAdapter<BusRepository> {
    Context context;
    int resources;
    ArrayList<BusRepository> busRepositories;

    public BusAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BusRepository> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resources = resource;
        this.busRepositories = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater in = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = in.inflate(R.layout.element_single_bus_view_og,null,true);
        }

        final BusRepository busRepository = getItem(position);

        TextView bus_name = (TextView)convertView.findViewById(R.id.element_single_bus_view_bus_name);
        bus_name.setText(busRepository.getBus_name());

        TextView check_in = (TextView)convertView.findViewById(R.id.element_single_bus_view_check_in_time);
        check_in.setText(busRepository.getCheck_in());

        TextView departure = (TextView)convertView.findViewById(R.id.element_single_bus_view_departure_time);
        departure.setText(busRepository.getDeparture_time());

        TextView fear_price = (TextView)convertView.findViewById(R.id.element_single_bus_view_price);
        fear_price.setText(busRepository.getFear_price());

        TextView available = (TextView)convertView.findViewById(R.id.element_single_bus_view_available_seat);
        available.setText(busRepository.getAvailable_seat());

        TextView bus_id = (TextView)convertView.findViewById(R.id.element_single_bus_view_bus_id);
        bus_id.setText(busRepository.getBus_id());

        Button book_me = (Button)convertView.findViewById(R.id.element_single_bus_view_button_book_me);
        book_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Bus Id " + busRepository.getBus_id(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
