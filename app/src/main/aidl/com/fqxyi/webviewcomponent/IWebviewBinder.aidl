package com.fqxyi.webviewcomponent;

import com.fqxyi.webviewcomponent.IWebviewBinderCallback;

/**
 * 主进程
 */
interface IWebviewBinder {

    /**
     * 处理JS调用Java的方法
     */
    void handleJsFunc(String methodName, String params, IWebviewBinderCallback callback);

}