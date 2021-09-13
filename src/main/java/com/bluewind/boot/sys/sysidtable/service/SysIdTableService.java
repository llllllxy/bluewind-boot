package com.bluewind.boot.sys.sysidtable.service;

import com.bluewind.boot.sys.sysidtable.entity.SysIdTable;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-04-05-21:49
 **/
public interface SysIdTableService {

    List<SysIdTable> getSysIdTableList(Map<String, String> map);

    int addOneIdTable(SysIdTable sysIdTable);

    int deleteOne(Integer id);

    SysIdTable getOneIdTable(Integer id);

    int updateIdTable(SysIdTable sysIdTable);

}
