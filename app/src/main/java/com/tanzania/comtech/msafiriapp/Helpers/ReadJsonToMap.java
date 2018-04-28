package com.tanzania.comtech.msafiriapp.Helpers;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReadJsonToMap {
    private Context context;

    public ReadJsonToMap(Context context) {
        this.context = context;
    }

    public Map<String, Object> readJsonToMap(JSONObject jsonObject){
        HashMap<String, Object> map = new HashMap<>();
        Iterator<String> inter = jsonObject.keys();
        while(inter.hasNext()){
            String key = inter.next();
            try {
                map.put(key,jsonObject.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
