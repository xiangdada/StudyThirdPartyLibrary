package com.example.admin.studythirdpartylibrary.library.androidslidinguppanel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.studythirdpartylibrary.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/8/30.
 */

public class AndroidSlidingUpPanelActivity extends AppCompatActivity {
    @BindView(R.id.listview)
    ListView mListView;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout mSlidingLayout;
    @BindView(R.id.follow)
    ImageView mFollow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_slidingup_panel);
        ButterKnife.bind(this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AndroidSlidingUpPanelActivity.this, "onItemClick", Toast.LENGTH_SHORT).show();
            }
        });

        List<String> your_array_list = Arrays.asList(
                "This",
                "Is",
                "An",
                "Example",
                "ListView",
                "That",
                "You",
                "Can",
                "Scroll",
                ".",
                "It",
                "Shows",
                "How",
                "Any",
                "Scrollable",
                "View",
                "Can",
                "Be",
                "Included",
                "As",
                "A",
                "Child",
                "Of",
                "SlidingUpPanelLayout"
        );

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list);

        mListView.setAdapter(arrayAdapter);

        // 面板滑动状态的监听
        mSlidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                Log.i("测试", "onPanelSlide, offset " + slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                Log.i("测试", "onPanelStateChanged " + newState);

                if(newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    mFollow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.btn_expandn_normal));
                } else if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    mFollow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.btn_collapse_normal));
                }

            }
        });

        // 点击面板外部区域监听
        mSlidingLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

    }
}
