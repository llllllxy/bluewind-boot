package com.liuxingyu.meco.sys.sysroleinfo.mapper;

import com.liuxingyu.meco.sys.sysroleinfo.entity.SysRoleInfo;
import com.liuxingyu.meco.sys.sysroleinfo.entity.XmSelect;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-01-28-13:31
 **/
@Repository
public interface SysRoleInfoMapper {


    /**
     * 根据用户id查询角色，给xmselect赋值
     */
    List<XmSelect> listXmSelectPojo(@Param("userId") Integer userId);


    /**
     * 获取角色列表
     * @param map
     * @return
     */
    List<SysRoleInfo> getSysRoleInfoList(Map map);



    /**
     * 角色新增
     * @param sysRoleInfo
     * @return
     */
    int doAdd(SysRoleInfo sysRoleInfo);


    /**
     * 删除一个系统角色（这里用逻辑删除）
     * @param id
     * @return
     */
    int delete(int id);



    /**
     * 禁用一个系统角色
     * @param id
     * @return
     */
    int forbid(int id);


    /**
     * 启用一个系统角色
     * @param id
     * @return
     */
    int enable(int id);


    /**
     * 通过角色id获取一个角色
     * @param id
     * @return
     */
    SysRoleInfo getOneRoleById(int id);


    /**
     * 角色更新
     * @param sysRoleInfo
     * @return
     */
    int doUpdate(SysRoleInfo sysRoleInfo);

}
