package com.example.admin.studythirdpartylibrary.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by xpf on 2017/8/11.
 */

public class ScrollViewListView extends ListView {
    public ScrollViewListView(Context context) {
        super(context);
    }

    public ScrollViewListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandMeasureSpec);
    }
}
