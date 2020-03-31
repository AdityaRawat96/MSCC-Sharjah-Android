package mscc.net.churchdirectory.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import es.voghdev.pdfviewpager.library.RemotePDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;
import es.voghdev.pdfviewpager.library.remote.DownloadFile;
import mscc.net.churchdirectory.LoginActivity;
import mscc.net.churchdirectory.R;

public class WebViewActivity extends AppCompatActivity implements DownloadFile.Listener{

    private boolean zoomEnabled = false;
    private RemotePDFViewPager remotePDFViewPager;
    private PDFPagerAdapter adapter;
    private ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = getIntent().getStringExtra("urlString");
        zoomEnabled = getIntent().getBooleanExtra("zoomEnabled", false);
        WebView myWebView = (WebView) findViewById(R.id.webview);

        if(haveNetworkConnection() == true){
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            myWebView.setWebChromeClient(new WebChromeClient());
            myWebView.addJavascriptInterface(new WebviewInterface(), "Interface");
            myWebView.loadUrl(url);

            if(zoomEnabled == true){
                myWebView.getSettings().setBuiltInZoomControls(true);
                myWebView.getSettings().setDisplayZoomControls(false);
                myWebView.getSettings().setLoadWithOverviewMode(true);
                myWebView.getSettings().setUseWideViewPort(true);
            }

            myWebView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.endsWith(".pdf")) {
                        myWebView.setVisibility(View.INVISIBLE);
                        remotePDFViewPager = findViewById(R.id.pdfViewPager);
                        remotePDFViewPager.setVisibility(View.VISIBLE);
                        remotePDFViewPager = new RemotePDFViewPager(getApplicationContext(), url, WebViewActivity.this);
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        myWebView.loadUrl(url);
                    }

                    return true;
                }

                public void onPageFinished(WebView view, String url) {
                    //Log.i(TAG, "Finished loading URL: " +url);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    //Log.e(TAG, "Error: " + description);
                    //Toast.makeText(WebViewActivity.this, "Page Load Error" + description, Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            myWebView.setVisibility(View.INVISIBLE);
            ImageView back = findViewById(R.id.imageViewNoInternet);
            ImageView front = findViewById(R.id.imageViewNoInternetBackground);
            TextView textView = findViewById(R.id.textViewNoInternet);

            back.setVisibility(View.VISIBLE);
            front.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
        }


    }


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    @Override
    public void onSuccess(String url, String destinationPath) {
        adapter = new PDFPagerAdapter(this, destinationPath);
        remotePDFViewPager.setAdapter(adapter);
        setContentView(remotePDFViewPager);
    }

    @Override
    public void onFailure(Exception e) {
        Toast.makeText(WebViewActivity.this, "An error ocurred! Please try again.", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onProgressUpdate(int progress, int total) {
        if(progress >= total){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.close();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public class WebviewInterface {

        @JavascriptInterface
        public void closePasswordReset() {
            startActivity(new Intent(WebViewActivity.this, LoginActivity.class));
            finish();
        }

    }


}
