package com.github.evan.common_utils.event;

/**
 * Created by Evan on 2018/9/23.
 */

public class BaseMessageEvent {
    private String message;

    public BaseMessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
