package com.example.admin.studythirdpartylibrary.library.rejava;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.example.admin.studythirdpartylibrary.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xpf on 2017/7/6.
 */

public class OptimizationActivity extends AppCompatActivity {
    public static final String TAG = "OptimizationActivity";
    public static final String URL = "http://www.baidu.com";
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.relativeLayout)
    RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimization);
        ButterKnife.bind(this);
        mWebView.loadUrl(URL);
        init();
    }

    private void init() {
        // 设置WebView属性，能够执行JavaScript脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 设置内容的字体
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // 大于android 4.0以上版本
            mWebView.getSettings().setTextZoom(100);
        } else {
            // 4.0以下版本
            mWebView.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        }
        // 隐藏WebView的滚动条
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        // 支持缩放
        mWebView.getSettings().setSupportZoom(false);
        // 支持缩放工具
        mWebView.getSettings().setBuiltInZoomControls(false);
        // 让网页自适应屏幕宽度
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 阻塞加载图片
        mWebView.getSettings().setBlockNetworkImage(false);
        // 设置任意比较缩放为假
        mWebView.getSettings().setUseWideViewPort(false);
        // 设置网页的缓存模式打开，并且可以通过setAppCachePath(String appCachePath)方法设置缓存路径
        mWebView.getSettings().setAppCacheEnabled(true);
        if (isNetWorkConnect(this)) {
            // 存在网络
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        } else {
            // 显示本地数据
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        // 设置编码
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        // 设置可以访问文件
        mWebView.getSettings().setAllowFileAccess(true);
        // 设置开启本地DOM存储
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启数据库
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                super.onPageFinished(view, url);
                if (mRelativeLayout.getVisibility() != View.VISIBLE) {
                    /**
                     * api<23的手机上捕获网络加载出错的方法是onReceivedError()，
                     * 但是此方法又存在一定的bug，有些错误无法捕获到，
                     * 所以此处通过getResponseState(String string)方法再次的请求网页，
                     * 目的是获取网页请求返回的状态码，通过对状态码的值来判断请求是否出错，
                     * 网络请求不能主线程中进行所以此处另起一个线程，
                     * 此方法可以无视api的版本，都可以捕获到请求是否出错，
                     * 但是并没有注释掉onReceivedError()和onReceivedHttpError()方法，
                     * 主要是有利于对官方的一些方法进行熟悉以及了解
                     */
                    Observable.create(new Observable.OnSubscribe<Integer>() {
                        @Override
                        public void call(Subscriber<? super Integer> subscriber) {
                            int state = getResponseState(url);
                            subscriber.onNext(new Integer(state));
                            subscriber.onCompleted();
                        }
                    })
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Integer>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.i(TAG, e.toString());
                                }

                                @Override
                                public void onNext(Integer integer) {
                                    int state = integer.intValue();
                                    if (state != 200) {
                                        mRelativeLayout.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                }

            }


            /**
             * 返回 true ，则说明由应用的代码处理该 url，WebView 不处理
             * 返回 false，则说明由 WebView 处理该 url，即用 WebView 加载该 url
             *
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, final String url) {
                view.loadUrl(url);
                return true;
            }

            /**
             * api<23当网页加载出错是会调用这个方法,
             * google官方人员表明此方法以及官方文档存在bug，
             * 只能捕获文件找不到、网络连不上、服务器找不到等问题，并不能捕获 http errors
             *
             * @param view
             * @param request
             * @param error
             */
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                // 这个方法里面的BUG好像有点多会捕获一些乱七八糟的错误，还是不要再这个方法里面操作比较好
                //                mRelativeLayout.setVisibility(View.VISIBLE);
            }

            /**
             * api>=23当网页加载出错是会调用这个方法，
             * 针对onReceivedError方法存在的bug而新写的一个方法，
             * 可以捕获到 http errors，但是在api低的手机上是不会执行的
             *
             * @param view
             * @param request
             * @param errorResponse
             */
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                mRelativeLayout.setVisibility(View.VISIBLE);
            }
        });

        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRelativeLayout.setVisibility(View.GONE);
                mWebView.loadUrl(URL);
            }
        });

    }

    /**
     * 获取到WebView请求网址返回的状态码
     *
     * @param string 请求的链接
     * @return 请求返回的状态码
     */
    private int getResponseState(String string) {
        int state = -1;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(string);
            if (url != null) {
                connection = (HttpURLConnection) url.openConnection();
            }
            if (connection != null) {
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                state = connection.getResponseCode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return state;
    }


    /**
     * 判断网络是否连接
     *
     * @param context 上下文对象
     * @return true 已连接上, false 未连接
     */
    public boolean isNetWorkConnect(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null;
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
