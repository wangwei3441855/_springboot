package com.example.demo.mapper.system;

import com.example.demo.domain.SysRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysRoleMapper {
    List<SysRole> getRolesByUser(Map<String, Object> map);
}
