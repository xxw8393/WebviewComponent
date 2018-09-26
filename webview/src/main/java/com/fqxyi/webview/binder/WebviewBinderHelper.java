package com.fqxyi.webview.binder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.fqxyi.webview.IWebviewBinder;
import com.fqxyi.webview.IWebviewBinderCallback;
import com.fqxyi.webview.js.JsInterface;
import com.fqxyi.webview.service.MainRemoteService;

public class WebviewBinderHelper implements JsInterface.JsFuncCallback {

    private Activity activity;
    private WebviewBinderCallback callback;
    //
    private JsInterface jsInterface;
    //
    private IWebviewBinder iWebviewBinder;
    //
    private ServiceConnection conn;

    public WebviewBinderHelper(Activity activity, WebviewBinderCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    public void initJsInterface() {
        jsInterface = new JsInterface();
        jsInterface.setCallback(this);
    }

    public void bindMainRemoteService(Activity activity) {
        activity.bindService(
                new Intent(activity, MainRemoteService.class),
                conn = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                        iWebviewBinder = IWebviewBinder.Stub.asInterface(iBinder);
                        if (callback != null) {
                            callback.onServiceConnected();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName componentName) {

                    }
                },
                Context.BIND_AUTO_CREATE);
    }

    @Override
    public void execute(String methodName, String params) {
        if (iWebviewBinder != null) {
            try {
                iWebviewBinder.handleJsFunc(methodName, params, new IWebviewBinderCallback.Stub() {
                    @Override
                    public void callJs(String params) throws RemoteException {
                        if (callback != null) {
                            callback.callJs(params);
                        }
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void destroyConnection() {
        if (conn != null) {
            activity.unbindService(conn);
        }
    }

    public JsInterface getJsInterface() {
        return jsInterface;
    }

}
