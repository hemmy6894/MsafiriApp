package com.tanzania.comtech.msafiriapp.Helpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.tanzania.comtech.msafiriapp.ChooseTransportType;
import com.tanzania.comtech.msafiriapp.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static com.tanzania.comtech.msafiriapp.API.BusApi.customerBusTicket;


public class ThanksActivity extends Activity implements View.OnClickListener, Response.Listener<byte[]>, Response.ErrorListener {

    VolleyFileDownload request;
    protected WebView mWebview;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thanks);

        mWebview  = (WebView)findViewById(R.id.display_ticket_view);
        final TextView downloading = (TextView)findViewById(R.id.downloading_text);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        mWebview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });
        String string = getIntent().getStringExtra("APPENDED_VALUE");
        String realUrl = "";
        if(string == null || string.equals("")) {
            realUrl = customerBusTicket + "5ada0db654baba0c6c551257/5ada120e54baba0c6c551259";
        }else{
           realUrl = customerBusTicket + string;
        }
        final String downloadedUrl = realUrl + "/download";
        final String loadedUrl = realUrl + "/preview";

        Button downloadPdf = (Button)findViewById(R.id.download_ticket_view);
        downloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request = new VolleyFileDownload(Request.Method.GET, downloadedUrl, ThanksActivity.this, ThanksActivity.this, null);
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(),
                        new HurlStack());
                mRequestQueue.add(request);
            }
        });

        Button printTicket = (Button)findViewById(R.id.print_ticket_view);
        printTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Under Construction",Toast.LENGTH_SHORT).show();
            }
        });

        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        mWebview.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                progressBar.setVisibility(View.GONE);
                mWebview.setVisibility(View.VISIBLE);
            }
        });
        mWebview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //start download
                request = new VolleyFileDownload(Request.Method.GET, downloadedUrl, ThanksActivity.this, ThanksActivity.this, null);
                RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(),
                        new HurlStack());
                mRequestQueue.add(request);
            }
        });

        mWebview .loadUrl(loadedUrl);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(.85 *width),(int)(.8*height));

    }

    @Override
    public void onClick(View view) {
        String array[] = {getString(R.string.shared_preference_route),getString(R.string.shared_preference_route_info),getString(R.string.shared_preference_text_to_pay_for),getString(R.string.shared_preference_bus_data_from_id)};
        new SharedPreferenceAppend(getApplicationContext()).clearSharedPref(array);
        finish();
        startActivity(new Intent(getApplicationContext(), ChooseTransportType.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void onResponse(byte[] response) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            if (response!=null) {

                //Read file name from headers
                String content = request.responseHeaders.get("Content-Disposition");
                StringTokenizer st = new StringTokenizer(content, "=");
                String[] arrTag = st.toArray();

                String filename = arrTag[1];
                filename = filename.replace(":", ".");
                filename = filename.replace("\"", "");
                Log.d("DEBUG::RESUME FILE NAME", filename);

                try{
                    long lenghtOfFile = response.length;

                    String fileNameToWrite = filename;
                    if(fileNameToWrite.length() > 15){
                        fileNameToWrite = fileNameToWrite.substring((fileNameToWrite.length() - 15));
                    }
                    //covert reponse to input stream
                    InputStream input = new ByteArrayInputStream(response);
                    File path = new File(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS).toString()+"/MsfiriApp/Download");
                    File file = new File(path, fileNameToWrite);
                    map.put("resume_path", file.toString());
                    BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
                    byte data[] = new byte[1024];

                    long total = 0;
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                    String CHANNEL_ID = "chanel_id";
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
                    MimeTypeMap mime = MimeTypeMap.getSingleton();
                    String ext=file.getName().substring(file.getName().indexOf(".")+1);
                    String type = mime.getMimeTypeFromExtension(ext);
                    Intent openFile = new Intent(Intent.ACTION_VIEW, Uri.fromFile(file));
                    openFile.setDataAndType(Uri.fromFile(file), type);
                    openFile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, openFile, 0);
                    mBuilder.setContentTitle("Ticket Download")
                            .setContentText(filename)
                            .setSmallIcon(R.mipmap.msafiri_app_logo_two)
                            .setPriority(NotificationCompat.PRIORITY_LOW)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    int PROGRESS_MAX = 100;
                    int PROGRESS_CURRENT = 0;
                    ///mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);

                    int notificationId = 456;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                       // PROGRESS_CURRENT = 0; //(int) ((total *100)/lenghtOfFile);
                       // mBuilder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false);

                        Notification notif =mBuilder.build();
                        notif.flags = Notification.FLAG_ONGOING_EVENT;
                        notificationManager.notify(notificationId, notif);
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                    mBuilder.setContentText("Download complete")
                            .setProgress(100,100,false);
                    notificationManager.notify(notificationId, mBuilder.build());
                }catch(IOException e){
                    e.printStackTrace();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE. ERROR:: "+error.getMessage());
    }

    
}
