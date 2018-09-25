package com.fqxyi.webviewcomponent;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * 子进程中的Webview
 */
public class WebviewActivity extends AppCompatActivity {

    private final int CODE_CALL_JS = 1;

    WebView webView;

    JsInterface jsInterface;

    IWebviewBinder iWebviewBinder;

    ServiceConnection connection;

    WebviewHandler handler;

    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.web_view);

        url = getIntent().getStringExtra("url");

        handler = new WebviewHandler();

        jsInterface = new JsInterface();
        jsInterface.setCallback(new JsInterface.JsFuncCallback() {
            @Override
            public void execute(String methodName, String params) {
                if (iWebviewBinder != null) {
                    try {
                        iWebviewBinder.handleJsFunc(methodName, params, new IWebviewBinderCallback.Stub() {
                            @Override
                            public void callJs(String params) throws RemoteException {
                                if (handler != null) {
                                    Message message = Message.obtain();
                                    message.what = CODE_CALL_JS;
                                    message.obj = params;
                                    handler.sendMessage(message);
                                }
                            }
                        });
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(jsInterface, "jsObj");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Toast.makeText(WebviewActivity.this, "processName = " + Utils.getCurrentProcessName() + "messgae = " + message, Toast.LENGTH_SHORT).show();
                result.confirm();
                return true;
            }
        });

        bindService(
                new Intent(this, MainRemoteService.class),
                connection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        iWebviewBinder = IWebviewBinder.Stub.asInterface(iBinder);
                        if (webView != null) {
                            webView.loadUrl(url);
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName componentName) {

                    }
                },
                Context.BIND_AUTO_CREATE);

    }

    public void JavaCallJs(View view) {
        webView.loadUrl("javascript:alert('testJsonCallBack');");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
