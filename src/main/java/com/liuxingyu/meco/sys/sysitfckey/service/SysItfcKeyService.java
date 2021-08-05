package com.liuxingyu.meco.sys.sysitfckey.service;

import com.liuxingyu.meco.sys.sysitfckey.entity.SysItfcKey;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-06-12-17:01
 **/
public interface SysItfcKeyService {

    List<SysItfcKey> getSysItfcKeyList(Map<String, String> map);

    int addOneSysItfcKey(SysItfcKey sysItfcKey);

    SysItfcKey getOneSysItfcKey(Integer id);

    int updateSysItfcKey(SysItfcKey sysItfcKey);

    int deleteSysItfcKey(Integer id);

    int forbidSysItfcKey(Integer id);

    int enableSysItfcKey(Integer id);

    String listPermissionForTree(String itfcKey);

    int authorize(String itfcKey, String permIds);

}
