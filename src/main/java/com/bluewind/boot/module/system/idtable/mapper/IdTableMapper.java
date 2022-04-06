package com.bluewind.boot.module.system.idtable.mapper;

import com.bluewind.boot.module.system.idtable.entity.IdTable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-04-05-21:48
 **/
@Repository
public interface IdTableMapper {

    List<IdTable> getSysIdTableList(Map<String, String> map);

    Integer checkExistByIdCode(String idCode);

    int addOneIdTable(IdTable idTable);

    int deleteOne(String idId);

    IdTable getOneIdTable(String idId);

    int updateIdTable(IdTable idTable);
}
