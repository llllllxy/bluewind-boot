package com.bluewind.boot.module.system.deptinfo.mapper;

import com.bluewind.boot.module.system.deptinfo.entity.DeptInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2022-03-17 16:49
 * @description
 **/
@Repository
public interface DeptInfoMapper {

    List<DeptInfo> list();

    DeptInfo getOne(String deptId);

    int add(DeptInfo deptInfo);

    int update(DeptInfo deptInfo);

    int del(String deptId);

    int updateSort(DeptInfo deptInfo);
}
