package com.liuxingyu.meco.sys.login.service;

import com.liuxingyu.meco.sys.login.mapper.LoginMapper;
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
}

