package com.bluewind.boot.common.utils.mail;

import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.JsonTool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuxingyu01
 * @date 2021-04-16-0:01
 **/
@Component
public class EmailUtils {
    final static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

    /**
     * 发件人地址
     */
    @Value("${spring.mail.username}")
    public String SEND_ADDRESS;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;


    /**
     * 发送文本邮件
     *
     * @param address 收件人地址
     * @param title   标题
     * @param content 内容
     */
    public BaseResult sendTextMail(String address, String title, String content) {
        if (StringUtils.isBlank(address)) {
            return BaseResult.failure("收件人地址不能为空");
        }
        if (StringUtils.isBlank(title)) {
            return BaseResult.failure("主题不能为空");
        }
        if (!isEmailAddress(address)) {
            return BaseResult.failure("邮箱地址格式不正确");
        }
        try {
            // 编辑发送邮件的一些信息，有-->发件人地址，收件人地址，邮件标题，邮件正文
            SimpleMailMessage message = new SimpleMailMessage();
            // 发件人地址
            message.setFrom(SEND_ADDRESS);
            // 收件人地址，可多个，使用逗号隔开
            message.setTo(address.split(","));
            // 邮件标题
            message.setSubject(title);
            // 邮件正文
            message.setText(content);
            // private JavaMailSender mailSender;-->使用该类的send()方法发送邮件
            mailSender.send(message);
            return BaseResult.success("文本邮件发送成功！收件人" + address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.failure("文本邮件发送失败！");
    }


    /**
     * 发送Html邮件
     *
     * @param address 收件人地址
     * @param title   标题
     * @param text    内容
     */
    public BaseResult sendHtmlMail(String address, String title, String text) {
        if (StringUtils.isBlank(address)) {
            return BaseResult.failure("收件人地址不能为空");
        }
        if (StringUtils.isBlank(title)) {
            return BaseResult.failure("主题不能为空");
        }
        if (!isEmailAddress(address)) {
            return BaseResult.failure("邮箱地址格式不正确");
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // 这里与发送文本邮件有所不同
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(SEND_ADDRESS);
            helper.setTo(address.split(","));
            helper.setSubject(title);
            // 发送HTML邮件，也就是将邮件正文使用HTML的格式书写
            helper.setText(text, true);
            mailSender.send(message);
            return BaseResult.success("Html邮件发送成功！收件人" + address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.failure("Html邮件发送失败！");
    }


    /**
     * 发送附件邮件
     *
     * @param address  收件人地址
     * @param title    标题
     * @param text     内容
     * @param filePath 文件路径
     */
    public BaseResult sendAttachmentMail(String address, String title, String text, String filePath) {
        if (StringUtils.isBlank(address)) {
            return BaseResult.failure("收件人地址不能为空");
        }
        if (StringUtils.isBlank(title)) {
            return BaseResult.failure("主题不能为空");
        }
        if (!isEmailAddress(address)) {
            return BaseResult.failure("邮箱地址格式不正确");
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(SEND_ADDRESS);
            helper.setTo(address.split(","));
            helper.setSubject(title);
            helper.setText(text, true);
            // 读取文件(这里)
            FileSystemResource file = new FileSystemResource(new File(filePath));
            // 取得文件名
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            return BaseResult.success("邮件发送成功！收件人" + address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.failure("邮件发送失败！");
    }


    /**
     * 发送图片邮件
     *
     * @param address 收件人地址
     * @param title   标题
     * @param text    内容
     * @param srcPath 图片路径
     * @param srcId   图片id
     */
    public BaseResult sendInlinResourceMail(String address, String title, String text, String srcPath, String srcId) {
        if (StringUtils.isBlank(address)) {
            return BaseResult.failure("收件人地址不能为空");
        }
        if (StringUtils.isBlank(title)) {
            return BaseResult.failure("主题不能为空");
        }
        if (!isEmailAddress(address)) {
            return BaseResult.failure("邮箱地址格式不正确");
        }
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(SEND_ADDRESS);
            helper.setTo(address.split(","));
            helper.setSubject(title);
            helper.setText(text, true);
            // 读取文件
            FileSystemResource res = new FileSystemResource(new File(srcPath));
            // 赋予文件一个id值
            helper.addInline(srcId, res);
            mailSender.send(message);
            return BaseResult.success("邮件发送成功！收件人" + address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.failure("邮件发送失败！");
    }


    /**
     * 发送模板邮件
     *
     * @param address 收件人地址
     * @param title   标题
     * @param type    模板
     * @param text    内容
     */
    public BaseResult sendTemplateMail(String address, String title, String type, String text) {
        if (StringUtils.isBlank(address)) {
            return BaseResult.failure("收件人地址不能为空");
        }
        if (StringUtils.isBlank(title)) {
            return BaseResult.failure("主题不能为空");
        }
        if (!isEmailAddress(address)) {
            return BaseResult.failure("邮箱地址格式不正确");
        }
        if (!isJSONString(text)) {
            return BaseResult.failure("邮箱内容格式不正确");
        }
        try {
            Context context = new Context();
            // 这里的id与html文件中的${id}必须对应
            Map textJson = JsonTool.getMapFromJsonString(text);
            context.setVariable("param", textJson);
            textJson.forEach((k, v) -> {
                context.setVariable(k.toString(), v);
            });

            // 这里的"type"，必须是html文件的文件名
            String emailContent = templateEngine.process(type, context);
            sendHtmlMail(address, title, emailContent);
            return BaseResult.success("模板邮件发送成功！收件人" + address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BaseResult.failure("模板邮件发送失败！");
    }


    /**
     * 判断该邮件地址是否合法
     *
     * @param address 邮件地址，可以多个，逗号隔开
     * @return
     */
    public static boolean isEmailAddress(String address) {
        // 是否合法
        boolean flag = false;
        if (StringUtils.isBlank(address)) {
            return false;
        }
        try {
            String[] addressArr = address.split(",");
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = null;
            for (String str : addressArr) {
                matcher = regex.matcher(str);
                flag = matcher.matches();
                if (!flag) {
                    return false;
                }
            }
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    /**
     * 判断是否为json字符串
     *
     * @param content
     * @return
     */
    public static boolean isJSONString(String content) {
        if (StringUtils.isBlank(content)) {
            return false;
        }
        if (!content.startsWith("{") || !content.endsWith("}")) {
            return false;
        }
        Map result = JsonTool.getMapFromJsonString(content);
        if (result == null || result.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
