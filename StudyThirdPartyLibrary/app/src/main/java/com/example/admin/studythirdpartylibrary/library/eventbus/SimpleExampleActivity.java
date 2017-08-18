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
 * Created by xpf on 2017/8/10.
 */

public class SimpleExampleActivity extends AppCompatActivity {
    @BindView(R.id.button)
    Button mButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_example_eventbus);
        ButterKnife.bind(this);

        onClickListener();
    }

    private void onClickListener(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发送消息
                EventBus.getDefault().post(new MessageEvent("Hello World!"));
            }
        });
    }
}
