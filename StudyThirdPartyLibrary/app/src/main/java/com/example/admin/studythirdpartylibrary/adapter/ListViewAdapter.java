package com.example.admin.studythirdpartylibrary.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.admin.studythirdpartylibrary.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by xpf on 2017/8/28.
 */

public class ListViewAdapter extends BaseSwipeAdapter {
    public static final int DELETE = 1;
    public static final int MAKETOP = 2;
    public static final int COLLECTION=3;

    @IntDef({DELETE,MAKETOP,COLLECTION})
    @Retention(RetentionPolicy.SOURCE)
    public @interface function{}


    private Context mContext;
    private LayoutInflater mInflater;
    private OnFunctionClickListener mOnFunctionClickListener;

    public ListViewAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipelayout;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_liet_view_example, parent, false);

        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(getSwipeLayoutResourceId(position));
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        // listview.setOnItemClickListener()
        //        swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                Toast.makeText(mContext, "getSurfaceView", Toast.LENGTH_SHORT).show();
        //            }
        //        });
        swipeLayout.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnFunctionClickListener != null) {
                    mOnFunctionClickListener.onClick(v,DELETE,position);
                }
            }
        });
        swipeLayout.findViewById(R.id.maketop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnFunctionClickListener != null) {
                    mOnFunctionClickListener.onClick(v,MAKETOP,position);
                }
            }
        });
        swipeLayout.findViewById(R.id.collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnFunctionClickListener != null) {
                    mOnFunctionClickListener.onClick(v,COLLECTION,position);
                }
            }
        });

        return view;
    }

    @Override
    public void fillValues(int position, View convertView) {
        TextView textView = (TextView) convertView.findViewById(R.id.position);
        textView.setText("");
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setOnFunctionClickListener(OnFunctionClickListener onFunctionClickListener) {
        this.mOnFunctionClickListener = onFunctionClickListener;
    }

    public interface OnFunctionClickListener {
        void onClick(View view, @function int function,int position);
    }
}
