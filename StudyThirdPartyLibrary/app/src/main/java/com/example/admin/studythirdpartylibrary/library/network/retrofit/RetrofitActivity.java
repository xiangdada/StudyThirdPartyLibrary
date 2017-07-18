package com.example.admin.studythirdpartylibrary.library.network.retrofit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.adapter.PictruesAdapter;
import com.example.admin.studythirdpartylibrary.adapter.TeachingVideoAdapter;
import com.example.admin.studythirdpartylibrary.entity.Array;
import com.example.admin.studythirdpartylibrary.entity.PictruesData;
import com.example.admin.studythirdpartylibrary.entity.TeachingVideoData;
import com.example.admin.studythirdpartylibrary.entity.ThanksWallData;
import com.example.admin.studythirdpartylibrary.library.network.retrofit.encapsulation.ApiService;
import com.example.admin.studythirdpartylibrary.library.network.retrofit.encapsulation.OnResponseCallBack;
import com.example.admin.studythirdpartylibrary.uitl.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xpf on 2017/7/7.
 * <p>
 * 涉及到两个Result类，一个是retrofit自带的，一个是在此包下自定义的类；
 * <p>
 * 本篇内容大部分学习自文章@link{http://www.jianshu.com/p/308f3c54abdd}
 */
public class RetrofitActivity extends AppCompatActivity {
    public static final String TAG = "RetrofitActivity";

    //    public static String url = "http://www.imooc.com/api/teacher?type=4&num=30";

    public static final String BASEURL = "http://www.imooc.com/";
    public static final String URL = "api/teache1";

    //    public static final String BASEURL = "http://120.27.37.132:9002/TrigMCISP-cdjsxy/";
    //    public static final String URL = "appService/appPerson_appService.t";

    @BindView(R.id.listView)
    ListView mListView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);

        //        UseResponseBody.run(this, mListView);
        //        UseCustomEntity.run(this, mListView);
        //        UseListTeachingVideoData.run(this, mListView);
        //        UseListTeachingVideoDataPerfectReceipt.run(this, mListView);
        //        UseCustomConverter.run(this, mListView);
        test();
        //        UseCustomCallAdapter.run(this, mListView);
    }


    /**
     * retrofit默认只将返回的数据转换成ResponseBody
     * 使用retrofit默认的ResponseBody去接受返回数据
     */
    static class UseResponseBody {
        /**
         * 步骤1：创建的业务请求接口
         * 拼接之后得到的完整的请求地址是：BASEURL + "api/teacher" + "?" + "type=type" + "&" + "num=num"
         *
         * @GET 请求
         * @Query 请求参数
         */
        public interface TeachingVideoService {
            /**
             * Retrofit接口返回的值分为两部分，分别为Call部分和ResponseBody部分
             * addConverterFactory可以改变call部分的值
             * addCallAdapterFactory可以改变ResponseBody部分的值
             */
            @GET("api/teacher")
            Call<ResponseBody> getCourse(@Query("type") int type, @Query("num") int num);
        }

        public static List<TeachingVideoData> run(Context context, ListView listView) {
            final TeachingVideoAdapter adapter = new TeachingVideoAdapter(context, null);
            listView.setAdapter(adapter);
            final List<TeachingVideoData> datas = new ArrayList<TeachingVideoData>();

            // 步骤2：需要创建一个Retrofit的实例
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // 步骤3：通过Retrofit的实例得到一个业务请求接口对象
            TeachingVideoService service = retrofit.create(TeachingVideoService.class);
            // 步骤4：调用请求方法并且得到Call实例
            Call<ResponseBody> call = service.getCourse(4, 30);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        /**
                         * response.body().string()只能有效的调用一次；
                         * 因为内容可能会很大OkHttp没有把他储存在内存中；
                         * 当需要使用的时候去读取一次内容而不是给一个引用；
                         * 所以一次网络请求只能成功的读取一次这个内容；
                         */
                        String str = response.body().string();
                        JSONObject responseObject = new JSONObject(str);
                        if (responseObject != null) {
                            JSONArray responseArray = responseObject.optJSONArray("data");
                            if (responseArray != null && responseArray.length() > 0) {
                                TeachingVideoData data;
                                for (int i = 0; i < responseArray.length(); i++) {
                                    JSONObject object = responseArray.optJSONObject(i);
                                    data = new TeachingVideoData(
                                            object.optString("id"),
                                            object.optString("name"),
                                            object.optString("picSmall"),
                                            object.optString("picBig"),
                                            object.optString("description"),
                                            object.optString("learner"));
                                    datas.add(data);
                                }
                                adapter.notifyDataSetChanged(datas);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t.toString());
                }
            });

            return datas;
        }

    }


    /**
     * 既然定义了泛型那么也可以使用其他类型来接受返回数据
     * 使用自定义的实体类型去接受返回数据方便对数据的解析
     */
    static class UseCustomEntity {
        /**
         * 步骤1：创建的业务请求接口
         * 拼接之后得到的完整的请求地址是：BASEURL + "api/teacher" + "?" + "type=type" + "&" + "num=num"
         *
         * @GET 请求
         * @Query 请求参数
         */
        public interface TeachingVideoService {
            @GET("api/teacher")
            Call<Result<PictruesData>> getCourse(@Query("type") int type);
        }

        public static void run(Context context, ListView listView) {
            final PictruesAdapter adapter = new PictruesAdapter(context, null);
            listView.setAdapter(adapter);
            final List<TeachingVideoData> datas = new ArrayList<TeachingVideoData>();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd hh:mm:ss")
                    .create();

            // 步骤2：需要创建一个Retrofit的实例
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            // 步骤3：通过Retrofit的实例得到一个业务请求接口对象
            TeachingVideoService service = retrofit.create(TeachingVideoService.class);

            // 步骤4：调用请求方法并且得到Call实例
            Call<Result<PictruesData>> call = service.getCourse(1);
            call.enqueue(new Callback<Result<PictruesData>>() {
                @Override
                public void onResponse(Call<Result<PictruesData>> call, Response<Result<PictruesData>> response) {
                    Result<PictruesData> result = response.body();
                    Log.i(TAG, "onResponse: " + result.toString());
                    PictruesData pictruesData = result.data;
                    if (pictruesData != null) {
                        List<String> pictrues = new ArrayList<String>();
                        pictrues.add(pictruesData.getPic1());
                        pictrues.add(pictruesData.getPic2());
                        pictrues.add(pictruesData.getPic3());
                        adapter.notifyDataSetChanged(pictrues);
                    }
                }

                @Override
                public void onFailure(Call<Result<PictruesData>> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t.toString());
                }
            });

        }
    }


    /**
     * 结合rxjava将返回的数据转换成Observable<Result<List<T>>>对象
     * 这样当返回的数据是列表时就可以直接将返回的数据解析成一个数组了
     */
    static class UseListTeachingVideoData {
        /**
         * 步骤1：创建的业务请求接口
         * 拼接之后得到的完整的请求地址是：BASEURL + "api/teacher" + "?" + "type=type" + "&" + "num=num"
         *
         * @GET 请求
         * @Query 请求参数
         */
        public interface TeachingVideoService {
            @GET("api/teacher")
            Observable<Result<List<TeachingVideoData>>> getTeachingVideo(@Query("type") int type, @Query("num") int num);
        }

        public static void run(Context context, ListView listView) {
            final TeachingVideoAdapter adapter = new TeachingVideoAdapter(context, null);
            listView.setAdapter(adapter);

            // 步骤2：需要创建一个Retrofit的实例
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    // 针对rxjava2.x
                    //            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            // 步骤3：通过Retrofit的实例得到一个业务请求接口对象
            TeachingVideoService service = retrofit.create(TeachingVideoService.class);

            // 步骤4：调用请求方法并且得到Call实例
            Observable<Result<List<TeachingVideoData>>> observable = service.getTeachingVideo(4, 30);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Result<List<TeachingVideoData>>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, e.toString());
                        }

                        @Override
                        public void onNext(Result<List<TeachingVideoData>> listResult) {
                            List<TeachingVideoData> datas = listResult.data;
                            adapter.notifyDataSetChanged(datas);
                        }
                    });

        }
    }


    /**
     * 使用retrofit自带的Result<T>类来接受返回的数据并将自定义的Array对象作为泛型参数传入
     * 优化UseListTeachingVideoData类中的方法所不能获取的一些网络请求回执数据具体可见方法类的打印部分
     */
    static class UseListTeachingVideoDataPerfectReceipt {

        /**
         * 步骤1：创建的业务请求接口
         * 拼接之后得到的完整的请求地址是：BASEURL + "api/teacher" + "?" + "type=type" + "&" + "num=num"
         *
         * @GET 请求
         * @Query 请求参数
         */
        public interface TeachingVideoService {
            @GET
            Observable<retrofit2.adapter.rxjava.Result<Array<TeachingVideoData>>> getTeachingVideo(@Url String url, @Query("type") int type, @Query("num") int num);
        }

        public static void run(Context context, ListView listView) {

            final TeachingVideoAdapter adapter = new TeachingVideoAdapter(context, null);
            listView.setAdapter(adapter);

            // 步骤2：需要创建一个Retrofit的实例
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    // 针对rxjava2.x
                    //            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            // 步骤3：通过Retrofit的实例得到一个业务请求接口对象
            TeachingVideoService service = retrofit.create(TeachingVideoService.class);

            // 步骤4：调用请求方法并且得到Call实例
            Observable<retrofit2.adapter.rxjava.Result<Array<TeachingVideoData>>> observable = service.getTeachingVideo("api/teacherl", 4, 30);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<retrofit2.adapter.rxjava.Result<Array<TeachingVideoData>>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, "onError: " + e.toString());
                        }

                        @Override
                        public void onNext(retrofit2.adapter.rxjava.Result<Array<TeachingVideoData>> arrayResult) {

                            Log.i(TAG, "headers: " + arrayResult.response().headers() + "\n"
                                    + "code: " + arrayResult.response().code() + "\n"
                                    + "raw: " + arrayResult.response().raw() + "\n"
                                    + "message: " + arrayResult.response().message() + "\n"
                                    + "body: " + arrayResult.response().body() + "\n"
                                    + "isSuccessful: " + arrayResult.response().isSuccessful() + "\n"
                                    + "errorBody: " + arrayResult.response().errorBody() + "\n"
                                    + "toString: " + arrayResult.response().toString());
                            List<TeachingVideoData> datas = arrayResult.response().body().list;
                            adapter.notifyDataSetChanged(datas);
                        }
                    });

        }
    }


    /**
     * 使用自定义的Converter将返回的数据ResponseBody转换成指定的类型如String
     * 这样就无论返回的是JSONObjetc还是JSONArray都可以转变成String再对数据进行解析
     */
    static class UseCustomConverter {

        /**
         * 自定义Converter实现ResponseBody到String的转换
         */
        public static class StringConverter implements Converter<ResponseBody, String> {
            public static final StringConverter instance = new StringConverter();

            @Override
            public String convert(ResponseBody value) throws IOException {
                return value.string();
            }
        }

        /**
         * 用于向Retrofit提供StringConverter
         */
        public static class StringConverterFactory extends Converter.Factory {
            public static final StringConverterFactory instance = new StringConverterFactory();

            public static StringConverterFactory create() {
                return instance;
            }

            // 只关注ResponseBody到String的转换，其他的方法可以不覆盖
            @Nullable
            @Override
            public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                if (type == String.class) {
                    return StringConverter.instance;
                }
                // 其他类型我们不处理，返回null就行
                return null;
            }
        }

        /**
         * 步骤1：创建的业务请求接口
         * 拼接之后得到的完整的请求地址是：BASEURL + "api/teacher" + "?" + "type=type" + "&" + "num=num"
         *
         * @GET 请求
         * @Query 请求参数
         */
        public interface TeachingVideoService {
            @GET(URL)
            Call<String> getTeachingVideo(@Query("type") int type, @Query("num") int num);
        }

        public static void run(Context context, ListView listView) {
            final TeachingVideoAdapter adapter = new TeachingVideoAdapter(context, null);
            listView.setAdapter(adapter);

            // 步骤2：需要创建一个Retrofit的实例
            /**
             * 注：addConverterFactory是有先后顺序的，
             * 如果有多个ConverterFactory支持同一种类型，
             * 那么就只有第一个才会被调用，其他的则不会继续调用，
             * 但是GsonConverterFactory是不判断是否支持的，
             * 所以此处如果将两个addConverterFactory交换一下顺序的话，
             * 会有一个异常抛出，原因是类型不匹配；
             */
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(StringConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // 步骤3：通过Retrofit的实例得到一个业务请求接口对象
            TeachingVideoService service = retrofit.create(TeachingVideoService.class);

            // 步骤4：调用请求方法并且得到Call实例
            Call<String> call = service.getTeachingVideo(4, 30);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (!response.isSuccessful()) {
                        LogUtil.i(TAG, "message： " + response.message());
                    } else {
                        String string = response.body();
                        LogUtil.i(TAG, "onResponse： " + string);
                        if (string != null) {
                            try {
                                JSONObject responseObject = new JSONObject(string);
                                if (responseObject != null) {
                                    JSONArray responseArray = responseObject.optJSONArray("data");
                                    if (responseArray != null) {
                                        if (responseArray.length() > 0) {
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    LogUtil.i(TAG, "onFailure: " + t.toString());
                }
            });
        }

    }


    /**
     * 使用自定义的CallAdapter将返回数据的原始类型Call转换成指定的类型
     * 这样就可以在指定的类型里面写一些自定义的的方法和一些自定义的属性
     */
    static class UseCustomCallAdapter {

        public static class CustomCall<R> {
            public final Call<R> call;

            public CustomCall(Call<R> call) {
                this.call = call;
            }

            // 提供一个同步获取数据的方法
            public R get() throws IOException {
                // 网络请求并且拿到数据
                return call.execute().body();
            }

        }

        public static class CustomCallAdapter implements CallAdapter<R, CustomCall<R>> {
            private final Type responseType;

            public CustomCallAdapter(Type responseType) {
                this.responseType = responseType;
            }

            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public CustomCall<R> adapt(Call<R> call) {
                // 由Custom决定如何使用
                return new CustomCall<>(call);
            }
        }

        public static class CustomCallAdapterFactory extends CallAdapter.Factory {
            public static final CustomCallAdapterFactory instance = new CustomCallAdapterFactory();

            public static CustomCallAdapterFactory create() {
                return instance;
            }


            @Nullable
            @Override
            public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
                // 获取原始类型
                Class<?> rawType = getRawType(returnType);
                // 返回值必须是CustomCall并且带有泛型
                if (rawType == CustomCall.class && returnType instanceof ParameterizedType) {
                    Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
                    return new CustomCallAdapter(callReturnType);
                }
                return null;
            }
        }

        /**
         * 步骤1：创建的业务请求接口
         * 拼接之后得到的完整的请求地址是：BASEURL + "api/teacher" + "?" + "type=type" + "&" + "num=num"
         *
         * @GET 请求
         * @Query 请求参数
         */
        public interface TeachingVideoService {
            @GET("api/teacher")
            CustomCall<String> getTeachingVideo(@Query("type") int type, @Query("num") int num);
        }

        public static void run(Context context, ListView listView) {
            final TeachingVideoAdapter adapter = new TeachingVideoAdapter(context, null);
            listView.setAdapter(adapter);

            // 步骤2：需要创建一个Retrofit的实例
            /**
             * 注：addCallAdapterFactor与addConverterFactory一样也是有先后顺序的
             */
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(UseCustomConverter.StringConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CustomCallAdapterFactory.create())
                    .build();

            // 步骤3：通过Retrofit的实例得到一个业务请求接口对象
            final TeachingVideoService service = retrofit.create(TeachingVideoService.class);

            // 步骤4：调用请求方法并且得到Call实例
            CustomCall<String> call = service.getTeachingVideo(4, 30);

            // 同步
            //            synchro(call);

            // 异步
            call.call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        final String string = response.body();
                        LogUtil.i(TAG, "onResponse: " + string);
                        // 按照api文档说明的onResponse是在主线程调用的，可是此处直接在此进行adapter动态刷新会抛出异常
                        notifyDataSetChanged(string, adapter);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    LogUtil.i(TAG, t.toString());
                }
            });

        }

        private static void synchro(final CustomCall<String> call) {
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(final Subscriber<? super String> subscriber) {
                    try {
                        // 调用CustomCall类中的get()方法进行同步的网络请求并且拿到返回数据
                        String result = call.get();
                        subscriber.onNext(result);
                        subscriber.onCompleted();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                            LogUtil.i(TAG, s);
                        }
                    });
        }

        private static void notifyDataSetChanged(final String string, final TeachingVideoAdapter adapter) {
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> subscriber) {
                    subscriber.onNext(string);
                    subscriber.onCompleted();
                }
            })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            try {
                                JSONObject responseObject = new JSONObject(s);
                                if (responseObject != null) {
                                    JSONArray responseArray = responseObject.optJSONArray("data");
                                    if (responseArray != null) {
                                        if (responseArray.length() > 0) {
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

    }


    /**
     * 对retrofit进行简单封装之后的使用示例
     * 目前只对返回的数据转换成String类型进行了简单的封装
     */
    public void test() {
        Map<String, Object> body = new HashMap<>();
        body.put("pageIndex", 1);
        body.put("pageNumber", 15);
        ApiService.test(this, body, new OnResponseCallBack<List<ThanksWallData>>(true) {
            @Override
            public void onSuccess(List<ThanksWallData> result) {
                if (result != null) {
                    for (ThanksWallData thanksWallData : result) {
                        LogUtil.i("测试", thanksWallData.getGratitudeContent());
                    }

                }

            }

            @Override
            public void onSuccess(String code, String message, JSONObject dataObject, JSONObject responseObject) {
                LogUtil.i(TAG, "onSuccess2--code: " + code);
                LogUtil.i(TAG, "onSuccess2--message: " + message);
                LogUtil.i(TAG, "onSuccess2--dataObject: " + dataObject.toString());
                LogUtil.i(TAG, "onSuccess2--responseObject: " + responseObject.toString());
            }

            @Override
            public void onError(String code, String message) {
                LogUtil.i(TAG, "onError--code: " + code);
                LogUtil.i(TAG, "onError--message: " + message);
            }

            @Override
            public void onFinish(boolean isSuccessful) {
                super.onFinish(isSuccessful);
            }

            @Override
            public void onFailure(Throwable t) {
                LogUtil.i(TAG, "onFailure: " + t.toString());
            }
        });
    }
}
