package com.example.admin.studythirdpartylibrary.library.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.studythirdpartylibrary.CommentInformation;
import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.adapter.TeachingVideoAdapter;
import com.example.admin.studythirdpartylibrary.custom.ScrollViewListView;
import com.example.admin.studythirdpartylibrary.entity.TeachingVideoData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/8/10.
 */

public class EventBusActivity extends AppCompatActivity {

    @BindView(R.id.buttonSimple)
    Button mButtonSimple;
    @BindView(R.id.buttonSpecific)
    Button mButtonSpecific;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.listview)
    ScrollViewListView mListView;
    private TeachingVideoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this);
        // 注册EventBus
        EventBus.getDefault().register(this);
        onClickListener();
        adapter = new TeachingVideoAdapter(this, null);
        mListView.setAdapter(adapter);
    }

    private void onClickListener() {
        mButtonSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到简单使用示例页面，点击里面的按钮发送消息，当再次返回这个页面的时候执行onMessageEvent()方法
                startActivity(new Intent(EventBusActivity.this, SimpleExampleActivity.class));
            }
        });

        mButtonSpecific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发送网络请求获取数据猎豹并显示
                CommentInformation.getTeachingVideoDatasFromNetWork(new CommentInformation.SimpleCallBack() {
                    @Override
                    public void onFailure(IOException e) {

                    }

                    @Override
                    public void onSuccess(List<TeachingVideoData> data, int code) {
                        adapter.notifyDataSetChanged(data);
                    }

                    @Override
                    public void onError(String message, int code) {

                    }
                });
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到具体使用示例页面，点击里面的按钮进行点赞，再次返回此页面的时候执行onPressedEvent()方法
                startActivity(new Intent(EventBusActivity.this, SpecificExampleActivity.class)
                        .putExtra("position", position));
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(EventBusActivity.this, event.getMessage(), Toast.LENGTH_SHORT).show();
        mContent.setText(event.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPressedEvent(RefreshEvent event) {
        int position = event.getPosition();
        boolean isLike = event.isLike();
        if (position != -1 && isLike == true) {
            List<TeachingVideoData> datas = adapter.getDatas();
            datas.get(position).setLike(true);
            adapter.notifyDataSetChanged(datas);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销EventBus
        EventBus.getDefault().unregister(this);
    }


}
