package com.bluewind.boot.module.sys.sysdeptinfo.mapper;

import com.bluewind.boot.module.sys.sysdeptinfo.entity.SysDeptInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuxingyu01
 * @date 2022-03-17 16:49
 * @description
 **/
@Repository
public interface SysDeptInfoMapper {

    List<SysDeptInfo> list();
}
