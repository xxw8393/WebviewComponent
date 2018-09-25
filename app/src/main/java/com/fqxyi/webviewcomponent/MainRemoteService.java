package com.fqxyi.webviewcomponent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * 主进程中的Service
 */
public class MainRemoteService extends Service {

    private final int CODE_HANDLE_JS_FUNC = 1;

    private ServiceHandler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new ServiceHandler();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceBinder;
    }

    private IWebviewBinder.Stub serviceBinder = new IWebviewBinder.Stub() {

        @Override
        public void handleJsFunc(String methodName, String params, IWebviewBinderCallback callback) throws RemoteException {
            if (handler != null) {
                Message message = Message.obtain();
                message.what = CODE_HANDLE_JS_FUNC;
                message.obj = callback;
                Bundle bundle = new Bundle();
                bundle.putString("methodName", methodName);
                bundle.putString("params", params);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        }

    };

    private class ServiceHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_HANDLE_JS_FUNC:
                    Bundle bundle = msg.getData();
                    JsBridge.get().callJava(
                            bundle.getString("methodName"),
                            bundle.getString("params"),
                            (IWebviewBinderCallback) msg.obj);
                    break;
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
