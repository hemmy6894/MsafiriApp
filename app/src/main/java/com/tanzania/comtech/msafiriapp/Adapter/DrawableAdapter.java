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

import java.util.ArrayList;

public class DrawableAdapter  extends ArrayAdapter<DrawableAdapter> {
    private Context context;
    private int resources;
    private ArrayList<DrawableAdapter> busRepositories;

    public DrawableAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DrawableAdapter> objects) {
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

        final DrawableAdapter drawableAdapter = getItem(position);

        return convertView;
    }
}

