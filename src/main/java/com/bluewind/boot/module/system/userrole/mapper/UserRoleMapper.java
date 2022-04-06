package com.bluewind.boot.module.system.userrole.mapper;

import com.bluewind.boot.module.system.userrole.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author liuxingyu01
 * @date 2021-01-07-22:51
 **/
@Repository
public interface UserRoleMapper {

    /**
     * 登录时，根据用户id查询全部角色标识
     */
    Set<String> listUserRoleByUserId(@Param("userId") String userId);

    int deleteUserRoleByUserId(@Param("userId") String userId);

    int batchSaveUserRole(List<UserRole> list);
}
