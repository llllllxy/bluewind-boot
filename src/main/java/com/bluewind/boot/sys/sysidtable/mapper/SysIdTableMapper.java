package com.bluewind.boot.sys.sysidtable.mapper;

import com.bluewind.boot.sys.sysidtable.entity.SysIdTable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-04-05-21:48
 **/
@Repository
public interface SysIdTableMapper {

    List<SysIdTable> getSysIdTableList(Map<String, String> map);

    int addOneIdTable(SysIdTable sysIdTable);

    int deleteOne(Integer id);

    SysIdTable getOneIdTable(Integer id);

    int updateIdTable(SysIdTable sysIdTable);
}
