package com.liuxingyu.meco.sys.sysitfckey.service;

import com.liuxingyu.meco.common.utils.JsonTool;
import com.liuxingyu.meco.sys.sysitfckey.entity.ItfcPermissionTree;
import com.liuxingyu.meco.sys.sysitfckey.entity.SysItfcKey;
import com.liuxingyu.meco.sys.sysitfckey.mapper.SysItfcKeyMapper;
import com.liuxingyu.meco.sys.sysitfckey.util.TreeUtil;
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
public class SysItfcKeyServiceImpl implements SysItfcKeyService {

    @Autowired
    private SysItfcKeyMapper sysItfcKeyMapper;

    @Override
    public List<SysItfcKey> getSysItfcKeyList(Map<String, String> map) {
        return sysItfcKeyMapper.getSysItfcKeyList(map);
    }

    @Override
    public int addOneSysItfcKey(SysItfcKey sysItfcKey) {
        return sysItfcKeyMapper.addOneSysItfcKey(sysItfcKey);
    }

    @Override
    public SysItfcKey getOneSysItfcKey(Integer id) {
        return sysItfcKeyMapper.getOneSysItfcKey(id);
    }

    @Override
    public int updateSysItfcKey(SysItfcKey sysItfcKey) {
        return sysItfcKeyMapper.updateSysItfcKey(sysItfcKey);
    }

    @Override
    public int deleteSysItfcKey(Integer id) {
        return sysItfcKeyMapper.deleteSysItfcKey(id);
    }

    @Override
    public int forbidSysItfcKey(Integer id) {
        return sysItfcKeyMapper.forbidSysItfcKey(id);
    }

    @Override
    public int enableSysItfcKey(Integer id) {
        return sysItfcKeyMapper.enableSysItfcKey(id);
    }

    @Override
    public String listPermissionForTree(String itfcKey) {
        Map<String, Object> resultMap = new HashMap<>();
        List<ItfcPermissionTree> list = sysItfcKeyMapper.listPermissionForTree(itfcKey);
        if (null == list || list.isEmpty()) {
            resultMap.put("data", null);
            resultMap.put("code", 0);
            resultMap.put("msg", "");
            return JsonTool.mapToJsonString(resultMap);
        }

        List<ItfcPermissionTree> resultList = TreeUtil.toTree(list, "0");
        resultMap.put("data", resultList);
        resultMap.put("code", 0);
        resultMap.put("msg", "");
        return JsonTool.mapToJsonString(resultMap);
    }

    @Override
    @Transactional
    public int authorize(String itfcKey, String permIds) {
        // 先删除sys_role_permission表里之前存的
        int num = sysItfcKeyMapper.deletePermissionByItfcKey(itfcKey);

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
            int numm = sysItfcKeyMapper.batchInsertPermission(list);
            return numm;
        } else {
            return 0;
        }
    }
}
