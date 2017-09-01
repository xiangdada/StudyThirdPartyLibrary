package com.example.admin.studythirdpartylibrary.entity;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by xpf on 2017/8/21.
 */

public class ImageUrlData {

    public static final int SMALL = 0;

    public static final int BIG = 1;

    @IntDef({SMALL, BIG})

    @Retention(RetentionPolicy.SOURCE)

    public @interface SizeType {

    }

    private String url;

    private int SizeType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSizeType() {
        return SizeType;
    }

    public void setSizeType(@SizeType int sizeType) {
        SizeType = sizeType;
    }
}
