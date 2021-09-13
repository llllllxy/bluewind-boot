package com.bluewind.boot.sys.sysitfcpermission.service;

import com.bluewind.boot.sys.sysitfcpermission.entity.SysItfcPermission;
import com.bluewind.boot.sys.sysitfcpermission.mapper.SysItfcPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-07-09-22:02
 **/
@Service
public class SysItfcPermissionServiceImpl implements SysItfcPermissionService {

    @Autowired
    private SysItfcPermissionMapper sysItfcPermissionMapper;

    public List<SysItfcPermission> list(String permissionId) {
        return sysItfcPermissionMapper.list(permissionId);
    }


    public int enable(String permissionId) {
        return sysItfcPermissionMapper.enable(permissionId);
    }

    public int delete(String permissionId) {
        int num1 = sysItfcPermissionMapper.delete(permissionId);
        int num2 = sysItfcPermissionMapper.deleteChildren(permissionId);
        return num1 + num2;
    }

    public List<Map> listTree() {
        Map<String, Object> resultMap = new HashMap<>();
        return sysItfcPermissionMapper.listTree();
    }

    public int update(SysItfcPermission sysItfcPermission, boolean ifEdit) {
        int num = 0;
        if (ifEdit) {
            num = sysItfcPermissionMapper.update(sysItfcPermission);
        } else {
            num = sysItfcPermissionMapper.insert(sysItfcPermission);
        }

        return num;
    }

}
