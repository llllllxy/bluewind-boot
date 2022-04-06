package com.bluewind.boot.module.system.job.mapper;

import com.bluewind.boot.module.system.job.entity.Job;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-08-27-16:39
 **/
@Repository
public interface JobMapper {

    List<Job> list(Map<String, String> paraMap);

    Job getOne(String jobId);

    int insertJob(Job job);

    int changeStatus(Job job);

    int deleteJobById(String jobId);

    int updateJob(Job job);
}
