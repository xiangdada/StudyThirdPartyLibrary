package com.example.admin.studythirdpartylibrary.entity;

import java.io.Serializable;

/**
 * Created by xpf on 2017/7/7.
 */

public class TeachingVideoData implements Serializable {
    private String id;
    private String name;
    private String picSmall;
    private String picBig;
    private String description;
    private String learner;
    private boolean isLike;

    public TeachingVideoData(String id, String name, String picSmall, String picBig, String description, String learner) {
        this.id = id;
        this.name = name;
        this.picSmall = picSmall;
        this.picBig = picBig;
        this.description = description;
        this.learner = learner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicSmall() {
        return picSmall;
    }

    public void setPicSmall(String picSmall) {
        this.picSmall = picSmall;
    }

    public String getPicBig() {
        return picBig;
    }

    public void setPicBig(String picBig) {
        this.picBig = picBig;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLearner() {
        return learner;
    }

    public void setLearner(String learner) {
        this.learner = learner;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    @Override
    public String toString() {
        return "TeachingVideoData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", picSmall='" + picSmall + '\'' +
                ", picBig='" + picBig + '\'' +
                ", description='" + description + '\'' +
                ", learner='" + learner + '\'' +
                ", isLike=" + isLike +
                '}';
    }

}
