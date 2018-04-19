package com.tanzania.comtech.msafiriapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by programing on 4/18/2018.
 */

public class ClearSharedPreference {
    Context context;

    public ClearSharedPreference(Context context, String[] name,int mode) {
        this.context = context;
        for (String aName : name) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(aName, mode);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().apply();
        }
    }
}
