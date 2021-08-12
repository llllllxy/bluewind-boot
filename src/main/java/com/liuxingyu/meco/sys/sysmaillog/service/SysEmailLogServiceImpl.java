package com.liuxingyu.meco.sys.sysmaillog.service;

import com.liuxingyu.meco.configuration.security.UserTokenUtil;
import com.liuxingyu.meco.sys.sysmail.entity.SysEmailLog;
import com.liuxingyu.meco.sys.sysmail.entity.SysEmailLogVO;
import com.liuxingyu.meco.sys.sysmaillog.mapper.SysEmailLogMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuxingyu01
 * @date 2021-04-16-0:55
 **/
@Service
public class SysEmailLogServiceImpl implements SysEmailLogService {

    @Autowired
    private SysEmailLogMapper sysEmailLogMapper;

    public int saveSysEmailLog(SysEmailLogVO sysEmailLogVO) {
        SysEmailLog sysEmailLog = new SysEmailLog();
        // 属性复制
        BeanUtils.copyProperties(sysEmailLogVO, sysEmailLog);
        sysEmailLog.setCreateUser(UserTokenUtil.getSysUserId());
        return sysEmailLogMapper.saveSysEmailLog(sysEmailLogVO);
    }

}
