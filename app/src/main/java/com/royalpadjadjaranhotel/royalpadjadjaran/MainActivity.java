package com.royalpadjadjaranhotel.royalpadjadjaran;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView myWebView;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    ProgressBar progressBar;
    TextView textView;
    String url = "https://royalpadjadjaranhotel.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textViewLoader);

        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {

                progressBar.setProgress(progress);
                textView.setText(progress + "%");
            }
        });
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings browserSetting = myWebView.getSettings();
        browserSetting.setJavaScriptEnabled(true);

        myWebView.loadUrl(url);

    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            progressBar.setAlpha((float) 0.6);
            textView.setAlpha((float) 0.6);
            view.loadUrl(url);
            return true;
            //return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            findViewById(R.id.imageLoading1).setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            findViewById(R.id.webView).setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            showExitConfirmDialog();

        }
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            showExitConfirmDialog();

        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

    }


    public void showExitConfirmDialog(){ // just show an dialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Royal Padjadjaran Hotel"); // set title
        dialog.setIcon(R.drawable.ic_launcher); // set icon
        dialog.setMessage("Are you sure want to exit?"); // set message
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish(); // when click OK button, finish current activity!
                    }
                });
        dialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Cancelled", Toast.LENGTH_SHORT).show(); // just show a Toast, do nothing else
                    }
                });
        dialog.create().show();
    }
}
