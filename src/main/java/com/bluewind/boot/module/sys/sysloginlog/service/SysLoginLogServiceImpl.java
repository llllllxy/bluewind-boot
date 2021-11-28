package com.bluewind.boot.module.sys.sysloginlog.service;

import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.module.sys.sysloginlog.entity.SysLoginLog;
import com.bluewind.boot.common.utils.AddressUtils;
import com.bluewind.boot.common.utils.network.IPUtils;
import com.bluewind.boot.module.sys.sysloginlog.mapper.SysLoginLogMapper;
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
public class SysLoginLogServiceImpl implements SysLoginLogService {
    final static Logger logger = LoggerFactory.getLogger(SysLoginLogServiceImpl.class);

    private SysLoginLogMapper sysLoginLogMapper;
    //  这里是基于构造函数的依赖注入，而不是@Autowired
    public SysLoginLogServiceImpl(SysLoginLogMapper sysLoginLogMapper) {
        this.sysLoginLogMapper = sysLoginLogMapper;
    }


    /**
     * 分页查询
     */
    @Override
    public List<SysLoginLog> list(Map map) {
        return sysLoginLogMapper.getLoginLoglist(map);
    }

    /**
     * 保存登录日志
     */
    @Override
    @Async("asyncServiceExecutor") // 耗时操作放进线程池去操作,注意：异步方法使用注解@Async的返回值只能为void或者Future
    public void saveLoginlog(HttpServletRequest request, String account, String status, String descript, String redisKey) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setLogId(IdGenerate.nextId());
        sysLoginLog.setAccount(account);
        sysLoginLog.setDescript(descript);
        sysLoginLog.setStatus(status);
        sysLoginLog.setSessionId(redisKey);
        try {
            // 获取ip地址
            sysLoginLog.setIp(IPUtils.getIpAddress(request));
            sysLoginLog.setLocation(AddressUtils.getAddressByIP(sysLoginLog.getIp()));
        } catch (Exception e) {
            sysLoginLog.setIp("127.0.0.1");
            sysLoginLog.setLocation("局域网，无法获取位置");
            logger.error("SysLoginLogServiceImpl -- save -- Exception = {e}", e);
        }
        int num = sysLoginLogMapper.saveLoginlog(sysLoginLog);
    }

    /**
     * 获取当前登陆用户记录
     * @param paraMap
     * @return
     */
    @Override
    public List<Map> onlineList(Map paraMap) {
        return sysLoginLogMapper.onlineList(paraMap);
    }

    /**
     * 批量删除登陆日志
     * @param logIdList
     * @return
     */
    @Override
    public int batchDelete(List<String> logIdList) {
        return sysLoginLogMapper.batchDelete(logIdList);
    }

    /**
     * 清空登陆日志
     */
    @Override
    public void emptyLog() {
        sysLoginLogMapper.emptyLog();
    }

}
