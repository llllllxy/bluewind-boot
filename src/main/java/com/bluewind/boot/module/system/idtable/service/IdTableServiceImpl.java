package com.bluewind.boot.module.system.idtable.service;

import com.bluewind.boot.module.system.idtable.entity.IdTable;
import com.bluewind.boot.module.system.idtable.mapper.IdTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-04-05-21:49
 **/
@Service
public class IdTableServiceImpl implements IdTableService {

    @Autowired
    private IdTableMapper idTableMapper;

    @Override
    public List<IdTable> getSysIdTableList(Map<String, String> map) {
        return idTableMapper.getSysIdTableList(map);
    }

    @Override
    public Integer checkExistByIdCode(String idCode) {
        return idTableMapper.checkExistByIdCode(idCode);
    }

    @Override
    public int addOneIdTable(IdTable idTable) {
        return idTableMapper.addOneIdTable(idTable);
    }


    @Override
    public int deleteOne(String idId) {
        return idTableMapper.deleteOne(idId);
    }

    @Override
    public IdTable getOneIdTable(String idId) {
        return idTableMapper.getOneIdTable(idId);
    }

    @Override
    public int updateIdTable(IdTable idTable) {
        return idTableMapper.updateIdTable(idTable);
    }

}
