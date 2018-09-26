package com.fqxyi.webviewcomponent;

import android.app.Application;
import android.content.Intent;

import com.fqxyi.webview.js.JsBridge;
import com.fqxyi.webview.service.PreWebService;
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
        //进程预加载
        startService(new Intent(this, PreWebService.class));
    }

}
