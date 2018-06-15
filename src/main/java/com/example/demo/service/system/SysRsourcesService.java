package com.example.demo.service.system;

import com.example.demo.domain.SysRole;
import com.example.demo.domain.SysRsources;

import java.util.List;

public interface SysRsourcesService {
    List<SysRsources> getRsourcesByRole(List<SysRole> roles);
    List<SysRsources> getAllRsources();
}
