package com.example.admin.studythirdpartylibrary.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.uitl.HttpUtil;
import com.example.admin.studythirdpartylibrary.uitl.SizeUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xpf on 2017/7/10.
 */

public class PictruesAdapter extends BaseAdapter {
    private static final String TAG = "PictruesAdapter";
    private List<String> mPictruesUrl;
    private Context mContext;
    private HttpUtil mHttpUtil;

    public PictruesAdapter(Context context, List<String> pictruesUrl) {
        this.mContext = context;
        if (pictruesUrl != null) {
            this.mPictruesUrl = pictruesUrl;
        } else {
            this.mPictruesUrl = new ArrayList<String>();
        }
        mHttpUtil = new HttpUtil();
    }

    public void notifyDataSetChanged(List<String> pictruesUrl) {
        if (pictruesUrl != null) {
            this.mPictruesUrl = pictruesUrl;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mPictruesUrl.size();
    }

    @Override
    public Object getItem(int position) {
        return mPictruesUrl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_imgs, null);
            holder.mIvPictures = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.mIvPictures.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = (int) (SizeUtil.getScreenWidth(mContext) /
                1.8);
        holder.mIvPictures.setLayoutParams(lp);
        loadingImage(mPictruesUrl.get(position), holder.mIvPictures);
        return convertView;
    }

    class Holder {
        public ImageView mIvPictures;
    }

    public void loadingImage(final String url, final ImageView imageView) {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = mHttpUtil.getBitmapFromURLOrCache(url);
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
                        imageView.setImageBitmap(bitmap);
                    }
                });


    }


}
