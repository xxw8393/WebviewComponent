package com.fqxyi.webview.js;

import android.webkit.JavascriptInterface;

public final class JsInterface {

    private JsFuncCallback mCallback;

    public void setCallback(JsFuncCallback callback) {
        this.mCallback = callback;
    }

    public interface JsFuncCallback {
        void execute(String methodName, String params);
    }

    @JavascriptInterface
    public void jsFunc(final String methodName, final String param) {
        if (mCallback != null) {
            mCallback.execute(methodName, param);
        }
    }

}
