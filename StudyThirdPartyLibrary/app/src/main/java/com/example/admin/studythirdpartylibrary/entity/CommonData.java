package com.example.admin.studythirdpartylibrary.entity;

import android.app.Activity;

import java.io.Serializable;

/**
 * Created by xpf on 2017/6/28.
 */

public class CommonData implements Serializable {
    private String text;
    private Class<? extends Activity> flag;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Class<? extends Activity> getFlag() {
        return flag;
    }

    public void setFlag(Class<? extends Activity> flag) {
        this.flag = flag;
    }
}
