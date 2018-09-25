package com.fqxyi.webviewcomponent;

import android.os.RemoteException;
import android.widget.Toast;

public class JsInterfaceImpl {

    public void testJson(String params, IWebviewBinderCallback callback) {
        //操作主进程UI
        Toast.makeText(Utils.appContext, "processName = " + Utils.getCurrentProcessName() + ", params = " + params, Toast.LENGTH_SHORT).show();
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
        Toast.makeText(Utils.appContext, "processName = " + Utils.getCurrentProcessName() + ", params = " + params, Toast.LENGTH_SHORT).show();
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
