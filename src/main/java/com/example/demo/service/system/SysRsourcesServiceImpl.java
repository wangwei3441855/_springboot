package com.example.demo.service.system;

import com.example.demo.domain.SysRole;
import com.example.demo.domain.SysRsources;
import com.example.demo.mapper.system.SysRsourcesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, List<String>> initRsourcesRole() {
        List<SysRsources> allRsourcesRole = sysRsourcesMapper.getAllRsourcesRole();
        Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
        if (allRsourcesRole != null && allRsourcesRole.size() != 0) {
            for (int i = 0; i < allRsourcesRole.size(); i++) {
                String resourceContent = allRsourcesRole.get(i).getResourceContent();
                String roleName = allRsourcesRole.get(i).getRoleName();

                if (map.containsKey(resourceContent)) {
                    List<String> list = map.get(resourceContent);
                    if(StringUtils.hasText(roleName)){
                        list.add(roleName);
                    }
                    map.put(resourceContent, list);
                } else {
                    List<String> list = new ArrayList<String>();
                    if(StringUtils.hasText(roleName)){
                        list.add(roleName);
                    }
                    map.put(resourceContent, list);
                }
            }
        }
        return map;
    }
}
