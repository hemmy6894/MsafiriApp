package com.tanzania.comtech.msafiriapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.tanzania.comtech.msafiriapp.R;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferenceAppend {
    private Context context;
    private Map<String, String> mapValue;
    private SharedPreferences preferences;
    Map<String, ?> preferenceMap;
    public SharedPreferenceAppend(Context context) {
        this.context = context;
    }

    private void startSharedPref(Map<String, String> mapValue,String sharedKey){
        this.mapValue = mapValue;
        preferences = context.getSharedPreferences(sharedKey, Context.MODE_PRIVATE);
    }
    public void appendSharedPref(Map<String, String> mapValue,String sharedKey){
        startSharedPref(mapValue,sharedKey);
        preferenceMap = new HashMap<>();
        preferenceMap = preferences.getAll();
        for (Map.Entry <String, ?> entry : preferenceMap.entrySet()){
            if(!entry.getKey().equals(context.getString(R.string.shared_phone_verified)))
            mapValue.put(entry.getKey(), (String) entry.getValue());
        }
        preferences = context.getSharedPreferences(sharedKey,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (Map.Entry <String, ?> entry : mapValue.entrySet()){
            if(!entry.getKey().equals(context.getString(R.string.shared_phone_verified)))
                editor.putString(entry.getKey(), (String) entry.getValue());
        }
        editor.apply();
    }

    public void newSharedPref(Map<String, String> mapValue,String sharedKey) {
        startSharedPref(mapValue, sharedKey);
        preferences = context.getSharedPreferences(sharedKey,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for (Map.Entry <String, ?> entry : mapValue.entrySet()){
            if(!entry.getKey().equals(context.getString(R.string.shared_phone_verified)))
                editor.putString(entry.getKey(), (String) entry.getValue());
            Log.e(entry.getKey(),""+entry.getValue());
        }
        editor.apply();
    }

    public void newSharedPrefNormal(String storedString,String sharedKey) {
        startSharedPref(mapValue, sharedKey);
        preferences = context.getSharedPreferences(sharedKey,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("addedValue",storedString);
        editor.apply();
    }

    public  String readSharedPrefNormal(String sharedKey){
        startSharedPref(null,sharedKey);
        return preferences.getString("addedValue","{}");
    }

    public  Map<String, String> readSharedPref(String sharedKey){
        startSharedPref(null,sharedKey);
        mapValue = new HashMap<>();
        preferenceMap = preferences.getAll();
        for (Map.Entry <String, ?> entry : preferenceMap.entrySet()){
            if(!entry.getKey().equals(context.getString(R.string.shared_phone_verified)))
                mapValue.put(entry.getKey(), (String) entry.getValue());
        }
        return mapValue;
    }
}
