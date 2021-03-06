package com.bluewind.boot.module.sys.sysrolepermission.mapper;

import com.bluewind.boot.module.sys.sysrolepermission.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2021-01-07-23:06
 **/
@Repository
public interface SysRolePermissionMapper {

    /**
     * 登录时，根据用户id查询所有的权限标识(资源值)
     */
    Set<String> listRolePermissionByUserId(@Param("userId") Integer userId);

    /**
     * 根据roleId删除表里的记录
     * @param roleId
     * @return
     */
    int deleteRolePermissionByRoleId(@Param("roleId") String roleId);

    /**
     * 批量插入
     * @param list
     * @return
     */
    int batchInsertRolePermission(List<SysRolePermission> list);
}
