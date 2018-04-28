package com.tanzania.comtech.msafiriapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Model.BusModel;

import java.util.ArrayList;

import com.tanzania.comtech.msafiriapp.R;
import com.tanzania.comtech.msafiriapp.Repository.FetchRouteBusRepository;

/**
 * Created by programing on 3/31/2018.
 */

public class CompanyAdapter extends ArrayAdapter<BusModel> {
    private Context context;
    private int resources;
    private ArrayList<BusModel> busRepositories;

    public CompanyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BusModel> objects) {
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

        final BusModel busModel = getItem(position);

        TextView company_name = (TextView)convertView.findViewById(R.id.company_layout_company_name);
        company_name.setText(busModel.getCompanyName() + " (" + busModel.getTotal_buses()+ ")");

        TextView email = (TextView)convertView.findViewById(R.id.company_layout_email);
        email.setText(busModel.getEmail());

        TextView website = (TextView)convertView.findViewById(R.id.company_layout_website);
        website.setText(busModel.getWebsite_url());

        TextView call = (TextView)convertView.findViewById(R.id.company_layout_call);
        call.setText(busModel.getCall());


        Button book_me = (Button)convertView.findViewById(R.id.company_layout_book);
        book_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FetchRouteBusRepository fetchBusRepository = new FetchRouteBusRepository(context);
                fetchBusRepository.requestJson(busModel.getSource(), busModel.getDestination(), busModel.getDate(), busModel.getCompany_id());
            }
        });

        return convertView;
    }
}
