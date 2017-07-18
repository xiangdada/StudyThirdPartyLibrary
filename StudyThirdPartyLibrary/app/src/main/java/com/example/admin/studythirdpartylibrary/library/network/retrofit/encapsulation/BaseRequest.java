package com.example.admin.studythirdpartylibrary.library.network.retrofit.encapsulation;

import android.content.Context;
import android.content.pm.PackageManager;

import com.example.admin.studythirdpartylibrary.uitl.AppUtil;
import com.example.admin.studythirdpartylibrary.uitl.UserUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xpf on 2017/7/12.
 */

public class BaseRequest {
    /**
     * 公共路径
     */
    private String baseUrl;
    /**
     * 路径
     */
    private String url;
    /**
     * 报文
     */
    private String packetNo;
    /**
     * 请求体
     */
    private Map<String, Object> body;
    /**
     * 请求头
     */
    private Map<String, String> head;
    /**
     * 请求数据
     */
    private Map<String, Object> data;
    /**
     * 上下文
     */
    private Context context;

    public BaseRequest(Builder builder) {
        this.url = builder.url;
        this.packetNo = builder.packetNo;
        this.body = builder.body;
        this.context = builder.context;
        this.baseUrl = builder.baseUrl;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public String url() {
        return url;
    }

    public Map<String, Object> body() {
        return body;
    }

    public Map<String,String> header() {
        //请求头
        head = new HashMap<>();
        // 添加Cookie
        head.put("cookie", UserUtil.getUserCookie(context));
        // 添加请求码
        head.put("req_code","2");
        // 添加版本号
        try {
            head.put("app_ver", AppUtil.getVersionName(context));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 添加报文编号
        head.put("pack_no",packetNo);
        // 添加报文版本号
        head.put("pack_ver","1.0");
        return head;
    }

    public String data() {
        //请求头
        head = new HashMap<>();
        // 添加Cookie
        head.put("cookie", UserUtil.getUserCookie(context));
        // 添加请求码
        head.put("req_code","2");
        // 添加版本号
        try {
            head.put("app_ver", AppUtil.getVersionName(context));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 添加报文编号
        head.put("pack_no",packetNo);
        // 添加报文版本号
        head.put("pack_ver","1.0");
        data = new HashMap<>();
        data.put("head",head);
        data.put("data",body);
        return new Gson().toJson(data);
    }



    public static class Builder {
        private Context context;
        private String baseUrl;
        private String url;
        private String packetNo;
        private Map<String, Object> body;

        public Builder(Context context) {
            this.body = new HashMap<>();
            this.context = context;
        }

        public Builder body(Map<String, Object> body) {
            this.body = body;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder packetNo(String packetNo) {
            this.packetNo = packetNo;
            return this;
        }


        public BaseRequest build() {
            return new BaseRequest(this);
        }

    }

}
