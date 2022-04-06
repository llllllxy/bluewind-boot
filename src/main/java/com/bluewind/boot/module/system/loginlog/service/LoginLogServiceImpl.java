package com.bluewind.boot.module.system.loginlog.service;

import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.module.system.loginlog.entity.LoginLog;
import com.bluewind.boot.common.utils.AddressUtils;
import com.bluewind.boot.common.utils.network.IPUtils;
import com.bluewind.boot.module.system.loginlog.mapper.LoginLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-02-18-12:56
 **/
@Service
public class LoginLogServiceImpl implements LoginLogService {
    final static Logger logger = LoggerFactory.getLogger(LoginLogServiceImpl.class);

    private final LoginLogMapper loginLogMapper;
    // 基于构造函数的依赖注入，不需要@Autowired注解
    public LoginLogServiceImpl(LoginLogMapper loginLogMapper) {
        this.loginLogMapper = loginLogMapper;
    }


    /**
     * 分页查询
     */
    @Override
    public List<LoginLog> list(Map map) {
        return loginLogMapper.getLoginLoglist(map);
    }

    /**
     * 保存登录日志
     */
    @Override
    @Async("asyncServiceExecutor") // 耗时操作放进线程池去操作,注意：异步方法使用注解@Async的返回值只能为void或者Future
    public void saveLoginlog(HttpServletRequest request, String account, String status, String descript, String redisKey) {
        LoginLog loginLog = new LoginLog();
        loginLog.setLogId(IdGenerate.nextId());
        loginLog.setAccount(account);
        loginLog.setDescript(descript);
        loginLog.setStatus(status);
        loginLog.setSessionId(redisKey);
        try {
            // 获取ip地址
            loginLog.setIp(IPUtils.getIpAddress(request));
            loginLog.setLocation(AddressUtils.getAddressByIP(loginLog.getIp()));
        } catch (Exception e) {
            loginLog.setIp("127.0.0.1");
            loginLog.setLocation("局域网，无法获取位置");
            logger.error("SysLoginLogServiceImpl -- save -- Exception = {e}", e);
        }
        int num = loginLogMapper.saveLoginlog(loginLog);
    }

    /**
     * 获取当前登陆用户记录
     * @param paraMap
     * @return
     */
    @Override
    public List<Map> onlineList(Map paraMap) {
        return loginLogMapper.onlineList(paraMap);
    }

    /**
     * 批量删除登陆日志
     * @param logIdList
     * @return
     */
    @Override
    public int batchDelete(List<String> logIdList) {
        return loginLogMapper.batchDelete(logIdList);
    }

    /**
     * 清空登陆日志
     */
    @Override
    public void emptyLog() {
        loginLogMapper.emptyLog();
    }

}
