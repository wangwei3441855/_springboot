package com.example.demo.configure.websocket;

import com.example.demo.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * 获取用户信息，定向推送
 * 虽然STMOP的协议很好的实现了订阅，
 * 返回的模式，但是没法定向的从服务端推送消息个某个用户，
 * 要解决这个问题就需要获取用户的信息，使得我们可以对其推送。
 */
public class HandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        //shrio中获取当前登陆用户
        SysUser principal = (SysUser)SecurityUtils.getSubject().getPrincipal();
        return new Principal() {
            @Override
            public boolean equals(Object another) {
                return false;
            }

            @Override
            public String toString() {
                return null;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String getName() {
                return principal.getUserName();
            }
        };
    }
}
