package com.example.admin.studythirdpartylibrary.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.entity.TeachingVideoData;
import com.example.admin.studythirdpartylibrary.uitl.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by xpf on 2017/7/7.
 */

public class TeachingVideoAdapter extends BaseAdapter {
    private static final String TAG = "TeachingVideoAdapter";
    private Context mContext;
    private List<TeachingVideoData> mDatas;
    private BaseAdapter mAdapter;
    private HttpUtil mHttpUtil;

    public TeachingVideoAdapter(Context context, List<TeachingVideoData> datas) {
        mContext = context;
        if (datas != null) {
            mDatas = datas;
        } else {
            mDatas = new ArrayList<TeachingVideoData>();
        }
        mAdapter = this;
        mHttpUtil = new HttpUtil();
    }

    public void notifyDataSetChanged(@NonNull List<TeachingVideoData> datas) {
        if(datas != null) {
            mDatas = datas;
            notifyDataSetChanged();
        }
    }

    public List<TeachingVideoData> getDatas() {
        return mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TeachingVideoViewHolder holder = null;
        if (convertView != null) {
            holder = (TeachingVideoViewHolder) convertView.getTag();
        } else {
            holder = new TeachingVideoViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_mooc, null);
            holder.mIvPicSmall = (ImageView) convertView.findViewById(R.id.picSmall);
            holder.mTvName = (TextView) convertView.findViewById(R.id.name);
            holder.mTvDecription = (TextView) convertView.findViewById(R.id.description);
            holder.mIvIslikes = (ImageView) convertView.findViewById(R.id.islikes);
            holder.mTvLeaener = (TextView) convertView.findViewById(R.id.learner);
            convertView.setTag(holder);
        }
        TeachingVideoData data = mDatas.get(position);
        imageLoading(data.getPicSmall(),holder.mIvPicSmall);
        holder.mTvName.setText(data.getName());
        holder.mTvDecription.setText(data.getDescription());
        if(data.isLike()) {
            holder.mIvIslikes.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.islike_pressed));
        } else {
            holder.mIvIslikes.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.islike_unpressed));
        }
        holder.mTvLeaener.setText(data.getLearner());
        return convertView;
    }

    class TeachingVideoViewHolder {
        private ImageView mIvPicSmall;
        private TextView mTvName;
        private TextView mTvDecription;
        private ImageView mIvIslikes;
        private TextView mTvLeaener;
    }

    private void imageLoading(final String url, final ImageView imageView) {
        Observable.create(new Observable.OnSubscribe<Bitmap>() {    // 被观察者
            @Override
            public void call(final Subscriber<? super Bitmap> subscriber) {
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
