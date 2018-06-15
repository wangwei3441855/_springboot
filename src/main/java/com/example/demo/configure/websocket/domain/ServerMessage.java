package com.example.demo.configure.websocket.domain;

import java.util.Date;

public class ServerMessage {
    private String message;

    private Long time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
