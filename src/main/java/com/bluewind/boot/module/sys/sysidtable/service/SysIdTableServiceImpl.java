package com.bluewind.boot.module.sys.sysidtable.service;

import com.bluewind.boot.module.sys.sysidtable.mapper.SysIdTableMapper;
import com.bluewind.boot.module.sys.sysidtable.entity.SysIdTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-04-05-21:49
 **/
@Service
public class SysIdTableServiceImpl implements SysIdTableService {

    @Autowired
    private SysIdTableMapper sysIdTableMapper;

    @Override
    public List<SysIdTable> getSysIdTableList(Map<String, String> map) {
        return sysIdTableMapper.getSysIdTableList(map);
    }

    @Override
    public Integer checkExistByIdCode(String idCode) {
        return sysIdTableMapper.checkExistByIdCode(idCode);
    }

    @Override
    public int addOneIdTable(SysIdTable sysIdTable) {
        return sysIdTableMapper.addOneIdTable(sysIdTable);
    }


    @Override
    public int deleteOne(String idId) {
        return sysIdTableMapper.deleteOne(idId);
    }

    @Override
    public SysIdTable getOneIdTable(String idId) {
        return sysIdTableMapper.getOneIdTable(idId);
    }

    @Override
    public int updateIdTable(SysIdTable sysIdTable) {
        return sysIdTableMapper.updateIdTable(sysIdTable);
    }

}
