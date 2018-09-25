package com.fqxyi.webviewcomponent;

import android.app.Application;

/**
 * 注册统一实现类
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.appContext = getApplicationContext();

        JsBridge.get().register(JsInterfaceImpl.class);
    }

}
