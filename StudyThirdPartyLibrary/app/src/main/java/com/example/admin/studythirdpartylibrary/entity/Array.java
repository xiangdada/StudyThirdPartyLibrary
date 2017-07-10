package com.example.admin.studythirdpartylibrary.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xpf on 2017/7/10.
 */

public class Array<T> {
    @SerializedName("data")
    public List<T> list;

}
