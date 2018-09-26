package com.fqxyi.webview.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

import com.fqxyi.webview.R;

/**
 * 子进程中的Webview
 */
public class WebviewActivity extends AppCompatActivity implements WebviewBinderCallback {

    private final int CODE_CALL_JS = 1;
    private WebviewHandler handler;
    //
    private WebView webView;
    private String url;
    //
    private WebviewBinderHelper binderHelper;
    private WebviewSettingHelper settingHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        init();
    }

    private void init() {
        findView();
        initData();
        handler = new WebviewHandler();
        binderHelper = new WebviewBinderHelper(this, this);
        binderHelper.initJsInterface();
        binderHelper.bindMainRemoteService(this);
        settingHelper = new WebviewSettingHelper(this);
        settingHelper.initWebview(webView, binderHelper);
    }

    private void findView() {
        webView = (WebView) findViewById(R.id.web_view);
    }

    private void initData() {
        url = getIntent().getStringExtra("url");
    }

    public void JavaCallJs(View view) {
        webView.loadUrl("javascript:alert('testJsonCallBack');");
    }

    @Override
    public void onServiceConnected() {
        if (webView != null) {
            webView.loadUrl(url);
        }
    }

    @Override
    public void callJs(String params) {
        if (handler != null) {
            Message message = Message.obtain();
            message.what = CODE_CALL_JS;
            message.obj = params;
            handler.sendMessage(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binderHelper != null) {
            binderHelper.destroyConnection();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private class WebviewHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_CALL_JS:
                    String params = (String) msg.obj;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        webView.evaluateJavascript(params, null);
                    } else {
                        webView.loadUrl(params);
                    }
                    break;
            }
        }

    }

}
