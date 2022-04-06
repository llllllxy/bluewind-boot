package com.bluewind.boot.module.system.login.service;

import com.bluewind.boot.module.system.login.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuxingyu01
 * @date 2020-03-18-13:46
 **/

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;
}

