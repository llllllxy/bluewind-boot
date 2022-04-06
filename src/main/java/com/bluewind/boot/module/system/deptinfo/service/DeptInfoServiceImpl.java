package com.bluewind.boot.module.system.deptinfo.service;

import com.bluewind.boot.module.system.deptinfo.entity.DeptInfo;
import com.bluewind.boot.module.system.deptinfo.mapper.DeptInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2022-03-17 16:50
 * @description
 **/
@Service
public class DeptInfoServiceImpl implements DeptInfoService {

    @Autowired
    private DeptInfoMapper deptInfoMapper;

    public List<DeptInfo> list() {
        return deptInfoMapper.list();
    }
}
