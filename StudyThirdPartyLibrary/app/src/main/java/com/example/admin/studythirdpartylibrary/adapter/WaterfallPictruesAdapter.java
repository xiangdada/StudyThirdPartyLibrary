package com.example.admin.studythirdpartylibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.entity.ImageUrlData;
import com.example.admin.studythirdpartylibrary.uitl.GlideUtil;
import com.example.admin.studythirdpartylibrary.uitl.SizeUtil;

import java.util.List;

/**
 * Created by xpf on 2017/8/21.
 */

public class WaterfallPictruesAdapter extends AbstractRecyclerViewAdapter<ImageUrlData> {
    private LayoutInflater mInflater;
    private int screenWidth;


    public WaterfallPictruesAdapter(Context context, List<ImageUrlData> datas) {
        super(context, datas);
        mInflater = LayoutInflater.from(context);
        screenWidth = SizeUtil.getScreenWidth(context);
    }

    @Override
    public AbstractRecyclerViewAdapter getAdapter() {
        return this;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotosViewHolder(mInflater.inflate(R.layout.item_waterfall_pictrues, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        PhotosViewHolder photosViewHolder = (PhotosViewHolder) holder;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) photosViewHolder.mImageView.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        if (ImageUrlData.SMALL == mDatas.get(position).getSizeType()) {
            lp.height = screenWidth / 3;
        } else if (ImageUrlData.BIG == mDatas.get(position).getSizeType()) {
            lp.height = (int) ((screenWidth / 3) * 1.5);
        }
        photosViewHolder.mImageView.setLayoutParams(lp);

        GlideUtil.setImage(mContext, mDatas.get(position).getUrl(), photosViewHolder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class PhotosViewHolder extends AbstractViewHolder {
        public ImageView mImageView;

        public PhotosViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }
}
