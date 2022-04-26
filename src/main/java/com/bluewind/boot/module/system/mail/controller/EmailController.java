package com.bluewind.boot.module.system.mail.controller;

import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.mail.EmailUtils;
import com.bluewind.boot.module.system.maillog.service.EmailLogService;
import com.bluewind.boot.module.system.mail.entity.EmailLogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuxingyu01
 * @date 2021-04-16-0:45
 * @description 测试邮件发送
 **/
@Controller
@Api(value = "系统邮件发送控制器", tags = "系统邮件发送控制器")
@RequestMapping("/sysmail/email")
public class EmailController {

    @Autowired
    private EmailUtils emailUtils;
    @Autowired
    private EmailLogService emailLogService;


    @ApiOperation(value = "页面初始化", notes = "页面初始化")
    @GetMapping("/init")
    public String init(Model model) {
        model.addAttribute("from", emailUtils.sendAddress);
        return "mail/email/index";
    }


    @ApiOperation(value = "获取邮件模板", notes = "获取邮件模板")
    @GetMapping("/template/{id}")
    public String template(@PathVariable Integer id) {
        return "mail/templates/template" + id + "_bak";
    }


    @ApiOperation(value = "发送文本邮件", notes = "发送文本邮件")
    @PostMapping("/sendTextMail")
    @ResponseBody
    public BaseResult sendTextMail(@RequestBody EmailLogVO sysEmailLogVO) {
        BaseResult responseResult = emailUtils.sendTextMail(sysEmailLogVO.getAddress().trim(), sysEmailLogVO.getSubject().trim(), sysEmailLogVO.getContent().trim());
        sysEmailLogVO.setType("0");
        sysEmailLogVO.setStatus(0 == responseResult.getCode() ? "0" : "1");
        emailLogService.saveSysEmailLog(sysEmailLogVO);
        return responseResult;
    }


    @ApiOperation(value = "发送Html邮件", notes = "发送Html邮件")
    @PostMapping("/sendHtmlMail")
    @ResponseBody
    public BaseResult sendHtmlMail(@RequestBody EmailLogVO sysEmailLogVO) {
        BaseResult baseResult = emailUtils.sendHtmlMail(sysEmailLogVO.getAddress().trim(), sysEmailLogVO.getSubject().trim(), sysEmailLogVO.getContent().trim());
        sysEmailLogVO.setType("1");
        sysEmailLogVO.setStatus(0 == baseResult.getCode() ? "0" : "1");
        emailLogService.saveSysEmailLog(sysEmailLogVO);
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
    @ApiOperation(value = "发送图片邮件", notes = "发送图片邮件")
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
    @ApiOperation(value = "发送附件邮件", notes = "发送附件邮件")
    @PostMapping("/sendFileMail")
    @ResponseBody
    public BaseResult sendFileMail(String address, String title, String text, String filePath) {
        return emailUtils.sendAttachmentMail(address.trim(), title.trim(), text.trim(), filePath.trim());
    }


    @ApiOperation(value = "发送模板邮件", notes = "发送模板邮件")
    @PostMapping("/sendTemplateMail")
    @ResponseBody
    public BaseResult sendTemplateMail(@RequestBody EmailLogVO sysEmailLogVO) {
        BaseResult baseResult = emailUtils.sendTemplateMail(sysEmailLogVO.getAddress().trim(), sysEmailLogVO.getSubject().trim(),
                sysEmailLogVO.getTemplate().trim(), sysEmailLogVO.getContent().trim());
        sysEmailLogVO.setType("4");
        sysEmailLogVO.setStatus(0 == baseResult.getCode() ? "0" : "1");
        emailLogService.saveSysEmailLog(sysEmailLogVO);
        return baseResult;
    }

}
