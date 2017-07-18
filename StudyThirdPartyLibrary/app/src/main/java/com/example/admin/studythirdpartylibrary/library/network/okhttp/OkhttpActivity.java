package com.example.admin.studythirdpartylibrary.library.network.okhttp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.adapter.TeachingVideoAdapter;
import com.example.admin.studythirdpartylibrary.entity.TeachingVideoData;
import com.example.admin.studythirdpartylibrary.uitl.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xpf on 2017/7/7.
 */

public class OkhttpActivity extends AppCompatActivity {
    public static final String TAG = "OkhttpActivity";

    public static String fullLink = "http://www.imooc.com/api/teacher?type=4&num=30";

    public static final String BASEURL = "http://www.imooc.com/api/teacher";

    //    public static final String BASEURL = "http://120.27.37.132:9002/TrigMCISP-cdjsxy/";
    //    public static final String URL = "appService/appPerson_appService.t";

    @BindView(R.id.listView)
    ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);

        //        synchroGet(this, mListView);
        synchroPost(this, mListView);

    }

    /**
     * 同步的Get请求
     *
     * @param context
     * @param listView
     */
    public static void synchroGet(Context context, ListView listView) {
        final TeachingVideoAdapter adapter = new TeachingVideoAdapter(context, null);
        listView.setAdapter(adapter);
        Map<String, Object> params = new HashMap<>();
        params.put("type", 4);
        params.put("num", 30);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(BASEURL);
        int position = 0;
        for (String key : params.keySet()) {
            if (position == 0) {
                stringBuffer.append("?").append(key).append("=").append(params.get(key));
            } else {
                stringBuffer.append("&").append(key).append("=").append(params.get(key));
            }
            position++;
        }
        String url = stringBuffer.toString();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        LogUtil.i(TAG, "request: " + call.request().toString());
        if (call.request().body() != null) {
            LogUtil.i(TAG, "body: " + call.request().body().toString());
        } else {
            LogUtil.i(TAG, "body is null");
        }
        LogUtil.i(TAG, "url: " + call.request().url());

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Response response = null;
                String string = "";
                try {
                    response = call.execute();
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(string);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        if (s != null && !"".equals(s)) {
                            JSONObject responseObject = null;
                            try {
                                responseObject = new JSONObject(s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (responseObject != null) {
                                JSONArray responseArray = responseObject.optJSONArray("data");
                                if (responseArray != null && responseArray.length() > 0) {
                                    List<TeachingVideoData> datas = new ArrayList<TeachingVideoData>();
                                    for (int i = 0; i < responseArray.length(); i++) {
                                        JSONObject jsonObject = responseArray.optJSONObject(i);
                                        datas.add(new TeachingVideoData(
                                                jsonObject.optString("name"),
                                                jsonObject.optString("name"),
                                                jsonObject.optString("picSmall"),
                                                jsonObject.optString("picBig"),
                                                jsonObject.optString("description"),
                                                jsonObject.optString("learner")));
                                    }
                                    adapter.notifyDataSetChanged(datas);
                                }
                            }
                        }
                    }
                });


    }

    /**
     * 同步的Post请求
     *
     * @param context
     * @param listView
     */
    public void synchroPost(Context context, ListView listView) {
        final TeachingVideoAdapter adapter = new TeachingVideoAdapter(context, null);
        listView.setAdapter(adapter);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .build();

        RequestBody requestBody = new FormBody.Builder()
                .add("type", "4")
                .add("num","30")
                .build();

        Request request = new Request.Builder()
                .url("http://www.imooc.com/api/teacher")
                .post(requestBody)
                .build();

        final Call call = okHttpClient.newCall(request);

        LogUtil.i(TAG, "call: " + call.toString());
        LogUtil.i(TAG, "request: " + call.request().toString());
        if (call.request().body() != null) {
            LogUtil.i(TAG, "body: " + call.request().body().toString());
        } else {
            LogUtil.i(TAG, "body is null");
        }
        LogUtil.i(TAG, "url: " + call.request().url());

        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Response response = null;
                String string = "";
                try {
                    response = call.execute();
                    string = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(string);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        LogUtil.i(TAG, "s: " + s);

                        if (s != null && !"".equals(s)) {
                            JSONObject responseObject = null;
                            try {
                                responseObject = new JSONObject(s);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (responseObject != null) {
                                JSONArray responseArray = responseObject.optJSONArray("data");
                                if (responseArray != null && responseArray.length() > 0) {
                                    List<TeachingVideoData> datas = new ArrayList<TeachingVideoData>();
                                    for (int i = 0; i < responseArray.length(); i++) {
                                        JSONObject jsonObject = responseArray.optJSONObject(i);
                                        datas.add(new TeachingVideoData(
                                                jsonObject.optString("name"),
                                                jsonObject.optString("name"),
                                                jsonObject.optString("picSmall"),
                                                jsonObject.optString("picBig"),
                                                jsonObject.optString("description"),
                                                jsonObject.optString("learner")));
                                    }
                                    adapter.notifyDataSetChanged(datas);
                                }
                            }
                        }
                    }
                });

    }


    public void asynchronousGet(Context context, ListView listView) {

    }


    public class LogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(chain.request());
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            String method = request.method();
            if ("POST".equals(method)) {
                StringBuffer sb = new StringBuffer();
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "="
                            + body.encodedValue(i) + ",");
                }
                LogUtil.i(TAG, "FormBody: " + body.toString());
                LogUtil.i(TAG, "requestParams: " + sb.toString());
            }

            return response.newBuilder()
                    .body(ResponseBody.create(mediaType, content))
                    .build();
        }
    }



    /*Map<String, Object> body = new HashMap<>();
        body.put("pageIndex", 1);
        body.put("pageNumber", 15);
    BaseRequest.Builder builder = new BaseRequest.Builder(this);
        builder.baseUrl("http://120.27.37.132:9002/TrigMCISP-cdjsxy/")
                .url("appService/appLife_appService.t")
                .body(body)
                .packetNo("90030037");
    BaseRequest baseRequest = builder.build();
    String data = baseRequest.data();
        LogUtil.i(TAG, "data: " + data);
            try {
        // 加密
        data = AESEncryptor.encrypt(data);
    } catch (Exception e) {
        e.printStackTrace();
    }*/


}
