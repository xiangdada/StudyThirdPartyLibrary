package com.example.admin.studythirdpartylibrary.library.network.retrofit.encapsulation;

import android.content.Context;

import java.util.Map;

/**
 * Created by xpf on 2017/7/12.
 */

public class ApiService {

    public static void test(Context context, Map<String,Object> body, OnResponseCallBack responseCallBack) {
        NetUtil.post(context, new BaseRequest.Builder(context)
                .baseUrl("http://120.27.37.132:9002/TrigMCISP-cdjsxy/")
                .url("appService/appLife_appService.t")
                .packetNo("90030037")
                .body(body)
                .build(), responseCallBack);
    }


}
