package com.tanzania.comtech.msafiriapp.Helpers;

public class JavaStringOperation {
    public static String replace(String word){
        return word.replaceAll("_"," ");
    }

    public static String capitalize(String word){
        if (word.length() > 0)
        return word.substring(0,1) + word.substring(1);
        else return "";
    }

    public static String replaceCapitalize(String word){
        return capitalize(replace(word));
    }
}
