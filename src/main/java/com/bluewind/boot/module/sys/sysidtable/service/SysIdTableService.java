package com.bluewind.boot.module.sys.sysidtable.service;

import com.bluewind.boot.module.sys.sysidtable.entity.SysIdTable;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-04-05-21:49
 **/
public interface SysIdTableService {

    List<SysIdTable> getSysIdTableList(Map<String, String> map);

    Integer checkExistByIdCode(String idCode);

    int addOneIdTable(SysIdTable sysIdTable);

    int deleteOne(String idId);

    SysIdTable getOneIdTable(String idId);

    int updateIdTable(SysIdTable sysIdTable);

}
