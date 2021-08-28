package com.liuxingyu.meco.sys.sysjob.mapper;

import com.liuxingyu.meco.sys.sysjob.entity.SysJob;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-08-27-16:39
 **/
@Repository
public interface SysJobMapper {

    List<SysJob> list(Map<String, String> paraMap);

    SysJob getOne(String jobId);

    int insertJob(SysJob sysJob);

    int changeStatus(SysJob sysJob);

    int deleteJobById(String jobId);

    int updateJob(SysJob sysJob);
}
