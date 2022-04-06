package com.bluewind.boot.module.system.itfcpermission.mapper;

import com.bluewind.boot.module.system.itfcpermission.entity.ItfcPermission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-07-09-22:10
 **/
@Repository
public interface ItfcPermissionMapper {

    List<ItfcPermission> list(String permissionId);

    int update(ItfcPermission itfcPermission);

    int insert(ItfcPermission itfcPermission);

    int enable(String permissionId);

    int delete(String permissionId);

    int deleteChildren(String permissionId);

    List<Map> listTree();
}
