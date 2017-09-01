package com.example.admin.studythirdpartylibrary.library.androidviewanimations;

import android.animation.Animator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.adapter.AnimationsAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/8/30.
 */

public class OtherExampleAnimationsActivity extends AppCompatActivity {
    @BindView(R.id.animation)
    TextView mAnimation;
    @BindView(R.id.pause)
    TextView mPause;
    @BindView(R.id.listview)
    ListView mListView;
    private AnimationsAdapter mAdapter;
    private YoYo.YoYoString rope;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_example_animations);
        ButterKnife.bind(this);

        List<Techniques> datas = new ArrayList<Techniques>();
        Techniques[] techniques = Techniques.values();
        datas = Arrays.asList(techniques);
        mAdapter = new AnimationsAdapter(this, datas);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (rope != null) {
                    rope.stop(true);
                }
                Techniques technique = (Techniques) parent.getAdapter().getItem(position);
                rope = YoYo.with(technique)
                        .duration(1200)
                        .repeat(YoYo.INFINITE)
                        .pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT)
                        .interpolate(new AccelerateDecelerateInterpolator())
                        .withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        })
                        .playOn(mAnimation);
            }
        });

        onClickListener();

    }

    private void onClickListener() {
        mPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rope != null) {
                    rope.stop(true);
                }
            }
        });
    }

}
