package com.tanzania.comtech.msafiriapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Helpers.JavaStringOperation;
import com.tanzania.comtech.msafiriapp.Model.DrawerModel;
import com.tanzania.comtech.msafiriapp.R;

import java.util.ArrayList;

public class DrawableAdapter  extends ArrayAdapter<DrawerModel> {
    private Context context;
    private int resources;
    private ArrayList<DrawerModel> busRepositories;

    public DrawableAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DrawerModel> objects) {
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
            convertView = in.inflate(R.layout.element_single_bus_view_og_two,null,true);
        }

        final DrawerModel drawerModel = getItem(position);

        TextView treval_date, bus_name, helpline_no, status, source, destination;

        treval_date = convertView.findViewById(R.id.drawer_travel_date);
        bus_name = convertView.findViewById(R.id.drawer_bus_name);
        helpline_no = convertView.findViewById(R.id.drawer_helpline_no);
        status = convertView.findViewById(R.id.drawer_status);
        source = convertView.findViewById(R.id.drawer_source);
        destination = convertView.findViewById(R.id.drawer_destination);

        treval_date.setText("" + drawerModel.getSch_departure_time());
        bus_name.setText(drawerModel.getBus_name());
        helpline_no.setText(drawerModel.getHelp_line_no());
        status.setText(JavaStringOperation.replaceCapitalize("ticket_canceled"));
        source.setText(drawerModel.getBus_from());
        destination.setText(drawerModel.getBus_to());
        return convertView;
    }
}

