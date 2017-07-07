package com.example.admin.studythirdpartylibrary.uitl;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.LruCache;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xpf on 2017/7/5.
 */

public class HttpUtil {
    // 这个缓存在app被退出后就自动清理
    private LruCache<String, Bitmap> mCache;
    public LoadingProgressListener mLoadingProgressListener;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public HttpUtil() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 在每次存入缓存时调用
                return value.getByteCount();
            }
        };
    }

    /**
     * 网络图片加载进度监听接口
     */
    public interface LoadingProgressListener {
        void onLoading(float progress);
    }

    /**
     * 网络图片加载进度监听方法
     *
     * @param loadingProgressListener
     */
    public void setOnLoadingProgressListener(LoadingProgressListener loadingProgressListener) {
        this.mLoadingProgressListener = loadingProgressListener;
    }

    /**
     * 从网络图片链接中获取图片的Bitmap
     *
     * @param string
     * @return
     */
    public static Bitmap getBitmapFromURL(String string) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(8000);
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 从网络图片链接中获取图片的Bitmap
     *
     * @param string
     * @return
     */
    public Bitmap getBitmapFromURL(String string, LoadingProgressListener loadingProgressListener) {
        this.mLoadingProgressListener = loadingProgressListener;
        Bitmap bitmap = null;
        try {
            URL url = new URL(string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(8000);
            connection.setRequestMethod("GET");
            int contentLength = connection.getContentLength();
            if (connection.getResponseCode() == 200) {
                InputStream is = connection.getInputStream();
                final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                int sum = 0;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                    sum += len;
                    float percent = (100 * ((float) sum / (float) contentLength));
                    if (mLoadingProgressListener != null) {
                        mLoadingProgressListener.onLoading(percent);
                    }
                }
                baos.close();
                byte[] data = baos.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 从网络图片链接中或者从缓存中获取图片的Bitmap
     * 如果图片在缓存中则从缓存中获取，否则从网页链接中加载图片获取
     *
     * @param string
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public Bitmap getBitmapFromURLOrCache(final String string) {
        Bitmap bitmap = null;
        if (getBitmapFormCache(string) != null) {
            bitmap = getBitmapFormCache(string);
        } else {
            bitmap = getBitmapFromURL(string);
            if (bitmap != null) {
                addBitmapToCache(string, bitmap);
            }
        }
        return bitmap;
    }


    /**
     * 从网络图片链接中或者从缓存中获取图片的Bitmap
     * 如果图片在缓存中则从缓存中获取，否则从网页链接中加载图片获取
     *
     * @param string
     * @param loadingProgressListener
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public Bitmap getBitmapFromURLOrCache(final String string, LoadingProgressListener loadingProgressListener) {
        Bitmap bitmap = null;
        if (getBitmapFormCache(string) != null) {
            bitmap = getBitmapFormCache(string);
        } else {
            bitmap = getBitmapFromURL(string, loadingProgressListener);
            if (bitmap != null) {
                addBitmapToCache(string, bitmap);
            }
        }
        return bitmap;
    }

    /**
     * 将图片Bitmap以键值的方式保存到缓存
     *
     * @param url
     * @param bitmap
     */
    public void addBitmapToCache(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }

    /**
     * 从缓存之中获取图片Bitmap
     *
     * @param url
     * @return
     */
    public Bitmap getBitmapFormCache(String url) {
        return mCache.get(url);
    }

}
