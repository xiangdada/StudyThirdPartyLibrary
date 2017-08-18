package com.example.admin.studythirdpartylibrary.library.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.admin.studythirdpartylibrary.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/8/11.
 */

public class SpecificExampleActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button mButton;

    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_example);
        ButterKnife.bind(this);
        onClickListener();
        position = getIntent().getIntExtra("position",-1);
    }

    private void onClickListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发送消息
                EventBus.getDefault().post(new RefreshEvent(position,true));
            }
        });
    }

}
