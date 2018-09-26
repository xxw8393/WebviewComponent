package com.fqxyi.webview.binder;

public interface WebviewBinderCallback {

    void onServiceConnected();

    void callJs(String params);

}
