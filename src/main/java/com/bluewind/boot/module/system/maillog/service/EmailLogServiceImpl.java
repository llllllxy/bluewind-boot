package com.bluewind.boot.module.system.maillog.service;

import com.bluewind.boot.common.config.security.SecurityUtil;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.module.system.maillog.mapper.EmailLogMapper;
import com.bluewind.boot.module.system.mail.entity.EmailLog;
import com.bluewind.boot.module.system.mail.entity.EmailLogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuxingyu01
 * @date 2021-04-16-0:55
 **/
@Service
public class EmailLogServiceImpl implements EmailLogService {

    @Autowired
    private EmailLogMapper emailLogMapper;

    public int saveSysEmailLog(EmailLogVO sysEmailLogVO) {
        EmailLog emailLog = new EmailLog();
        // 属性复制
        BeanUtils.copyProperties(sysEmailLogVO, emailLog);
        emailLog.setCreateUser(SecurityUtil.getSysUserId());
        emailLog.setLogId(IdGenerate.nextId());
        return emailLogMapper.saveSysEmailLog(emailLog);
    }

}
