package com.example.demo.service.user;

import com.example.demo.domain.SysRole;
import com.example.demo.domain.SysUser;
import com.example.demo.domain.SysRsources;
import com.example.demo.mapper.system.SysRoleMapper;
import com.example.demo.mapper.system.SysRsourcesMapper;
import com.example.demo.mapper.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRsourcesMapper sysRsourcesMapper;

    @Override
    @Transactional
    public boolean addUser(SysUser user) {
        Integer b = userMapper.addUser(user);
        return b.intValue() > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public SysUser getUserByName(String userName) {
        SysUser user = userMapper.getUserByName(userName);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SysUser> getList(Map<String, Object> map) {
        return userMapper.getList(map);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUser getUserRoleRes(Map<String, Object> map) {
        SysUser user = userMapper.getUser(map);
        List<SysRole> rolesByUser = sysRoleMapper.getRolesByUser(map);
        if (rolesByUser != null) {
            user.setRoles(rolesByUser);
            List<SysRsources> rsourcesByRole = sysRsourcesMapper.getRsourcesByRole(rolesByUser);
            if (rsourcesByRole != null) {
                user.setRsources(rsourcesByRole);
            }
        }
        return user;
    }
}
