package com.fqxyi.webview.utils;

import android.app.ActivityManager;
import android.content.Context;

/**
 * 工具类
 */
public class GlobalUtil {

    /**
     * 全局上下文
     */
    public static Context appContext;

    /**
     * 获取当前进程名
     */
    public static String getCurrentProcessName() {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
            if (process.pid == pid) {
                processName = process.processName;
            }
        }
        return processName;
    }

}
