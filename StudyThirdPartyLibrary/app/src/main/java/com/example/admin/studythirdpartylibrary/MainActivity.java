package com.example.admin.studythirdpartylibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.studythirdpartylibrary.adapter.CommonAdapter;
import com.example.admin.studythirdpartylibrary.entity.CommonData;
import com.example.admin.studythirdpartylibrary.library.androidslidinguppanel.AndroidSlidingUpPanelActivity;
import com.example.admin.studythirdpartylibrary.library.androidviewanimations.AndroidViewAnimationsActivity;
import com.example.admin.studythirdpartylibrary.library.eventbus.EventBusActivity;
import com.example.admin.studythirdpartylibrary.library.ijkplayer.IjkplayerActivity;
import com.example.admin.studythirdpartylibrary.library.mpandroidchart.MPAndroidChartActivity;
import com.example.admin.studythirdpartylibrary.library.network.NetWorkActivity;
import com.example.admin.studythirdpartylibrary.library.picturezoom.PictureZoomActivity;
import com.example.admin.studythirdpartylibrary.library.rejava.RxJavaActivity;
import com.example.admin.studythirdpartylibrary.library.sideslipfunction.SideslipFunctionActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listview)
    ListView mListView;
    private CommonAdapter adapter;
    private List<CommonData> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        datas();
        adapter = new CommonAdapter(this, datas);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommonData commonData = (CommonData) parent.getAdapter().getItem(position);
                startActivity(new Intent(MainActivity.this, commonData.getFlag()));
            }
        });
    }

    private void datas() {
        datas = new ArrayList<CommonData>();
        datas.add(commonData("RxJava", RxJavaActivity.class));
        datas.add(commonData("网络编程", NetWorkActivity.class));
        datas.add(commonData("EventBus",EventBusActivity.class));
        datas.add(commonData("MPAndroidChart",MPAndroidChartActivity.class));
        datas.add(commonData("图片缩放",PictureZoomActivity.class));
        datas.add(commonData("列表侧滑", SideslipFunctionActivity.class));
        datas.add(commonData("视图动画",AndroidViewAnimationsActivity.class));
        datas.add(commonData("滑行面板",AndroidSlidingUpPanelActivity.class));
        datas.add(commonData("ijkplayer",IjkplayerActivity.class));

    }

    private CommonData commonData(String text, Class<? extends Activity> flag) {
        CommonData commonData = new CommonData();
        commonData.setText(text);
        commonData.setFlag(flag);
        return commonData;
    }
}
