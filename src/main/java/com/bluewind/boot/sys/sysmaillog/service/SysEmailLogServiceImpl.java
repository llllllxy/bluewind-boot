package com.bluewind.boot.sys.sysmaillog.service;

import com.bluewind.boot.configuration.security.SecurityUtil;
import com.bluewind.boot.sys.sysmaillog.mapper.SysEmailLogMapper;
import com.bluewind.boot.sys.sysmail.entity.SysEmailLog;
import com.bluewind.boot.sys.sysmail.entity.SysEmailLogVO;
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
        sysEmailLog.setCreateUser(SecurityUtil.getSysUserId());
        return sysEmailLogMapper.saveSysEmailLog(sysEmailLogVO);
    }

}
