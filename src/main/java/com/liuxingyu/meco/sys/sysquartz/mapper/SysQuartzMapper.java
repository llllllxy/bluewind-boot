package com.liuxingyu.meco.sys.sysquartz.mapper;

import com.liuxingyu.meco.sys.sysquartz.entity.SysQuartz;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-17-22:32
 **/
@Repository
public interface SysQuartzMapper {

    /**
     * 获取需要默认执行的定时任务
     *
     * @param sysQuartz
     * @return
     */
    List<SysQuartz> getDefaultList(SysQuartz sysQuartz);


    /**
     * 获取定时任务分页列表
     *
     * @param map
     * @return
     */
    List<SysQuartz> list(Map map);


    /**
     * 获取一个定时任务
     *
     * @param id
     * @return
     */
    SysQuartz getOneQuqrtz(Integer id);

    /**
     * 停止一个定时任务
     *
     * @param id
     * @return
     */
    int stopQuqrtzStatus(Integer id);


    /**
     * 开始一个定时任务
     *
     * @param id
     * @return
     */
    int startQuqrtzStatus(Integer id);


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
     * 任务修改
     *
     * @param sysQuartz
     * @return
     */
    int updateOneQuartz(SysQuartz sysQuartz);

}
