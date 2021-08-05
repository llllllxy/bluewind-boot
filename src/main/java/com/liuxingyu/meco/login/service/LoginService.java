package com.liuxingyu.meco.login.service;

import com.liuxingyu.meco.login.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2020-03-18-13:46
 **/

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;
    /*
     * 查询登陆用户的信息
     */
    public List<Map> userLogin(Map map){

        return loginMapper.userLogin(map);
    }
}

