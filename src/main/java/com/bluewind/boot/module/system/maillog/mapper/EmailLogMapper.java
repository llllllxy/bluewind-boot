package com.bluewind.boot.module.system.maillog.mapper;

import com.bluewind.boot.module.system.mail.entity.EmailLog;
import org.springframework.stereotype.Repository;

/**
 * @author liuxingyu01
 * @date 2021-04-16-0:56
 **/
@Repository
public interface EmailLogMapper {

    int saveSysEmailLog(EmailLog emailLog);

}
