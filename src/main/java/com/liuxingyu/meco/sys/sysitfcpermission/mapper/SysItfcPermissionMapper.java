package com.liuxingyu.meco.sys.sysitfcpermission.mapper;

import com.liuxingyu.meco.sys.sysitfcpermission.entity.SysItfcPermission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-07-09-22:10
 **/
@Repository
public interface SysItfcPermissionMapper {

    List<SysItfcPermission> list(String permissionId);

    int update(SysItfcPermission sysItfcPermission);

    int insert(SysItfcPermission sysItfcPermission);

    int enable(String permissionId);

    int delete(String permissionId);

    int deleteChildren(String permissionId);

    List<Map> listTree();
}
