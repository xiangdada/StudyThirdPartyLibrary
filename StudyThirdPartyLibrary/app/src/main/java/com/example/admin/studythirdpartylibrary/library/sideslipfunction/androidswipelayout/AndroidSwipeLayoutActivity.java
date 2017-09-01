package com.example.admin.studythirdpartylibrary.library.sideslipfunction.androidswipelayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.example.admin.studythirdpartylibrary.R;
import com.nineoldandroids.view.ViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/8/22.
 */

public class AndroidSwipeLayoutActivity extends AppCompatActivity {
    @BindView(R.id.btn_listview)
    Button mBtnListView;
    @BindView(R.id.btn_recyclerview)
    Button mBtnRecyclerView;
    @BindView(R.id.example_textview)
    SwipeLayout mSlExampleTextView;
    @BindView(R.id.flag)
    TextView mTvFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_swipe_layout);
        ButterKnife.bind(this);

        onClickListener();
        exampleTextView();
    }

    private void onClickListener() {
        mBtnListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AndroidSwipeLayoutActivity.this,ListViewExampleActivity.class));
            }
        });
        mBtnRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void exampleTextView() {
        // PullOut功能区域是连接在内容区域后面抽拉出来的，LayDown功能区域是固定在屏幕边缘不动的滑动只对内容区域操作
        mSlExampleTextView.setShowMode(SwipeLayout.ShowMode.PullOut);
        mSlExampleTextView.addDrag(SwipeLayout.DragEdge.Left,
                mSlExampleTextView.findViewById(R.id.left));
        mSlExampleTextView.addDrag(SwipeLayout.DragEdge.Top,
                mSlExampleTextView.findViewById(R.id.top));
        mSlExampleTextView.addDrag(SwipeLayout.DragEdge.Right,
                mSlExampleTextView.findViewById(R.id.right));
        mSlExampleTextView.addDrag(SwipeLayout.DragEdge.Bottom,
                mSlExampleTextView.findViewById(R.id.bottom));
        int[] childIds = new int[]{R.id.flag, R.id.like, R.id.delete, R.id.maketop, R.id.collection, R.id.functionbottom, R.id.functiontop};
        /**
         * 功能区域当前显示比例正在变化项的监听
         */
        mSlExampleTextView.addRevealListener(childIds,
                new SwipeLayout.OnRevealListener() {
                    /**
                     *
                     * @param child 滑动过程中最新现实的功能区域视图
                     * @param edge  功能区域所在方位(left、top、right、bottom)
                     * @param fraction  当前child所显示出来的区域比例
                     * @param distance  当前child所显示出来的区域数值
                     */
                    @Override
                    public void onReveal(View child, SwipeLayout.DragEdge edge, float fraction, int distance) {
                        /**
                         * 此处传入的都是每个功能id，如果传入的是包裹功能的区域id(left、top、right、bottom)
                         * 则需要通过这个方法获取功能id：child.findViewById(int id).getId();
                         *
                         */

                        int id = child.getId();
                        if (R.id.functionbottom == id) {
                            View star = child.findViewById(R.id.starbottom);
                            float d = child.getHeight() / 2 - star.getHeight() / 2;
                            ViewHelper.setTranslationY(star, d * (fraction - 1));
                            ViewHelper.setScaleX(star, fraction + 0.6f);
                            ViewHelper.setScaleY(star, fraction + 0.6f);
                        }

                    }
                });
        mSlExampleTextView.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AndroidSwipeLayoutActivity.this, "内容区域", Toast.LENGTH_SHORT).show();
            }
        });
        mSlExampleTextView.findViewById(R.id.like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AndroidSwipeLayoutActivity.this, "LIKE", Toast.LENGTH_SHORT).show();
            }
        });
        mTvFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AndroidSwipeLayoutActivity.this, "FLAG", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
