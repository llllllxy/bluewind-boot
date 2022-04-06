package com.bluewind.boot.module.sys.sysdeptinfo.service;

import com.bluewind.boot.module.sys.sysdeptinfo.entity.SysDeptInfo;
import com.bluewind.boot.module.sys.sysdeptinfo.mapper.SysDeptInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2022-03-17 16:50
 * @description
 **/
@Service
public class SysDeptInfoServiceImpl implements SysDeptInfoService {

    @Autowired
    private SysDeptInfoMapper sysDeptInfoMapper;

    public List<SysDeptInfo> list() {
        return sysDeptInfoMapper.list();
    }
}
