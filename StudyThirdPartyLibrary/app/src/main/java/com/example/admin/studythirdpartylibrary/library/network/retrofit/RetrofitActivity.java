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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xpf on 2017/7/7.
 *
 * 涉及到两个Result类，一个是retrofit自带的，一个是在此包下自定义的类；
 */
public class RetrofitActivity extends AppCompatActivity {
    public static final String TAG = "RetrofitActivity";

    public static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

    public static final String BASEURL = "http://www.imooc.com/";

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
        UseListTeachingVideoDataPerfectReceipt.run(this, mListView);
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
            Call<ResponseBody> call = service.getCourse(4, 3);
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
            Observable<Result<List<TeachingVideoData>>> observable = service.getTeachingVideo(4, 3);
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
            @GET("api/teacher")
            Observable<retrofit2.adapter.rxjava.Result<Array<TeachingVideoData>>> getTeachingVideo(@Query("type") int type, @Query("num") int num);
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
            Observable<retrofit2.adapter.rxjava.Result<Array<TeachingVideoData>>> observable = service.getTeachingVideo(4, 3);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<retrofit2.adapter.rxjava.Result<Array<TeachingVideoData>>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i(TAG, e.toString());
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

}
