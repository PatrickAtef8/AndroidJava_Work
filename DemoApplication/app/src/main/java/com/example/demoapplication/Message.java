package com.example.demoapplication;

import androidx.annotation.NonNull;

public class Message {

    private final String mobile;
    private final String message;

    public Message(String mobile, String message) {
        this.mobile = mobile;
        this.message = message;
    }

    public String getMobile() {
        return mobile;
    }

    public String getMessage() {
        return message;
    }

    @NonNull
    @Override
    public String toString() {
        return message + " " + mobile;
    }
}
