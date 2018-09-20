package com.fqxyi.webviewcomponent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class WebviewActivity extends AppCompatActivity {

    private WebView webView;

    private String url;

    private JsCallJavaManager jsCallJavaManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        init();
    }

    private void init() {
        jsCallJavaManager = new JsCallJavaManager(getApplicationContext());

        if (getIntent() != null) {
            url = getIntent().getStringExtra("url");
        }

        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.addJavascriptInterface(jsCallJavaManager, "jsObj");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(WebviewActivity.this, message, Toast.LENGTH_SHORT).show();
                result.confirm();
                return true;
            }
        });
    }

    public void JavaCallJs(View view) {
        webView.loadUrl(JavaCallJsManager.getJson("JavaCallJs", "{data: 'Message From Java'}"));
    }

}
