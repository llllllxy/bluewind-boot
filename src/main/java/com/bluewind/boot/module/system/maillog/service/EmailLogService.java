package com.bluewind.boot.module.system.maillog.service;

import com.bluewind.boot.module.system.mail.entity.EmailLogVO;

/**
 * @author liuxingyu01
 * @date 2021-04-16-0:54
 **/
public interface EmailLogService {

    int saveSysEmailLog(EmailLogVO sysEmailLogVO);

}
