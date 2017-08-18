package com.example.admin.studythirdpartylibrary;

import com.example.admin.studythirdpartylibrary.entity.TeachingVideoData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xpf on 2017/8/11.
 */

public class CommentInformation {
    public static final String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

//    private SimpleCallBack mSimpleCallBack;

    public interface SimpleCallBack {
        void onFailure(IOException e);
        void onSuccess(List<TeachingVideoData> data,int code);
        void onError(String message,int code);
    }

    public static void getTeachingVideoDatasFromNetWork(final SimpleCallBack simpleCallBack) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(URL)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(simpleCallBack != null) {
                    simpleCallBack.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()) {
                    String responseString = response.body().string();
                    final List<TeachingVideoData> datas = new ArrayList<TeachingVideoData>();
                    final int code = response.code();
                    if (responseString != null && !"".equals(responseString)) {
                        JSONObject responseObject = null;
                        try {
                            responseObject = new JSONObject(responseString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (responseObject != null) {
                            JSONArray responseArray = responseObject.optJSONArray("data");
                            if (responseArray != null && responseArray.length() > 0) {
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
                            }
                        }
                    }

                    Observable.create(new Observable.OnSubscribe<List<TeachingVideoData>>() {
                        @Override
                        public void call(Subscriber<? super List<TeachingVideoData>> subscriber) {
                            subscriber.onNext(datas);
                            subscriber.onCompleted();
                        }
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<List<TeachingVideoData>>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(List<TeachingVideoData> teachingVideoDatas) {
                                    simpleCallBack.onSuccess(teachingVideoDatas,code);
                                }
                            });



                } else {
                    simpleCallBack.onError(response.message(),response.code());
                }
            }
        });
    }
}
