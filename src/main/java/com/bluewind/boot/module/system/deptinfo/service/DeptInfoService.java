package com.bluewind.boot.module.system.deptinfo.service;

import com.bluewind.boot.module.system.deptinfo.entity.DeptInfo;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2022-03-17 16:49
 * @description
 **/
public interface DeptInfoService {

    List<DeptInfo> list();

    DeptInfo getOne(String deptId);

    int add(DeptInfo deptInfo);

    int update(DeptInfo deptInfo);

    int del(String deptId);

    int updateSort(DeptInfo deptInfo);
}
