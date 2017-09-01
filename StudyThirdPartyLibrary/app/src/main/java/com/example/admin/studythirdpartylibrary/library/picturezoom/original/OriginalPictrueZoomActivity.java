package com.example.admin.studythirdpartylibrary.library.picturezoom.original;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.studythirdpartylibrary.R;

import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/8/21.
 */

public class OriginalPictrueZoomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_original_pictrue_zoom);
        ButterKnife.bind(this);
    }
}
