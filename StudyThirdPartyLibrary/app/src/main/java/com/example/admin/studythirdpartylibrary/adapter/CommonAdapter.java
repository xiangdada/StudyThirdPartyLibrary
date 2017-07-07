package com.example.admin.studythirdpartylibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.entity.CommonData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xpf on 2017/6/28.
 */

public class CommonAdapter extends BaseAdapter {
    private Context mContext;
    private List<CommonData> mDatas;
    private BaseAdapter mAdapter;

    public CommonAdapter(Context context, List<CommonData> datas) {
        mContext = context;
        if (datas != null) {
            mDatas = datas;
        } else {
            mDatas = new ArrayList<CommonData>();
        }
        mAdapter = this;
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
        CommonViewHolder holder = null;
        if (convertView == null) {
            holder = new CommonViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_common, null);
            holder.mTextView = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        } else {
            holder = (CommonViewHolder) convertView.getTag();
        }
        CommonData commonData = mDatas.get(position);
        holder.mTextView.setText(commonData.getText());

        return convertView;
    }

    class CommonViewHolder {
        public TextView mTextView;
    }
}
