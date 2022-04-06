package com.bluewind.boot.module.system.itfckey.service;

import com.bluewind.boot.module.system.itfckey.entity.ItfcKey;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-06-12-17:01
 **/
public interface ItfcKeyService {

    List<ItfcKey> getSysItfcKeyList(Map<String, String> map);

    int addOneSysItfcKey(ItfcKey itfcKey);

    ItfcKey getOneSysItfcKey(Integer id);

    int updateSysItfcKey(ItfcKey itfcKey);

    int deleteSysItfcKey(Integer id);

    int forbidSysItfcKey(Integer id);

    int enableSysItfcKey(Integer id);

    String listPermissionForTree(String itfcKey);

    int authorize(String itfcKey, String permIds);

}
