package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangwei on 2017/6/19.
 */
public class SysUser {
    private String userId;

    private String userName;

    private String password;

    private int status;

    private String descn;

    private List<SysRole> roles = new ArrayList<SysRole>();
    private List<SysRsources> rsources = new ArrayList<SysRsources>();


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescn() {
        return descn;
    }

    public void setDescn(String descn) {
        this.descn = descn;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public List<SysRsources> getRsources() {
        return rsources;
    }

    public void setRsources(List<SysRsources> rsources) {
        this.rsources = rsources;
    }
}
