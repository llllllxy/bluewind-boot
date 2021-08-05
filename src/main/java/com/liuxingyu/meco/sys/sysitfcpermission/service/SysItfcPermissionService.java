package com.liuxingyu.meco.sys.sysitfcpermission.service;

import com.liuxingyu.meco.sys.sysitfcpermission.entity.SysItfcPermission;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-07-09-22:01
 **/
public interface SysItfcPermissionService {

    List<SysItfcPermission> list(String permissionId);

    int enable(String permissionId);

    int delete(String permissionId);

    List<Map> listTree();

    int update(SysItfcPermission sysItfcPermission, boolean ifEdit);
}
