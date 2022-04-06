package com.bluewind.boot.module.system.itfcpermission.service;

import com.bluewind.boot.module.system.itfcpermission.entity.ItfcPermission;
import com.bluewind.boot.module.system.itfcpermission.mapper.ItfcPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-07-09-22:02
 **/
@Service
public class ItfcPermissionServiceImpl implements ItfcPermissionService {

    @Autowired
    private ItfcPermissionMapper itfcPermissionMapper;

    public List<ItfcPermission> list(String permissionId) {
        return itfcPermissionMapper.list(permissionId);
    }


    public int enable(String permissionId) {
        return itfcPermissionMapper.enable(permissionId);
    }

    public int delete(String permissionId) {
        int num1 = itfcPermissionMapper.delete(permissionId);
        int num2 = itfcPermissionMapper.deleteChildren(permissionId);
        return num1 + num2;
    }

    public List<Map> listTree() {
        return itfcPermissionMapper.listTree();
    }

    public int update(ItfcPermission itfcPermission, boolean ifEdit) {
        int num = 0;
        if (ifEdit) {
            num = itfcPermissionMapper.update(itfcPermission);
        } else {
            num = itfcPermissionMapper.insert(itfcPermission);
        }

        return num;
    }

}
