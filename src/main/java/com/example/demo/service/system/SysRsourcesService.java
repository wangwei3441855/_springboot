package com.example.demo.service.system;

import com.example.demo.domain.SysRole;
import com.example.demo.domain.SysRsources;

import java.util.List;
import java.util.Map;

public interface SysRsourcesService {
    List<SysRsources> getRsourcesByRole(List<SysRole> roles);
    List<SysRsources> getAllRsources();
    Map<String, List<String>> initRsourcesRole();
}
