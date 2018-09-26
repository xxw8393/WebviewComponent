package com.fqxyi.webviewcomponent;

import android.os.RemoteException;
import android.widget.Toast;

import com.fqxyi.webview.IWebviewBinderCallback;
import com.fqxyi.webview.utils.GlobalUtil;

public class JsInterfaceImpl {

    public void testJson(String params, IWebviewBinderCallback callback) {
        //操作主进程UI
        Toast.makeText(GlobalUtil.appContext, "processName = " + GlobalUtil.getCurrentProcessName() + ", params = " + params, Toast.LENGTH_SHORT).show();
        //回调给子进程调用js
        if (callback != null) {
            try {
                callback.callJs("javascript:alert('testJsonCallBack');");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void testMessage(String params, IWebviewBinderCallback callback) {
        //操作主进程UI
        Toast.makeText(GlobalUtil.appContext, "processName = " + GlobalUtil.getCurrentProcessName() + ", params = " + params, Toast.LENGTH_SHORT).show();
        //回调给子进程调用js
        if (callback != null) {
            try {
                callback.callJs("javascript:alert('testMessageCallBack');");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
