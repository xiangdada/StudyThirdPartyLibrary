package com.example.admin.studythirdpartylibrary.library.androidviewanimations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.admin.studythirdpartylibrary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2017/8/29.
 */

public class AndroidViewAnimationsActivity extends AppCompatActivity {
    @BindView(R.id.password)
    EditText mPassWord;
    @BindView(R.id.accountnumber)
    EditText mAccountNumber;
    @BindView(R.id.submit)
    Button mSubmit;
    @BindView(R.id.otherexample)
    TextView mOthereExample;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_androidview_animations);
        ButterKnife.bind(this);

        onClickListener();
    }

    private void onClickListener() {
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountnumber = mAccountNumber.getText().toString().trim();
                String password = mPassWord.getText().toString().trim();
                if(!"123456789".equals(accountnumber)) {
                    YoYo.with(Techniques.Shake)
                            .duration(1000)
                            .playOn(mAccountNumber);
                }

                if(!"123456789".equals(password)) {
                    YoYo.with(Techniques.Shake)
                            .duration(1000)
                            .playOn(mPassWord);
                }
            }
        });

        mOthereExample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AndroidViewAnimationsActivity.this,OtherExampleAnimationsActivity.class));
            }
        });
    }
}
