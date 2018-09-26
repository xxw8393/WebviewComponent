package com.fqxyi.webviewcomponent;

import android.app.Application;

import com.fqxyi.webview.js.JsBridge;
import com.fqxyi.webview.utils.GlobalUtil;

/**
 * 注册统一实现类
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GlobalUtil.appContext = getApplicationContext();

        JsBridge.get().register(JsInterfaceImpl.class, "jsObj");
    }

}
