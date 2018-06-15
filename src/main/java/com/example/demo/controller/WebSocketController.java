package com.example.demo.controller;

import com.example.demo.configure.BaseController;
import com.example.demo.configure.websocket.domain.ClientMessage;
import com.example.demo.configure.websocket.domain.ServerMessage;
import com.example.demo.domain.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class WebSocketController extends BaseController {

    private Logger log = LoggerFactory.getLogger(WebSocketController.class);

    @MessageMapping("/send")
    @SendTo("/topic/send")
    public ServerMessage send(ClientMessage message) {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setMessage(message.getMessage() + "_呵呵呵");
        return serverMessage;
    }

    @MessageMapping("/chat")
    public void chat(ClientMessage message, SysUser toUser) {
        ServerMessage msg = new ServerMessage();
        msg.setMessage(message.getMessage());
        msg.setTime(new Date().getTime());
        sendMessageToUser(toUser.getUserName(), msg);
    }

    @RequestMapping("/toMessage")
    public void toMessage(ClientMessage message, SysUser toUser) {
        ServerMessage msg = new ServerMessage();
        msg.setMessage(message.getMessage());
        msg.setTime(new Date().getTime());
        sendMessageToUser(toUser.getUserName(), msg);
    }
}
