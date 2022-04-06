package com.bluewind.boot.module.system.itfcpermission.service;

import com.bluewind.boot.module.system.itfcpermission.entity.ItfcPermission;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-07-09-22:01
 **/
public interface ItfcPermissionService {

    List<ItfcPermission> list(String permissionId);

    int enable(String permissionId);

    int delete(String permissionId);

    List<Map> listTree();

    int update(ItfcPermission itfcPermission, boolean ifEdit);
}
