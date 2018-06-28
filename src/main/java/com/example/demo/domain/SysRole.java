package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

public class SysRole {
    private String roleId;
    private String roleName;
    private String desc;
    private List<SysRsources> rsources = new ArrayList<SysRsources>();

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public List<SysRsources> getRsources() {
        return rsources;
    }

    public void setRsources(List<SysRsources> rsources) {
        this.rsources = rsources;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
