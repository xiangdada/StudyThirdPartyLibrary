package com.example.admin.studythirdpartylibrary.uitl;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by xpf on 2017/7/12.
 */

public class AppUtil {

    /**
     * 获取app版本号
     *
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        return context.getPackageManager().getPackageInfo(
                context.getPackageName(), 0).versionName;
    }
}
