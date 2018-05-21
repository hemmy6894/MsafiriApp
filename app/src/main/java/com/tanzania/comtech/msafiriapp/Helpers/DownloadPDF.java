package com.tanzania.comtech.msafiriapp.Helpers;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebView;
import android.widget.TextView;

import com.tanzania.comtech.msafiriapp.ChooseTransportType;
import com.tanzania.comtech.msafiriapp.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DownloadPDF extends AsyncTask<String, Integer, String> {
    @SuppressLint("StaticFieldLeak")
    protected Context context;
    DownloadPDF(Context context, WebView mWebview, TextView downloading){
        this.context = context;
        mWebview.setVisibility(View.GONE);
        downloading.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(String... sUrl) {
        try {
            URL url = new URL(sUrl[0]);

            File myDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS).toString()+"/MsfiriApp/Download");

            //create the directory if it does not exist
            if (!myDir.exists()) myDir.mkdirs();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.connect();

            //get filename from the contentDisposition
            String filename = null;
            Pattern p = Pattern.compile("\"([^\"]*)\"");
            Matcher m = p.matcher(sUrl[2]);
            while (m.find()) {
                filename = m.group(1);
            }

            File outputFile = new File(myDir, filename);

            InputStream input   = new BufferedInputStream(connection.getInputStream());
            OutputStream output = new FileOutputStream(outputFile);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            String CHANNEL_ID = "chanel_id";
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String ext=outputFile.getName().substring(outputFile.getName().indexOf(".")+1);
            String type = mime.getMimeTypeFromExtension(ext);
            Intent openFile = new Intent(Intent.ACTION_VIEW, Uri.fromFile(outputFile));
            openFile.setDataAndType(Uri.fromFile(outputFile), type);
            openFile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, openFile, 0);
            mBuilder.setContentTitle("Ticket Download")
                    .setContentText(filename)
                    .setSmallIcon(R.mipmap.msafiri_app_logo_two)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            int PROGRESS_MAX = 100;
            int PROGRESS_CURRENT = 0;


            while ((count = input.read(data)) != -1) {
                total += count;
                PROGRESS_CURRENT = (int)total / 100;
                output.write(data, 0, count);
                mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);
            }



            int notificationId = 456;
            notificationManager.notify(notificationId, mBuilder.build());

            mBuilder.setContentText("Download complete")
                    .setProgress(0,0,false);
            notificationManager.notify(notificationId, mBuilder.build());

            connection.disconnect();
            output.flush();
            output.close();
            input.close();

            String array[] = {context.getString(R.string.shared_preference_route),context.getString(R.string.shared_preference_route_info),context.getString(R.string.shared_preference_text_to_pay_for),context.getString(R.string.shared_preference_bus_data_from_id)};
            new SharedPreferenceAppend(context).clearSharedPref(array);
            context.startActivity(new Intent(context, ChooseTransportType.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
            // displayPdf();  a function to open the PDF file automatically

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}