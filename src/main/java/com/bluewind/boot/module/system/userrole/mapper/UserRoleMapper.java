package com.bluewind.boot.module.system.userrole.mapper;

import com.bluewind.boot.module.system.userrole.entity.UserRole;
import com.bluewind.boot.module.system.userrole.vo.XmSelectVo;
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
     * 登录时，根据用户userId查询全部角色标识
     * @param userId
     * @return
     */
    Set<String> listUserRoleByUserId(@Param("userId") String userId);


    /**
     * 根据userId删除对应的角色
     * @param userId
     * @return
     */
    int deleteUserRoleByUserId(@Param("userId") String userId);


    /**
     * 批量插入角色
     * @param list
     * @return
     */
    int batchSaveUserRole(List<UserRole> list);


    /**
     * 根据userId查询角色，给xmselect赋值
     * @param userId
     * @return
     */
    List<XmSelectVo> listRoleByUserId(@Param("userId") String userId);
}
