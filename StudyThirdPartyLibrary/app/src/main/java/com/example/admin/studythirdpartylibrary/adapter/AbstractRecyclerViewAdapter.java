package com.example.admin.studythirdpartylibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xpf on 2017/8/21.
 * <p>
 * RecyclerView适配器的父类封装了一些常用的方法和接口
 * 子类的onBindViewHolder(RecyclerView.ViewHolder holder, int position)方法中必须调用super.onBindViewHolder(holder,position);
 * 子类的ViewHolder必须继承自AbstractViewHolder；
 */

public abstract class AbstractRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<T> mDatas;
    protected OnItemClickListener mOnItemClickListener;
    protected AbstractRecyclerViewAdapter mAdapter;

    public AbstractRecyclerViewAdapter(Context context, List<T> datas) {
        this.mContext = context;
        if (datas != null) {
            this.mDatas = datas;
        } else {
            this.mDatas = new ArrayList<T>();
        }
        this.mAdapter = getAdapter();
    }

    public abstract AbstractRecyclerViewAdapter getAdapter();

    /**
     * 动态刷新
     *
     * @param datas
     */
    public void notifyDataSetChanged(List<T> datas) {
        if (datas != null) {
            this.mDatas.clear();
            for (T data : datas) {
                mDatas.add(data);
            }
            notifyDataSetChanged();
        }
    }

    public Object getItem(int position) {
        return mDatas.get(position);
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public interface OnItemClickListener {
        void onItemClick(AbstractRecyclerViewAdapter adapter, View view, int position);

        void onItemLongClick(AbstractRecyclerViewAdapter adapter, View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AbstractViewHolder abstractViewHolder = (AbstractViewHolder) holder;

        abstractViewHolder.mRootView.setTag(position);

    }

    abstract class AbstractViewHolder extends RecyclerView.ViewHolder {
        public View mRootView;

        public AbstractViewHolder(View itemView) {
            super(itemView);
            mRootView = itemView.getRootView();
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(mAdapter, v, ((Integer) v.getTag()).intValue());
                    }
                }
            });
            mRootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemLongClick(mAdapter, v, ((Integer) v.getTag()).intValue());
                    }
                    return true;
                }
            });
        }
    }
}
