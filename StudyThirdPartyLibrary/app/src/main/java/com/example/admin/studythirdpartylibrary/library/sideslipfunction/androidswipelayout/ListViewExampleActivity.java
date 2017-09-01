package com.example.admin.studythirdpartylibrary.library.sideslipfunction.androidswipelayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.example.admin.studythirdpartylibrary.R;
import com.example.admin.studythirdpartylibrary.adapter.ListViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/8/28.
 */

public class ListViewExampleActivity extends AppCompatActivity {
    @BindView(R.id.listview)
    ListView mListView;
    private ListViewAdapter mAdapter;
    private Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_example);
        ButterKnife.bind(this);
        mContext = this;
        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mAdapter.setMode(Attributes.Mode.Single);
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "setOnItemClickListener", Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnFunctionClickListener(new ListViewAdapter.OnFunctionClickListener() {
            @Override
            public void onClick(View view, @ListViewAdapter.function int function, int position) {
                switch (function) {
                    case ListViewAdapter.DELETE:
                        Toast.makeText(mContext, "delete " + position, Toast.LENGTH_SHORT).show();
                        break;
                    case ListViewAdapter.MAKETOP:
                        Toast.makeText(mContext, "maketop " + position, Toast.LENGTH_SHORT).show();
                        break;
                    case ListViewAdapter.COLLECTION:
                        Toast.makeText(mContext, "collection " + position, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }
}
