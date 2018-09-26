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
import com.fqxyi.webview.binder.WebviewBinderCallback;
import com.fqxyi.webview.binder.WebviewBinderHelper;
import com.fqxyi.webview.setting.WebChromeClientCallback;
import com.fqxyi.webview.setting.WebviewClientCallback;
import com.fqxyi.webview.setting.WebviewSettingHelper;
import com.fqxyi.webview.view.BaseWebview;
import com.fqxyi.webview.view.WebviewTitleBar;
import com.fqxyi.webview.view.WebviewTitleBarCallback;

/**
 * 子进程中的Webview
 */
public class WebviewActivity extends AppCompatActivity implements WebviewBinderCallback, WebChromeClientCallback, WebviewClientCallback {

    private final int CODE_CALL_JS = 1;
    private WebviewHandler handler;
    //findView
    private WebviewTitleBar bar;
    private BaseWebview webview;
    //
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
        initEvent();
        initData();
        handler = new WebviewHandler();
        binderHelper = new WebviewBinderHelper(this, this);
        binderHelper.initJsInterface();
        binderHelper.bindMainRemoteService(this);
        settingHelper = new WebviewSettingHelper(this);
        settingHelper.initWebview(webview, binderHelper, this, this);
    }

    private void findView() {
        bar = (WebviewTitleBar) findViewById(R.id.bar);
        webview = (BaseWebview) findViewById(R.id.webview);
    }

    private void initEvent() {
        bar.setCallback(new WebviewTitleBarCallback() {
            @Override
            public void clickBarBack() {
                if (webview != null) {
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {
                        finish();
                    }
                }
            }

            @Override
            public void clickBarClose() {
                finish();
            }
        });
    }

    private void initData() {
        url = getIntent().getStringExtra("url");
    }

    public void JavaCallJs(View view) {
        webview.loadUrl("javascript:alert('testJsonCallBack');");
    }

    @Override
    public void onServiceConnected() {
        if (webview != null) {
            webview.loadUrl(url);
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
    public void onProgressChanged(WebView view, int newProgress) {
        if (bar != null) {
            bar.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (bar != null) {
            bar.onReceivedTitle(view, title);
        }
    }

    private class WebviewHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_CALL_JS:
                    String params = (String) msg.obj;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        webview.evaluateJavascript(params, null);
                    } else {
                        webview.loadUrl(params);
                    }
                    break;
            }
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

}
