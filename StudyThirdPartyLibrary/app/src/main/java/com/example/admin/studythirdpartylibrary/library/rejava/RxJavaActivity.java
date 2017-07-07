package com.example.admin.studythirdpartylibrary.library.rejava;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.uitl.HttpUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.example.admin.studythirdpartylibrary.R.id.print;

/**
 * Created by xpf on 2017/6/28.
 */

public class RxJavaActivity extends AppCompatActivity {
    public static final String TAG = "RxJavaActivity";

    @BindView(print)
    Button mBtnPrint;
    @BindView(R.id.display)
    Button mBtnDisplay;
    @BindView(R.id.networkRequest)
    Button mBtnNetworkRequest;
    @BindView(R.id.optimization)
    Button mBtnOptimization;
    @BindView(R.id.imageView)
    ImageView mImageView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.textView)
    TextView mTextView;

    private String string = "";

    private HttpUtil mHttpUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);
        mHttpUtil = new HttpUtil();
        print();
        display();
        networkRequest();
        optimization();

    }

    /**
     * 一次打印出字符串数据
     */
    public void print() {
        // 观察者
        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted!");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError!");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "Item: " + s);
            }
        };

        // 被观察者
        final Observable observable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("hello");
                        subscriber.onNext("hi");
                        subscriber.onNext("Aloha");
                        subscriber.onCompleted();
                    }
                }
        );

        mBtnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                observable.subscribe(observer);

            }
        });
    }

    /**
     * 由指定的一个 drawable 文件 id drawableRes 取得图片，
     * 并显示在 ImageView 中，并在出现异常的时候打印 Toast 报错
     */
    public void display() {
        final int drawableRes = R.mipmap.ic_launcher;
        mBtnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new Observable.OnSubscribe<Drawable>() {
                    @Override
                    public void call(Subscriber<? super Drawable> subscriber) {
                        Drawable drawable = getResources().getDrawable(drawableRes);
                        subscriber.onNext(drawable);
                        subscriber.onCompleted();
                    }
                })
                        //                        .subscribeOn(Schedulers.io())   // 指定subscribe()发生在IO线程
                        //                        .observeOn(AndroidSchedulers.mainThread())  // 指定Subscriber的回调发生在主线程
                        .subscribe(new Observer<Drawable>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(RxJavaActivity.this, "onError!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(Drawable drawable) {
                                mImageView.setImageDrawable(drawable);
                            }
                        });
            }
        });
    }

    /**
     * 加载网络图片
     */
    private void networkRequest() {
        mBtnNetworkRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new Observable.OnSubscribe<Bitmap>() {    // 被观察者
                    @Override
                    public void call(final Subscriber<? super Bitmap> subscriber) {
                        Bitmap bitmap = mHttpUtil.getBitmapFromURLOrCache("http://att.bbs.duowan.com/forum/201304/18/0932088bi83i3si8s8gdbb.jpg"
                                , new HttpUtil.LoadingProgressListener() {
                                    @Override
                                    public void onLoading(float progress) {
                                        progress(progress);
                                    }
                                });
                        subscriber.onNext(bitmap);
                        subscriber.onCompleted();
                    }
                })
                        .subscribeOn(Schedulers.io())   // 指定subscribe()发生在IO线程
                        .observeOn(AndroidSchedulers.mainThread())  // 指定Subscriber的回调发生在主线程
                        .subscribe(new Observer<Bitmap>() { // 观察者订阅被观察者
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, e.toString());
                            }

                            @Override
                            public void onNext(Bitmap bitmap) {
                                mImageView.setImageBitmap(bitmap);
                            }
                        });
            }
        });
    }


    /**
     * 跳转到一个新页面使用WebView加载一个网页并对加载出错是的页面进行优化
     */
    private void optimization() {
        mBtnOptimization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RxJavaActivity.this, OptimizationActivity.class));
            }
        });
    }

    /**
     * 显示出图片加载的进度
     *
     * @param progress
     */
    private void progress(final float progress) {

        Observable.create(new Observable.OnSubscribe<Float>() {
            @Override
            public void call(Subscriber<? super Float> subscriber) {
                Float f = new Float(progress);
                subscriber.onNext(f);
                subscriber.onCompleted();
            }
        }).map(new Func1<Float, Integer>() {    // 将被观察者返回的Float对象转换成Integer对象再返回

            @Override
            public Integer call(Float aFloat) {
                return new Integer((int) aFloat.floatValue());
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (mProgressBar.getVisibility() == View.GONE) {
                            mProgressBar.setVisibility(View.VISIBLE);
                        }
                        if (mTextView.getVisibility() == View.GONE) {
                            mTextView.setVisibility(View.VISIBLE);
                        }
                        mProgressBar.setProgress(integer.intValue());
                        mTextView.setText(integer.intValue() + "%");
                        if (integer.intValue() == 100) {
                            mProgressBar.setVisibility(View.GONE);
                            mTextView.setVisibility(View.GONE);
                        }
                    }
                });
    }


}
