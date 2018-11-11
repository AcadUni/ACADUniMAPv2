package com.elshaikh.mano.acadunimap;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Webing extends AppCompatActivity {
    private WebView wv1;
    public ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webing);
        pb = (ProgressBar) findViewById(R.id.loading);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        String runningurl = (String)getIntent().getExtras().get("runningurl");//"http://192.168.1.103/akad/diplayschool.php?school_id=1&cohort_id=1";//(String)getIntent().getExtras().get("runningurl");
        //String firstKeyName = myIntent.getStringExtra("firstKeyName");
        //url = apps_main.getURL();
        setTitle("School Details");
        wv1=(WebView)findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        //wv1.getSettings().setDomStorageEnabled(true);
        wv1.getSettings().setAllowFileAccess(true);
        wv1.getSettings().setBuiltInZoomControls(true);
        wv1.getSettings().setSupportZoom(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        System.out.println("AAAAAAAAAA: "+runningurl);
        wv1.loadUrl(runningurl);
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pb.setVisibility(View.GONE);
        }
    }
}
