package com.example.admin.studythirdpartylibrary.library.eventbus;

/**
 * Created by xpf on 2017/8/10.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
