package com.liuxingyu.meco.sys.sysquartz.service;

import com.liuxingyu.meco.common.base.BaseResult;
import com.liuxingyu.meco.sys.sysquartz.entity.SysQuartz;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-17-22:39
 **/
public interface SysQuartzService {

    /**
     * 获取需要默认执行的定时任务
     * @param sysQuartz
     * @return
     */
    List<SysQuartz> getDefaultList(SysQuartz sysQuartz);

    /**
     * 启动定时任务(项目启动时用的)
     */
    BaseResult schedulerAdd(String className, String cronExpression, String param);

    /**
     * 停止定时任务(项目启动时用的)
     */
    BaseResult schedulerDelete(String className);

    /**
     * 获取定时任务分页列表
     *
     * @param map
     * @return
     */
    List<SysQuartz> list(Map map);

    /**
     * 启动定时任务(查询列表用的)
     *
     * @param id
     * @return
     */
    int startJob(Integer id);

    /**
     * 停止定时任务(查询列表用的)
     *
     * @param id
     * @return
     */
    int stopJob(Integer id);

    /**
     * 任务新增
     *
     * @param sysQuartz
     * @return
     */
    int addOneQuartz(SysQuartz sysQuartz);

    /**
     * 任务删除
     *
     * @param id
     * @return
     */
    int deleteOneById(Integer id);


    /**
     * 获取一个定时任务
     *
     * @param id
     * @return
     */
    SysQuartz getOneQuqrtz(Integer id);


    /**
     * 任务修改
     *
     * @param sysQuartz
     * @return
     */
    int updateOneQuartz(SysQuartz sysQuartz);
}
