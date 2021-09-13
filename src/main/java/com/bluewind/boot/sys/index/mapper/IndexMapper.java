package com.bluewind.boot.sys.index.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * @author liuxingyu01
 * @date 2020-03-22-15:27
 **/
@Repository
public interface IndexMapper {

    /**
     * 根据用户名查询一个账户
     */
    Map findAccountByUserId(Integer userId);

    /**
     * 修改密码
     */
    int doUpdatePassword(Map map);

    /**
     * 获取菜单列表
     * @return
     */
    List<Map> menuInit(@Param("id") Integer id);

}
