package com.example.admin.studythirdpartylibrary.library.network;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.adapter.CommonAdapter;
import com.example.admin.studythirdpartylibrary.entity.CommonData;
import com.example.admin.studythirdpartylibrary.library.network.okhttp.OkhttpActivity;
import com.example.admin.studythirdpartylibrary.library.network.retrofit.RetrofitActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/7/7.
 */

public class NetWorkActivity extends AppCompatActivity {
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
                startActivity(new Intent(NetWorkActivity.this, commonData.getFlag()));
            }
        });
    }

    private void datas() {
        datas = new ArrayList<CommonData>();
        datas.add(commonData("retrofit", RetrofitActivity.class));
        datas.add(commonData("okhttp", OkhttpActivity.class));

    }

    private CommonData commonData(String text, Class<? extends Activity> flag) {
        CommonData commonData = new CommonData();
        commonData.setText(text);
        commonData.setFlag(flag);
        return commonData;
    }
}
