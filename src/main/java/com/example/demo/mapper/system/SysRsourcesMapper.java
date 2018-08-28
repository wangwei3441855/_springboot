package com.example.demo.mapper.system;

import com.example.demo.domain.SysRole;
import com.example.demo.domain.SysRsources;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRsourcesMapper {
    List<SysRsources> getRsourcesByRole(List<SysRole> roles);
    List<SysRsources> getAllRsources();
    List<SysRsources> getAllRsourcesRole();
}
