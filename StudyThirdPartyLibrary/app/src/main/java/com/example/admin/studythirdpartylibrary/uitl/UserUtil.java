package com.example.admin.studythirdpartylibrary.uitl;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xpf on 2017/7/12.
 */

public class UserUtil {

    /**
     * 获取登录用户的cookie
     *
     * @param context
     * @return
     */
    public static String getUserCookie(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo",Context.MODE_PRIVATE);
        return sharedPreferences.getString("userCoolie","");
    }

}
