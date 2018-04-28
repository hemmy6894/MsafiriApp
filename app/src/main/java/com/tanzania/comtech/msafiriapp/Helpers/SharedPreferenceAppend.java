package com.tanzania.comtech.msafiriapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.tanzania.comtech.msafiriapp.R;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferenceAppend {
    private Context context;
    private Map<String, Object> mapValue;
    private SharedPreferences preferences;
    private Map<String, ?> preferenceMap;
    public SharedPreferenceAppend(Context context) {
        this.context = context;
    }

    private void startSharedPref(Map<String, Object> mapValue, String sharedKey){
        this.mapValue = mapValue;
        preferences = context.getSharedPreferences(sharedKey, Context.MODE_PRIVATE);
    }
    public void appendSharedPref(Map<String, Object> mapValue, String sharedKey){
        startSharedPref(mapValue,sharedKey);
        preferenceMap = new HashMap<>();
        preferenceMap = preferences.getAll();
        for (Map.Entry <String, ?> entry : preferenceMap.entrySet()){
            mapValue.put(entry.getKey(), (String) entry.getValue());
            if(entry.getValue() instanceof Boolean){
                mapValue.put(entry.getKey(), (Boolean) entry.getValue());
            }else if(entry.getValue() instanceof Integer){
                mapValue.put(entry.getKey(), (Integer) entry.getValue());
            }else{
                mapValue.put(entry.getKey(), (String) entry.getValue());
            }
        }
        preferences = context.getSharedPreferences(sharedKey,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (Map.Entry <String, ?> entry : mapValue.entrySet()){
            if(entry.getValue() instanceof Boolean){
                editor.putBoolean(entry.getKey(), (Boolean) entry.getValue() );
            }else if(entry.getValue()  instanceof Integer){
                editor.putInt(entry.getKey(), (Integer) entry.getValue() );
            }else{
                editor.putString(entry.getKey(), (String) entry.getValue() );
            }
        }
        editor.apply();
    }

    public void newSharedPref(Map<String, Object> mapValue, String sharedKey) {
        startSharedPref(mapValue, sharedKey);
        preferences = context.getSharedPreferences(sharedKey,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        for (Map.Entry <String, ?> entry : mapValue.entrySet()){
            if(entry.getValue() instanceof Boolean){
                editor.putBoolean(entry.getKey(), (Boolean) entry.getValue() );
            }else if(entry.getValue()  instanceof Integer){
                editor.putInt(entry.getKey(), (Integer) entry.getValue() );
            }else{
                editor.putString(entry.getKey(), (String) entry.getValue() );
            }
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

    public  Map<String, ?> readSharedPref(String sharedKey){
        startSharedPref(null,sharedKey);
        mapValue = new HashMap<String, Object>();
        preferenceMap = preferences.getAll();
        for (Map.Entry <String, ?> entry : preferenceMap.entrySet()){
            mapValue.put(entry.getKey(), (String) entry.getValue());
            if(entry.getValue() instanceof Boolean){
                mapValue.put(entry.getKey(), (Boolean) entry.getValue());
            }else if(entry.getValue() instanceof Integer){
                mapValue.put(entry.getKey(), (Integer) entry.getValue());
            }else{
                mapValue.put(entry.getKey(), (String) entry.getValue());
            }
        }
        return mapValue;
    }

    public void clearSharedPref(String arrays[]){
        for (String string : arrays){
            startSharedPref(null,string);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
        }
    }
}
