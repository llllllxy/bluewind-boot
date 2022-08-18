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

    ItfcKey getOneSysItfcKey(String itfcId);

    int updateSysItfcKey(ItfcKey itfcKey);

    int deleteSysItfcKey(String itfcId);

    int forbidSysItfcKey(String itfcId);

    int enableSysItfcKey(String itfcId);

    String listPermissionForTree(String itfcKey);

    int authorize(String itfcKey, String permIds);

}
