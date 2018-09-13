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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/test.html");
        webView.addJavascriptInterface(WebviewActivity.this, "jsObj");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
    }

    @JavascriptInterface
    public void JSCallJava(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void JavaCallJs(View view) {
        webView.loadUrl("javascript:JavaCallJS('Message From Java');");
    }
}
