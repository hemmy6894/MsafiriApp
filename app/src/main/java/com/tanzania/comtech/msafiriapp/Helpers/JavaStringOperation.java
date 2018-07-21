package com.tanzania.comtech.msafiriapp.Helpers;

import android.os.Environment;

import java.io.File;

public class JavaStringOperation {

    public final static File ticketPath = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS).toString()+ "/MsafirApp/Download");

    private static String replace(String word){
        return word.replaceAll("_"," ");
    }

    public static String replace(String word,String regex, String replacement){
        return word.replaceAll(regex,replacement);
    }

    private static String capitalize(String word){
        if (word.length() > 0)
        return word.substring(0,1) + word.substring(1);
        else return "";
    }

    public static String replaceCapitalize(String word){
        return capitalize(replace(word));
    }
}
