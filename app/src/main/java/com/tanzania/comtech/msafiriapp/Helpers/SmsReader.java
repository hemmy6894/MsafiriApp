package com.tanzania.comtech.msafiriapp.Helpers;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by programing on 4/16/2018.
 */

public class SmsReader extends BroadcastReceiver {

    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        preferences = context.getSharedPreferences("ReceiveSms",Context.MODE_PRIVATE);
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from = "";
            String msgBody = "";
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();

                        if(msg_from.equals("+19412144420") || msg_from.equals("eValidate") || msg_from.equals("+255685639653") || msg_from.equals("0685639653") ) {
                            msgBody = msgs[i].getMessageBody();
                            int otp = cutRequiredPart(msgBody);

                            markMessageRead(context, msg_from, msgBody);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("smsCode", String.valueOf(otp));
                            editor.apply();
                        }
                    }
                }catch(Exception e){
                       Log.e("Exception caught",e.getMessage());
                }
            }
        }
    }

    public int cutRequiredPart(String sms){
        String pattern = "\\d+";
        Pattern intPattern = Pattern.compile(pattern);
        Matcher matcher = intPattern.matcher(sms);
        matcher.find();
        String input = matcher.group();
        return Integer.parseInt(input);
    }

    private void markMessageRead(Context context, String number, String body) {
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        try{
            while (cursor.moveToNext()) {
                if ((cursor.getString(cursor.getColumnIndex("address")).equals(number)) && (cursor.getInt(cursor.getColumnIndex("read")) == 0)) {
                    if (cursor.getString(cursor.getColumnIndex("body")).startsWith(body)) {
                        String SmsMessageId = cursor.getString(cursor.getColumnIndex("_id"));
                        ContentValues values = new ContentValues(); values.put("read", true);
                        context.getContentResolver().update(Uri.parse("content://sms/inbox"), values, "_id=" + SmsMessageId, null); return;
                    }
                }
            }
        }catch(Exception e) {
            Log.e("Mark Read", "Error in Read: "+e.toString());
        }
    }
}
