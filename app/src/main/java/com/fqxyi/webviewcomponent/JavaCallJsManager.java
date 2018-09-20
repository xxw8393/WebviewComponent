package com.fqxyi.webviewcomponent;

/**
 * 统一管理Java调用Js的方法
 * 使用方式：
 * webView.loadUrl(JavaCallJsManager.getJson("回调名称", "回调数据"));
 * webView.loadUrl(JavaCallJsManager.getMessage("回调名称", "回调数据"));
 */
public class JavaCallJsManager {

    /**
     * 回调JSON形式的数据
     * @param name 回调名称
     * @param json 回调数据
     * @return
     */
    public static String getJson(String name, String json) {
        return "javascript:"+ name +"(" + json + ")";
    }

    /**
     * 回调Message形式的数据
     * @param name 回调名称
     * @param message 回调数据
     * @return
     */
    public static String getMessage(String name, String message) {
        return "javascript:"+ name +"(" + "'" + message + "'" + ")";
    }

}
