package com.tanzania.comtech.msafiriapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.Helpers.SharedPreferenceAppend;
import com.tanzania.comtech.msafiriapp.Repository.FetchBusRepository;
import com.tanzania.comtech.msafiriapp.R;
import com.tanzania.comtech.msafiriapp.Model.CompanyModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by programing on 3/31/2018.
 */

public class BusAdapter extends ArrayAdapter<CompanyModel> {
    private Context context;
    private int resources;
    private ArrayList<CompanyModel> busRepositories;

    public BusAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CompanyModel> objects) {
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

        final CompanyModel companyModel = getItem(position);

        if(!companyModel.isBus_visible() || !companyModel.isSch_visible()){
            convertView.setVisibility(View.GONE);
        }
        TextView company_name = (TextView)convertView.findViewById(R.id.company_layout_company_name);
        company_name.setText(companyModel.getBus_name() + " (" + companyModel.getBus_max_seat_no() +")");

        TextView email = (TextView)convertView.findViewById(R.id.company_layout_email);
        email.setText(companyModel.getSch_departure_time());

        TextView website = (TextView)convertView.findViewById(R.id.company_layout_website);
        website.setText(companyModel.getSch_arrival_time());

        TextView call = (TextView)convertView.findViewById(R.id.company_layout_call);
        call.setText(companyModel.getBus_driver_incharge());


        Button book_me = (Button)convertView.findViewById(R.id.company_layout_book);
        book_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put(context.getString(R.string.shared_fare),companyModel.getSch_fare());
                objectMap.put(context.getString(R.string.shared_discount),companyModel.getSch_discount());
                new SharedPreferenceAppend(context).newSharedPref(objectMap,context.getString(R.string.shared_fare));

                Log.e("priceMap","price " + objectMap);
                FetchBusRepository fetchBusRepository = new FetchBusRepository(context);
                fetchBusRepository.fetchBusInfo(companyModel.getBus_id());
            }
        });

        return convertView;
    }
}
