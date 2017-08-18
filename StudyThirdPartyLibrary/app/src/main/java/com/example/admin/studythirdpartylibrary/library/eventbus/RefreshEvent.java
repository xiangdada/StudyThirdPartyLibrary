package com.example.admin.studythirdpartylibrary.library.eventbus;

/**
 * Created by xpf on 2017/8/11.
 */

public class RefreshEvent {

    private int position;

    private boolean isLike;

    public RefreshEvent(int position, boolean isLike) {
        this.position = position;
        this.isLike = isLike;
    }

    public int getPosition() {
        return position;
    }

    public boolean isLike() {
        return isLike;
    }
}
