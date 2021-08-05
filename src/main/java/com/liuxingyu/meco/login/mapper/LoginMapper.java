package com.liuxingyu.meco.login.mapper;

import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2020-03-18-13:45
 **/
@Repository
public interface LoginMapper {

    /*
     * 查询登陆用户的信息
     */

    List<Map> userLogin(Map map);

}
