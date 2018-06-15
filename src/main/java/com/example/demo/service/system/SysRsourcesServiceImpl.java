package com.example.demo.service.system;

import com.example.demo.domain.SysRole;
import com.example.demo.domain.SysRsources;
import com.example.demo.mapper.system.SysRsourcesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRsourcesServiceImpl implements SysRsourcesService {

    @Autowired
    private SysRsourcesMapper sysRsourcesMapper;

    @Override
    public List<SysRsources> getRsourcesByRole(List<SysRole> roles) {
        return null;
    }

    @Override
    public List<SysRsources> getAllRsources() {
        return sysRsourcesMapper.getAllRsources();
    }
}
