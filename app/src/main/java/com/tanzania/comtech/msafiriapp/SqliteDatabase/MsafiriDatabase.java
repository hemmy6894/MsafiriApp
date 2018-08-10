package com.tanzania.comtech.msafiriapp.SqliteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MsafiriDatabase extends SQLiteOpenHelper{
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS ";

    public static final String DATABASE_NAME = "M-SafariApp";
    private  String TABLE_NAME = "zero";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "SURNAME";
    public static final String COL_4 = "MARKS";
    private String QUERY_TABLE = "create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,MARKS INTEGER)";
    private String QUERY_VALUES = "";

    public String createTable(String tableName, String[] fieldNames){
        StringBuilder sql = new StringBuilder("CREATE TABLE " + tableName + " ( _id int not null AUTO_INCREMENT, ");
        final int n = fieldNames.length;
        int i = 0;
        for (String fieldName : fieldNames) {
            sql.append(" ").append(fieldName).append(" TEXT");
            ++i;
            if(i != n){
                sql.append(",");
            }else{
                sql.append(")");
            }
        }
       return String.valueOf(sql);
    }

    public String valuesReader(JSONArray jsonObject, String[] headers){
        List<String> list = Arrays.asList(headers);
        Collections.reverse(list);

        int iii = list.size();

        StringBuilder sql = new StringBuilder("(");
        int iii2 = 0;
        for (String l : list){
            iii2++;
            sql.append(l);

            if (iii2 != iii){
                sql.append(",");
            }
        }
        sql.append(") ");
        sql.append("VALUES");
        Log.e("RETURNED_VALUE",jsonObject.toString());
        try {
            for (int i = 0; i < jsonObject.length(); i++){
                JSONObject object = jsonObject.getJSONObject(i);
                Iterator<?> keys = object.keys();
                sql.append("(");
                    while (keys.hasNext()){
                        String key = (String)keys.next();
                        if(object.get(key) instanceof String){
                            sql.append(object.getString(key));
                            if(keys.hasNext()){
                                sql.append(",");
                            }else{
                                sql.append(")");
                            }
                        }
                    }
                    if(i != (jsonObject.length() - 1)) {
                        sql.append(",");
                    }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return String.valueOf(sql);
    }



    public MsafiriDatabase(Context context, String tableName, String[] fields, JSONArray array) {
        super(context, DATABASE_NAME, null, 1);
        QUERY_TABLE = createTable(tableName,fields);
        QUERY_VALUES = valuesReader(array,fields);
        TABLE_NAME = tableName;
    }

    public  MsafiriDatabase(Context context,String tableName){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL(QUERY_TABLE);
        db.execSQL(QUERY_VALUES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String[][] fieldValues,String[] fieldHeader) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int i = 0;
        for(String[] fieled : fieldValues){
            for(String fill : fieled){
                contentValues.put(fieldHeader[i],fill);
                i++;
            }
            db.insert(TABLE_NAME,null ,contentValues);
        }
    }

    public Cursor getAllData(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+tableName,null);
        return res;
    }

    public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
}
