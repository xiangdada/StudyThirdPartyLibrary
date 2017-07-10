package com.example.admin.studythirdpartylibrary.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xpf on 2017/7/10.
 */

public class PictruesData {
    @SerializedName("pic1")
    private String pic1;
    @SerializedName("pic2")
    private String pic2;
    @SerializedName("pic3")
    private String pic3;

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    @Override
    public String toString() {
        return "PictruesData{" +
                "pic1='" + pic1 + '\'' +
                ", pic2='" + pic2 + '\'' +
                ", pic3='" + pic3 + '\'' +
                '}';
    }
}
