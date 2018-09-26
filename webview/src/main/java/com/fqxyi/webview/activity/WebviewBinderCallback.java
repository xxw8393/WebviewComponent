package com.fqxyi.webview.activity;

public interface WebviewBinderCallback {

    void onServiceConnected();

    void callJs(String params);

}
