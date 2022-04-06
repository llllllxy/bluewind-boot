package com.bluewind.boot.module.system.idtable.service;

import com.bluewind.boot.module.system.idtable.entity.IdTable;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-04-05-21:49
 **/
public interface IdTableService {

    List<IdTable> getSysIdTableList(Map<String, String> map);

    Integer checkExistByIdCode(String idCode);

    int addOneIdTable(IdTable idTable);

    int deleteOne(String idId);

    IdTable getOneIdTable(String idId);

    int updateIdTable(IdTable idTable);

}
