package com.example.admin.studythirdpartylibrary.uitl;

import android.util.Log;

/**
 * Created by xpf on 2017/7/11.
 */

public class LogUtil {
    private static final boolean isDeBug = true;

    public static void i(String tag, String msg) {
        if (isDeBug) {
            Log.i(tag, msg);
        }
    }
}
