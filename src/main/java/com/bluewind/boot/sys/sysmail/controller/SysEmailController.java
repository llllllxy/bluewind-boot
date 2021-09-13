package com.bluewind.boot.sys.sysmail.controller;

import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.mail.EmailUtils;
import com.bluewind.boot.sys.sysmaillog.service.SysEmailLogService;
import com.bluewind.boot.sys.sysmail.entity.SysEmailLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuxingyu01
 * @date 2021-04-16-0:45
 **/
@Controller
@RequestMapping("/sysmail/email")
public class SysEmailController {

    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private SysEmailLogService sysEmailLogService;

    /**
     * 请求页面
     *
     * @return
     */
    @GetMapping("/init")
    public String init(Model model) {
        model.addAttribute("from", emailUtils.SEND_ADDRESS);
        return "mail/email/index";
    }

    @GetMapping("/template/{id}")
    public String template(@PathVariable Integer id) {
        return "mail/templates/template" + id + "_bak";
    }

    /**
     * 发送文本邮件
     *
     * @return
     */
    @PostMapping("/sendTextMail")
    @ResponseBody
    public BaseResult sendTextMail(@RequestBody SysEmailLogVO sysEmailLogVO) {
        BaseResult responseResult = emailUtils.sendTextMail(sysEmailLogVO.getAddress().trim(), sysEmailLogVO.getSubject().trim(), sysEmailLogVO.getContent().trim());
        sysEmailLogVO.setType(0);
        sysEmailLogVO.setStatus(0 == responseResult.getCode() ? 0 : 1);
        sysEmailLogService.saveSysEmailLog(sysEmailLogVO);
        return responseResult;
    }

    /**
     * 发送Html邮件
     *
     * @return
     */
    @PostMapping("/sendHtmlMail")
    @ResponseBody
    public BaseResult sendHtmlMail(@RequestBody SysEmailLogVO sysEmailLogVO) {
        BaseResult baseResult = emailUtils.sendHtmlMail(sysEmailLogVO.getAddress().trim(), sysEmailLogVO.getSubject().trim(), sysEmailLogVO.getContent().trim());
        sysEmailLogVO.setType(1);
        sysEmailLogVO.setStatus(0 == baseResult.getCode() ? 0 : 1);
        sysEmailLogService.saveSysEmailLog(sysEmailLogVO);
        return baseResult;
    }

    /**
     * 发送图片邮件
     *
     * @param address 收件地址
     * @param title   标题
     * @param text    内容
     * @param srcPath 图片地址
     * @param srcId   图片标识
     * @return
     */
    @PostMapping("/sendImageMail")
    @ResponseBody
    public BaseResult sendImageMail(String address, String title, String text, String srcPath, String srcId) {
        return emailUtils.sendInlinResourceMail(address.trim(), title.trim(), text.trim(), srcPath.trim(), srcId.trim());
    }

    /**
     * 发送附件邮件
     *
     * @param address  收件地址
     * @param title    标题
     * @param text     内容
     * @param filePath 文件地址
     * @return
     */
    @PostMapping("/sendFileMail")
    @ResponseBody
    public BaseResult sendFileMail(String address, String title, String text, String filePath) {
        return emailUtils.sendAttachmentMail(address.trim(), title.trim(), text.trim(), filePath.trim());
    }

    /**
     * 发送模板邮件
     *
     * @return
     */
    @PostMapping("/sendTemplateMail")
    @ResponseBody
    public BaseResult sendTemplateMail(@RequestBody SysEmailLogVO sysEmailLogVO) {
        BaseResult baseResult = emailUtils.sendTemplateMail(sysEmailLogVO.getAddress().trim(), sysEmailLogVO.getSubject().trim(), sysEmailLogVO.getTemplate().trim(), sysEmailLogVO.getContent().trim());
        sysEmailLogVO.setType(4);
        sysEmailLogVO.setStatus(0 == baseResult.getCode() ? 0 : 1);
        sysEmailLogService.saveSysEmailLog(sysEmailLogVO);
        return baseResult;
    }

}
