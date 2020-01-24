package danysam.net.churchdirectory.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import danysam.net.churchdirectory.LoginActivity;
import danysam.net.churchdirectory.MainActivity;
import danysam.net.churchdirectory.R;

public class WebViewActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        String url = getIntent().getStringExtra("urlString");
        WebView myWebView = (WebView) findViewById(R.id.webview);

        if(haveNetworkConnection() == true){
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            myWebView.setWebChromeClient(new WebChromeClient());
            myWebView.addJavascriptInterface(new WebviewInterface(), "Interface");
            myWebView.loadUrl(url);
            myWebView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //Log.i(TAG, "Processing webview url click...");
                    view.loadUrl(url);
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

    public class WebviewInterface {
        @JavascriptInterface
        public void closePasswordReset() {
            startActivity(new Intent(WebViewActivity.this, LoginActivity.class));
        }
    }
}
