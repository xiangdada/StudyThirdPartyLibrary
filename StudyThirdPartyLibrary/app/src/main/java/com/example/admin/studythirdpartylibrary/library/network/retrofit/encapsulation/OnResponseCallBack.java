package com.example.admin.studythirdpartylibrary.library.network.retrofit.encapsulation;

import com.google.gson.internal.$Gson$Types;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by xpf on 2017/7/12.
 */

public class OnResponseCallBack<T> {

    private boolean isArray;

    private Type type;

    public OnResponseCallBack(boolean isArray) {
        this.isArray = isArray;
        type = getType(getClass());

    }

    /**
     * 获取泛型的数据类型
     *
     * @param subClass
     * @return
     */
    private Type getType(Class<?> subClass) {
        Type genericSuperclass = subClass.getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        return $Gson$Types.canonicalize(parameterizedType.getActualTypeArguments()[0]);
    }

    public void onStart() {
    }

    public void onSuccess(T result) {
    }

    public void onSuccess(String code, String message,
                          JSONObject dataObject,
                          JSONObject responseObject) {
    }

    /**
     * 只有当网络请求返回成功才会执行该回调，与onError回调相互排斥
     * 一般用于执行当json解析异常onSuccess没有成功回调时需要执行的操作
     *
     * @param isSuccess onSuccess是否成功回调
     */
    public void onFinish(boolean isSuccess) {

    }

    public void onError() {
    }

    public void onError(String code, String message) {
    }

    /**
     * 系统抛出的一些异常，比如检测到网络未连接，手动取消网络请求
     *
     * @param t
     */
    public void onFailure(Throwable t) {
        /*if(t.getMessage() != null && t.getMessage().startsWith("Failed to connect to")){
            Log.d("lincoln","当前网络情况不稳定");
        }*/
    }

    public boolean isArray() {
        return isArray;
    }

    public Type getType() {
        return type;
    }


}
