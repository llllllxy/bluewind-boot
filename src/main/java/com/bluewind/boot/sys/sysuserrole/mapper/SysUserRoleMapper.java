package com.bluewind.boot.sys.sysuserrole.mapper;

import com.bluewind.boot.sys.sysuserrole.entity.SysUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:51
 **/
@Repository
public interface SysUserRoleMapper {

    /**
     * 登录时，根据用户id查询全部角色标识
     */
    Set<String> listUserRoleByUserId(@Param("userId") Integer userId);


    int deleteUserRoleByUserId(@Param("userId") Integer userId);

    int batchSaveUserRole(List<SysUserRole> list);
}
