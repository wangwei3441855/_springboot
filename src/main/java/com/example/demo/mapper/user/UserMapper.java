package com.example.demo.mapper.user;

import com.example.demo.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper
{
    Integer addUser(SysUser user);

    SysUser getUserByName(String userName);

    List<SysUser> getList(Map<String, Object> map);

    SysUser getUser(Map<String, Object> map);
}
