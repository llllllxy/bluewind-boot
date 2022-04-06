package com.bluewind.boot.module.system.roleinfo.mapper;

import com.bluewind.boot.module.system.roleinfo.entity.RoleInfo;
import com.bluewind.boot.module.system.roleinfo.entity.XmSelect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-28-13:31
 **/
@Repository
public interface RoleInfoMapper {


    /**
     * 根据用户id查询角色，给xmselect赋值
     */
    List<XmSelect> listXmSelectPojo(@Param("userId") String userId);


    /**
     * 获取角色列表
     * @param map
     * @return
     */
    List<RoleInfo> getSysRoleInfoList(Map map);



    /**
     * 角色新增
     * @param roleInfo
     * @return
     */
    int doAdd(RoleInfo roleInfo);


    /**
     * 删除一个系统角色（这里用逻辑删除）
     * @param roleId
     * @return
     */
    int delete(String roleId);



    /**
     * 禁用一个系统角色
     * @param roleId
     * @return
     */
    int forbid(String roleId);


    /**
     * 启用一个系统角色
     * @param roleId
     * @return
     */
    int enable(String roleId);


    /**
     * 通过角色id获取一个角色
     * @param roleId
     * @return
     */
    RoleInfo getOneRoleById(String roleId);


    /**
     * 角色更新
     * @param roleInfo
     * @return
     */
    int doUpdate(RoleInfo roleInfo);

}
