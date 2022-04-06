package com.bluewind.boot.module.system.itfckey.mapper;

import com.bluewind.boot.module.system.itfckey.entity.ItfcPermissionTree;
import com.bluewind.boot.module.system.itfckey.entity.ItfcKey;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-06-12-17:00
 **/
@Repository
public interface ItfcKeyMapper {

    List<ItfcKey> getSysItfcKeyList(Map<String, String> map);

    int addOneSysItfcKey(ItfcKey itfcKey);

    ItfcKey getOneSysItfcKey(Integer id);

    int updateSysItfcKey(ItfcKey itfcKey);

    int deleteSysItfcKey(Integer id);

    int forbidSysItfcKey(Integer id);

    int enableSysItfcKey(Integer id);

    List<ItfcPermissionTree> listPermissionForTree(String itfcKey);

    int deletePermissionByItfcKey(String itfcKey);

    int batchInsertPermission( List<Map<String, String>> list);
}
