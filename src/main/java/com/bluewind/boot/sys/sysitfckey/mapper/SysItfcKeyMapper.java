package com.bluewind.boot.sys.sysitfckey.mapper;

import com.bluewind.boot.sys.sysitfckey.entity.ItfcPermissionTree;
import com.bluewind.boot.sys.sysitfckey.entity.SysItfcKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-06-12-17:00
 **/
@Repository
public interface SysItfcKeyMapper {

    List<SysItfcKey> getSysItfcKeyList(Map<String, String> map);

    int addOneSysItfcKey(SysItfcKey sysItfcKey);

    SysItfcKey getOneSysItfcKey(Integer id);

    int updateSysItfcKey(SysItfcKey sysItfcKey);

    int deleteSysItfcKey(Integer id);

    int forbidSysItfcKey(Integer id);

    int enableSysItfcKey(Integer id);

    List<ItfcPermissionTree> listPermissionForTree(String itfcKey);

    int deletePermissionByItfcKey(String itfcKey);

    int batchInsertPermission( List<Map<String, String>> list);
}
