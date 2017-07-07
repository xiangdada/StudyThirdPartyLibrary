package com.example.admin.studythirdpartylibrary.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.admin.studythirdpartylibrary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/7/7.
 */

public class DescriptionActivity extends AppCompatActivity {
    @BindView(R.id.listView)
    ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        ButterKnife.bind(this);
    }
}
