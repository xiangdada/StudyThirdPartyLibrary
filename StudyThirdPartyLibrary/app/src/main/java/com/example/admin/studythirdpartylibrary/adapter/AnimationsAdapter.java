package com.example.admin.studythirdpartylibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.daimajia.androidanimations.library.Techniques;
import com.example.admin.studythirdpartylibrary.uitl.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xpf on 2017/8/30.
 */

public class AnimationsAdapter extends BaseAdapter {
    private Context mContext;
    private List<Techniques> mDatas;
    private LayoutInflater mInflater;

    public AnimationsAdapter(Context context, List<Techniques> datas) {
        mContext = context;
        if(datas != null) {
            mDatas = datas;
        }else {
            mDatas  =new ArrayList<Techniques>();
        }
        mInflater = LayoutInflater.from(context);
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
        Holder holder = null;
      if(convertView != null) {
        holder = (Holder) convertView.getTag();
      }  else {
          holder = new Holder();
          convertView = mInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
          holder.mTextView = (TextView) convertView.findViewById(android.R.id.text1);
          convertView.setTag(holder);
      }
        BaseViewAnimator baseViewAnimator = mDatas.get(position).getAnimator();
        String name = baseViewAnimator.getClass().getSimpleName();
        LogUtil.i("测试",name);
        holder.mTextView.setText(name);
        return convertView;
    }

    class Holder {
        public TextView mTextView;
    }
}
