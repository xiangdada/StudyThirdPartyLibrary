package com.example.admin.studythirdpartylibrary.library.network.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.adapter.TeachingVideoAdapter;
import com.example.admin.studythirdpartylibrary.entity.TeachingVideoData;

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
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xpf on 2017/7/7.
 */

public class RetrofitActivity extends AppCompatActivity {
    public static final String TAG = "RetrofitActivity";

    public static String URL = "http://www.imooc.com/api/teacher?type=4&num=30";

    public static final String BASEURL = "http://www.imooc.com/";

    @BindView(R.id.listView)
    ListView mListView;
    private TeachingVideoAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);

        mAdapter = new TeachingVideoAdapter(this, null);
        mListView.setAdapter(mAdapter);

//        UseResponseBody.run(mAdapter);
        UseListTeachingVideo.run(mAdapter);

    }


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

        public static List<TeachingVideoData> run(final TeachingVideoAdapter adapter) {
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

    static class UseListTeachingVideo {
        /**
         * 步骤1：创建的业务请求接口
         * 拼接之后得到的完整的请求地址是：BASEURL + "api/teacher" + "?" + "type=type" + "&" + "num=num"
         *
         * @GET 请求
         * @Query 请求参数
         */
        public interface TeachingVideoService {
            @GET("api/teacher")
            Call<List<TeachingVideoData>> getCourse(@Query("type") int type, @Query("num") int num,Callback<List<TeachingVideoData>> callback);
        }

        public static List<TeachingVideoData> run(final TeachingVideoAdapter adapter) {
            final List<TeachingVideoData> datas = new ArrayList<TeachingVideoData>();

            // 步骤2：需要创建一个Retrofit的实例
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                                        .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // 步骤3：通过Retrofit的实例得到一个业务请求接口对象
            TeachingVideoService service = retrofit.create(TeachingVideoService.class);
            // 步骤4：调用请求方法并且得到Call实例
            service.getCourse(4, 3, new Callback<List<TeachingVideoData>>() {
                @Override
                public void onResponse(Call<List<TeachingVideoData>> call, Response<List<TeachingVideoData>> response) {
                    List<TeachingVideoData> datas = response.body();
                    if (datas != null) {
                        Log.i("测试", "size: " + datas.size());
                    }
                }

                @Override
                public void onFailure(Call<List<TeachingVideoData>> call, Throwable t) {
                    Log.i(TAG, "onFailure: " + t.toString());
                }
            });

            return datas;
        }
    }

}
