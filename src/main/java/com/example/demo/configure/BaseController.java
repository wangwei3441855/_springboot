package com.example.demo.configure;

import com.example.demo.configure.websocket.domain.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class BaseController extends BaseComponent {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    protected Map<String, Object> success(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj != null) {
            map.put("data", obj);
        }
        map.put("status", 200);
        return map;
    }

    protected Map<String, Object> fail(Throwable cause) {
        Map<String, Object> map = new HashMap<String, Object>();
        String errorMsg = "system fail";
        if (cause instanceof BusinessException) {
            map.put("errorCode", ((BusinessException) cause).getErrorCode());
            errorMsg = cause.getMessage();
        }
        map.put("success", false);
        map.put("status", 500);
        return map;
    }

    /**
     * websocket推送消息到用户
     * @param user 用户
     * @param message 服务端的消息
     */
    protected void sendMessageToUser(String user, ServerMessage message) {

        if (StringUtils.isEmpty(user)) {
            throw new BusinessException("send messages fail:  user is empty");
        }
        if (message == null) {
            throw new BusinessException("send messages fail:  message is empty");
        }
        messagingTemplate.convertAndSendToUser(user,
                "/queue/chat", message);
    }

}
