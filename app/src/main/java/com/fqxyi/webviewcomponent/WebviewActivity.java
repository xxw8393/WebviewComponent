package com.fqxyi.webviewcomponent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class WebviewActivity extends AppCompatActivity {

    private WebView webView;

    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        init();
    }

    private void init() {
        if (getIntent() != null) {
            url = getIntent().getStringExtra("url");
        }

        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.addJavascriptInterface(WebviewActivity.this, "jsObj");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(WebviewActivity.this, message, Toast.LENGTH_SHORT).show();
                result.confirm();
                return true;
            }
        });
    }

    @JavascriptInterface
    public void JsCallJava(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void JavaCallJs(View view) {
        webView.loadUrl("javascript:JavaCallJS({data: 'Message From Java'});");
    }
}
