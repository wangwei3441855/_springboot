package com.example.demo.configure.websocket.domain;

import java.util.Date;

public class ClientMessage {
    private  String message;
    private Date time;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
