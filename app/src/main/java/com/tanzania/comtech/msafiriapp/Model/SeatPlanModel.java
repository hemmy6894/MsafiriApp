package com.tanzania.comtech.msafiriapp.Model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.tanzania.comtech.msafiriapp.R;
import com.tanzania.comtech.msafiriapp.Repository.SeatPlanRepository;

import java.util.Observable;

public class SeatPlanModel extends ViewModel {
    public MutableLiveData<Object> data;

    public  ObservableField<Object> objectObservableField = new ObservableField<>();
    public  SeatPlanRepository seatPlanRepository;

    public SeatPlanModel(){
        seatPlanRepository = new SeatPlanRepository();
        objectObservableField.set(R.drawable.seat_taken);
        data = seatPlanRepository.getResponse();
    }
    public MutableLiveData<Object> getData() {
        if (data == null){
            data = new MutableLiveData<>();
        }
        return data;
    }
}
