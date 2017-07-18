package com.example.admin.studythirdpartylibrary.library.network.retrofit.encapsulation;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.admin.studythirdpartylibrary.uitl.AESEncryptor;
import com.example.admin.studythirdpartylibrary.uitl.LogUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by xpf on 2017/7/12.
 */

public class NetUtil {
    public static final String TAG = ApiService.class.getSimpleName();
    public static final Map<Object, Call<String>> calls = new HashMap<>();
    // 是否进行加密
    private static final boolean isEncryption = true;

    /**
     * 自定义Converter将返回的数据由ResponseBody转换成String
     */
    public static class ResponseStringConverter implements Converter<ResponseBody, String> {
        public static final ResponseStringConverter instance = new ResponseStringConverter();

        @Override
        public String convert(ResponseBody value) throws IOException {
            return value.string();
        }
    }

    public static class StringConverterFactory extends Converter.Factory {
        public static final StringConverterFactory instance = new StringConverterFactory();

        public static StringConverterFactory create() {
            return instance;
        }

        // 如果只关注ResponseBody到String的转换，其他的方法可以不覆盖
        @Nullable
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            if (type == String.class) {
                return ResponseStringConverter.instance;
            }
            // 其他类型我们不处理，返回null就行
            return null;
        }
    }

    public interface RetrofitService {
        @GET
        Call<String> getService(@Url String url, @Query("requestData") String data);

        @FormUrlEncoded
        @POST
        Call<String> postService(@Url String url, @Field("requestData") String data);
    }



    public static void post(Context context, final BaseRequest request, final OnResponseCallBack responseCallBack) {
        LogUtil.i(TAG, "request--rows:" + request.data());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(request.baseUrl())
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        String data = request.data();
        if (isEncryption) {
            try {
                // 加密
                data = AESEncryptor.encrypt(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Call<String> call = service.postService(request.url(), data);

        calls.put(context, call);
        responseCallBack.onStart();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // 只有当response.isSuccessful() == false时response.errorBody()才不为Null
                boolean isSuccess = false;
                if (response.isSuccessful()) {
                    String string = response.body();
                    if (isEncryption) {
                        try {
                            // 解密
                            string = AESEncryptor.decrypt(string);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (string != null && !"".equals(string)) {
                        try {
                            JSONObject stateObject = new JSONObject(string);
                            JSONObject dataObject = stateObject.getJSONObject("data");
                            isSuccess = true;
                            responseCallBack.onSuccess(jsonToatargetBean(dataObject, responseCallBack.getType(), responseCallBack.isArray()));
                            responseCallBack.onSuccess(String.valueOf(response.code()), response.message(), dataObject, stateObject);
                            LogUtil.i(TAG, "response--rows: " + stateObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }finally {
                            responseCallBack.onFinish(isSuccess);
                        }
                    }
                } else {
                    responseCallBack.onError();
                    responseCallBack.onError(String.valueOf(response.code()), response.message());
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseCallBack.onFailure(t);
            }
        });
    }

    public static void get(Context context, final BaseRequest request, final OnResponseCallBack responseCallBack) {
        LogUtil.i(TAG, "request--rows:" + request.data());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(request.baseUrl())
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitService service = retrofit.create(RetrofitService.class);

        String data = request.data();
        if (isEncryption) {
            try {
                // 加密
                data = AESEncryptor.encrypt(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Call<String> call = service.getService(request.url(), data);

        calls.put(context, call);
        responseCallBack.onStart();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // 只有当response.isSuccessful() == false时response.errorBody()才不为Null
                boolean isSuccess = false;
                if (response.isSuccessful()) {
                    String string = response.body();
                    if (isEncryption) {
                        try {
                            // 解密
                            string = AESEncryptor.decrypt(string);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (string != null && !"".equals(string)) {
                        try {
                            JSONObject stateObject = new JSONObject(string);
                            JSONObject dataObject = stateObject.getJSONObject("data");
                            isSuccess = true;
                            responseCallBack.onSuccess(jsonToatargetBean(dataObject, responseCallBack.getType(), responseCallBack.isArray()));
                            responseCallBack.onSuccess(String.valueOf(response.code()), response.message(), dataObject, stateObject);
                            LogUtil.i(TAG, "response--rows: " + stateObject.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }finally {
                            responseCallBack.onFinish(isSuccess);
                        }
                    }
                } else {
                    responseCallBack.onError();
                    responseCallBack.onError(String.valueOf(response.code()), response.message());
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseCallBack.onFailure(t);
            }
        });
    }


    private static Object jsonToatargetBean(JSONObject dataObject, Type type, boolean isArray) {
        Gson gson = new Gson();
        Object model = null;
        if (isArray) {
            JSONArray array = dataObject.optJSONArray("rows");
            model = gson.fromJson(array.toString(), type);
        } else {
            JSONArray array = dataObject.optJSONArray("rows");
            model = gson.fromJson(array.optJSONObject(0).toString(), type);
        }
        return model;
    }

    public static void cancel(Object tag) {
        if (calls.get(tag) != null) {
            calls.get(tag).cancel();
        }
    }


}
