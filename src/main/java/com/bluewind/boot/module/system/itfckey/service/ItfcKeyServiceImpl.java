package com.bluewind.boot.module.system.itfckey.service;

import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.module.system.itfckey.entity.ItfcKey;
import com.bluewind.boot.module.system.itfckey.entity.ItfcPermissionTree;
import com.bluewind.boot.module.system.itfckey.mapper.ItfcKeyMapper;
import com.bluewind.boot.module.system.itfckey.util.ItfcPermissionTreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-06-12-17:01
 **/
@Service
public class ItfcKeyServiceImpl implements ItfcKeyService {

    @Autowired
    private ItfcKeyMapper itfcKeyMapper;

    @Override
    public List<ItfcKey> getSysItfcKeyList(Map<String, String> map) {
        return itfcKeyMapper.getSysItfcKeyList(map);
    }

    @Override
    public int addOneSysItfcKey(ItfcKey itfcKey) {
        return itfcKeyMapper.addOneSysItfcKey(itfcKey);
    }

    @Override
    public ItfcKey getOneSysItfcKey(Integer id) {
        return itfcKeyMapper.getOneSysItfcKey(id);
    }

    @Override
    public int updateSysItfcKey(ItfcKey itfcKey) {
        return itfcKeyMapper.updateSysItfcKey(itfcKey);
    }

    @Override
    public int deleteSysItfcKey(Integer id) {
        return itfcKeyMapper.deleteSysItfcKey(id);
    }

    @Override
    public int forbidSysItfcKey(Integer id) {
        return itfcKeyMapper.forbidSysItfcKey(id);
    }

    @Override
    public int enableSysItfcKey(Integer id) {
        return itfcKeyMapper.enableSysItfcKey(id);
    }

    @Override
    public String listPermissionForTree(String itfcKey) {
        Map<String, Object> resultMap = new HashMap<>();
        List<ItfcPermissionTree> list = itfcKeyMapper.listPermissionForTree(itfcKey);
        if (null == list || list.isEmpty()) {
            resultMap.put("data", null);
            resultMap.put("code", 0);
            resultMap.put("msg", "");
            return JsonTool.toJsonString(resultMap);
        }

        List<ItfcPermissionTree> resultList = ItfcPermissionTreeUtil.toTree(list, "0");
        resultMap.put("data", resultList);
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        return JsonTool.toJsonString(resultMap);
    }

    @Override
    @Transactional
    public int authorize(String itfcKey, String permIds) {
        // 先删除sys_role_permission表里之前存的
        int num = itfcKeyMapper.deletePermissionByItfcKey(itfcKey);

        // 模拟事务报错，测试是否回滚，测试成功
        // List<String> test = new ArrayList<>();
        // String ii = test.get(10);


        // 再进行重新插入角色权限
        if (StringUtils.isNotBlank(permIds)) {
            String[] permArr = permIds.split(",");
            List<Map<String, String>> list = new ArrayList<>();
            Map<String, String> paraMap = null;
            for (String item : permArr) {
                paraMap = new HashMap<>();
                paraMap.put("itfcKey", itfcKey);
                paraMap.put("permId", item);
                list.add(paraMap);
            }
            int numm = itfcKeyMapper.batchInsertPermission(list);
            return numm;
        } else {
            return 0;
        }
    }
}
