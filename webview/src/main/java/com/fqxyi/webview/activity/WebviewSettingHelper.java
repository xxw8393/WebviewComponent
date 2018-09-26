package com.fqxyi.webview.activity;

import android.app.Activity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.fqxyi.webview.js.JsBridge;

public class WebviewSettingHelper {

    private Activity activity;
    //
    private WebSettings webSettings;

    public WebviewSettingHelper(Activity activity) {
        this.activity = activity;
    }

    public void initWebview(WebView webView, WebviewBinderHelper binderHelper) {
        initWebviewSetting(webView);
        webView.addJavascriptInterface(binderHelper.getJsInterface(), JsBridge.get().getName());
        webView.setWebChromeClient(new CustomWebChromeClient(activity));
        webView.setWebViewClient(new CustomWebviewClient(activity));
    }

    private void initWebviewSetting(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

}
