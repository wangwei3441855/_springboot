package com.example.demo.service.user;

import com.example.demo.domain.SysUser;

import java.util.List;
import java.util.Map;


public interface UserService
{
    boolean addUser(SysUser user);

    SysUser getUserByName(String userName);

    List<SysUser> getList(Map<String, Object> map);

    SysUser getUserRoleRes(Map<String, Object> map);
}
