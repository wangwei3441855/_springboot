package com.example.demo.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/")
public class SysController {

    private Logger log = LoggerFactory.getLogger(SysController.class);

    @Autowired
    protected HttpServletRequest request;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
    @RequestMapping("/index2")
    public String index2() {
        return "index2";
    }

    @RequestMapping("/login")
    public String login(Map<String, Object> map) throws Exception {
        String exception = (String) request.getAttribute("shiroLoginFailure");
        log.error("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                log.error("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                log.error("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                log.error("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> " + exception;
                log.error("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        return "login";
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        log.error("------没有权限-------");
        return "403";
    }
    @RequestMapping("/404")
    public String pageNotFound() {
        log.error("------没找到页面-------");
        return "404";
    }
}
