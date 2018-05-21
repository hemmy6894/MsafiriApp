package com.tanzania.comtech.msafiriapp.Helpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tanzania.comtech.msafiriapp.ChooseTransportType;
import com.tanzania.comtech.msafiriapp.R;

import static com.tanzania.comtech.msafiriapp.API.BusApi.customerBusTicket;


public class ThanksActivity extends Activity implements View.OnClickListener {

    protected WebView mWebview;
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
        final String realUrl = customerBusTicket + "5ada0db654baba0c6c551257/5ada120e54baba0c6c551259";
        final String downloadedUrl = realUrl + "/download";
        final String loadedUrl = realUrl + "/preview";

        Button downloadPdf = (Button)findViewById(R.id.download_ticket_view);
        downloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadPDF downloadPDF = new DownloadPDF(getApplicationContext(),mWebview,downloading);
                downloadPDF.execute(downloadedUrl,"","");
            }
        });

        Button printTicket = (Button)findViewById(R.id.print_ticket_view);
        printTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Under Construction",Toast.LENGTH_SHORT).show();
            }
        });

        mWebview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //start download
                DownloadPDF downloadPDF = new DownloadPDF(getApplicationContext(),mWebview,downloading);
                downloadPDF.execute(downloadedUrl,userAgent,contentDisposition);
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
}
