package com.fqxyi.webviewcomponent;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * 统一管理Js调用Java的方法
 * 使用方式：
 * javascript:jsObj.JsCallJava('数据')"
 */
public class JsCallJavaManager {

    private Context context;

    public JsCallJavaManager(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void JsCallJava(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
