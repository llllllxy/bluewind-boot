package com.bluewind.boot.module.sys.sysuserinfo.controller;

import com.bluewind.boot.common.config.security.SecurityUtil;
import com.bluewind.boot.common.config.security.annotation.RequiresPermissions;
import com.bluewind.boot.common.utils.excel.ExcelPoiUtil;
import com.bluewind.boot.module.sys.sysroleinfo.service.SysRoleInfoService;
import com.bluewind.boot.module.sys.sysuserinfo.entity.SysUserInfo;
import com.bluewind.boot.module.sys.sysuserinfo.service.SysUserInfoService;
import com.bluewind.boot.module.sys.sysuserrole.service.SysUserRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.utils.DateTool;
import com.bluewind.boot.common.utils.JsonTool;
import com.bluewind.boot.common.utils.encrypt.SHA256Utils;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.common.utils.DictUtils;
import com.bluewind.boot.common.base.BaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

/**
 * @author liuxingyu01
 * @date 2021-01-09-23:59
 **/
@Controller
@RequestMapping("/sysuser")
@Api(value = "系统用户管理控制器", tags = "系统用户管理控制器")
public class SysUserInfoController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(SysUserInfoController.class);

    @Autowired
    private SysUserInfoService sysUserInfoService;

    @Autowired
    private SysRoleInfoService sysRoleInfoService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 盐
     */
    @Value("${hash.salt}")
    private String salt;

    /**
     * 查询页面初始化
     *
     * @return
     */
    @RequiresPermissions("system:user:init")
    @ApiOperation(value = "查询页面初始化", notes = "查询页面初始化")
    @RequestMapping(value = "/SysUserInfoInit", method = RequestMethod.GET)
    public String SysUserInfoInit(Model model) {
        // 获取下拉栏枚举值
        List<Map<String,String>> baseDictList = DictUtils.getDictList("user_status");
        model.addAttribute("baseDictList", baseDictList);
        return "system/sysuserinfo/sysuserinfo_list";
    }


    /**
     * 账户管理页面分页查询
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequiresPermissions("system:user:init")
    @LogAround("用户数据分页查询")
    @ApiOperation(value = "用户数据分页查询", notes = "用户数据分页查询")
    @ResponseBody
    @RequestMapping(value = "/getSysUserInfoList", method = RequestMethod.POST)
    public BaseResult getSysUserInfoList(@RequestParam("page") Integer pageNum,
                                         @RequestParam("limit") Integer pageSize,
                                         @RequestParam(required = false, defaultValue = "", value = "account") String account,
                                         @RequestParam(required = false, defaultValue = "", value = "name") String name,
                                         @RequestParam(required = false, defaultValue = "", value = "status") String status,
                                         @RequestParam(required = false, defaultValue = "", value = "sortName") String sortName,
                                         @RequestParam(required = false, defaultValue = "", value = "sortOrder") String sortOrder) {
        //查询条件得加上
        //分页查询
        PageHelper.startPage(pageNum, pageSize);
        if (logger.isInfoEnabled()) {
            logger.info("getSysUserInfoList -- 页面大小："+pageSize+"--页码:" + pageNum);
            logger.info("getSysUserInfoList -- getSysUserAccount：" + getSysUserAccount());
        }

        Map<String, String> paraMap = new HashMap<>();
        paraMap.put("account", account);
        paraMap.put("name", name);
        paraMap.put("status", status);
        paraMap.put("sortName", sortName);
        paraMap.put("sortOrder", sortOrder);

        List<SysUserInfo> accounts = sysUserInfoService.getSysUserInfoList(paraMap);

        PageInfo<SysUserInfo> pageinfo = new PageInfo<>(accounts);
        //取出查询结果
        List<SysUserInfo> rows = pageinfo.getList();
        int total = (int) pageinfo.getTotal();
        Map<String, Object> result = new HashMap<>();
        result.put(RESULT_ROWS, rows);
        result.put(RESULT_TOTLAL, total);

        return BaseResult.success(result);
    }



    /**
     * 删除一个系统用户（这里后面可能会改为逻辑删除）
     * @return
     */
    @RequiresPermissions("system:user:delete")
    @ApiOperation(value = "删除一个系统用户", notes = "删除一个系统用户")
    @RequestMapping(value="/delete/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult delete(@PathVariable String userId){
        int num = sysUserInfoService.delete(userId);
        if (num > 0) {
            return BaseResult.success("删除成功!");
        } else {
            return BaseResult.failure("删除失败!");
        }
    }



    /**
     * 批量删除系统用户
     * @return
     */
    @RequiresPermissions("system:user:delete")
    @ApiOperation(value = "批量删除系统用户", notes = "批量删除系统用户")
    @RequestMapping(value="/batchDelete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult batchDelete(@RequestBody String data){
        if (logger.isInfoEnabled()) {
            logger.info("batchDelete -- data：" + data);
        }
        List<SysUserInfo> userList = JsonTool.getListFromJsonString(data, SysUserInfo.class);
        List<String> idList = new ArrayList<>();
        userList.forEach(item -> {
            idList.add(item.getUserId());
        });
        if (logger.isInfoEnabled()) {
            logger.info("batchDelete -- idList：{}", idList);
        }

        int num = sysUserInfoService.batchDelete(idList);
        if (num > 0) {
            return BaseResult.success("批量删除成功!");
        } else {
            return BaseResult.failure("批量删除失败!");
        }
    }


    /**
     * 新增页面初始化
     *
     * @return
     */
    @RequiresPermissions("system:user:add")
    @ApiOperation(value = "用户新增页面初始化", notes = "用户新增页面初始化")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        return "system/sysuserinfo/sysuserinfo_add";
    }



    /**
     * 新增
     *
     * @return
     */
    @RequiresPermissions("system:user:add")
    @ApiOperation(value = "用户新增", notes = "用户新增")
    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doAdd(@RequestParam(value = "account") String account,
                            @RequestParam(value = "password") String password,
                            @RequestParam(required = false, defaultValue = "", value = "name") String name,
                            @RequestParam(required = false, defaultValue = "1", value = "sex") String sex,
                            @RequestParam(required = false, defaultValue = "", value = "phone") String phone,
                            @RequestParam(required = false, defaultValue = "", value = "avatar") String avatar) {
        if (logger.isInfoEnabled()) {
            logger.info("SysUserInfoController -- doAdd -- account =  {}：" + account);
        }
        if (sysUserInfoService.checkUserNameUnique(account) > 0) {
            return BaseResult.failure("新增用户'" + account + "'失败，登录账号已存在!");
        }
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(IdGenerate.nextId());
        sysUserInfo.setAccount(account);
        // 再进行加密两次，才是正确密码
        password = SHA256Utils.SHA256Encode(salt + password);
        sysUserInfo.setPassword(password);
        sysUserInfo.setName(name);
        sysUserInfo.setSex(sex);
        sysUserInfo.setPhone(phone);
        sysUserInfo.setAvatar(avatar);
        sysUserInfo.setDelFlag("0");
        sysUserInfo.setStatus("0");
        sysUserInfo.setCreateUser(getSysUserId());
        int num = sysUserInfoService.doAdd(sysUserInfo);
        if (num > 0) {
            return BaseResult.success("新增用户'" + account + "'成功!");
        } else {
            return BaseResult.failure("新增用户'" + account + "'失败!");
        }
    }


    /**
     * 修改页面初始化
     *
     * @return
     */
    @RequiresPermissions("system:user:edit")
    @ApiOperation(value = "用户修改页面初始化", notes = "用户修改页面初始化")
    @RequestMapping(value = "/update/{userId}", method = RequestMethod.GET)
    public String update(Model model, @PathVariable String userId) {
        // 获取下拉栏枚举值
        List<Map<String,String>> baseDictList = DictUtils.getDictList("user_status");
        model.addAttribute("baseDictList", baseDictList);
        SysUserInfo sysUserInfo = sysUserInfoService.getOneById(userId);
        model.addAttribute("sysUserInfo", sysUserInfo);
        return "system/sysuserinfo/sysuserinfo_update";
    }


    /**
     * 用户密码修改
     *
     * @return
     */
    @RequiresPermissions("system:user:editpassword")
    @ApiOperation(value = "用户密码修改", notes = "用户密码修改")
    @RequestMapping(value = "/updatePassword/{userId}/{password}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult updatePassword(@PathVariable(value = "userId") String userId,
                                     @PathVariable(value = "password") String password) {
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(userId);
        // 再进行加密两次，才是正确密码
        password = SHA256Utils.SHA256Encode(salt + password);
        sysUserInfo.setPassword(password);
        int num = sysUserInfoService.resetPass(sysUserInfo);
        if (num > 0) {
            return BaseResult.success("密码修改成功!");
        } else {
            return BaseResult.failure("密码修改失败!");
        }
    }


    /**
     * 用户修改
     *
     * @return
     */
    @RequiresPermissions("system:user:edit")
    @ApiOperation(value = "用户信息修改", notes = "用户信息修改")
    @RequestMapping(value = "/doUpdate", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult doUpdate(@RequestParam(value = "userId") String userId,
                               @RequestParam(value = "name") String name,
                               @RequestParam(value = "sex") String sex,
                               @RequestParam(value = "phone") String phone,
                               @RequestParam(value = "status") String status,
                               @RequestParam(required = false, defaultValue = "", value = "avatar") String avatar) {
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(userId);
        sysUserInfo.setName(name);
        sysUserInfo.setSex(sex);
        sysUserInfo.setStatus(status);
        sysUserInfo.setPhone(phone);
        sysUserInfo.setAvatar(avatar);
        sysUserInfo.setUpdateUser(getSysUserId());
        int num = sysUserInfoService.doUpdate(sysUserInfo);
        if (num > 0) {
            return BaseResult.success("修改用户成功!");
        } else {
            return BaseResult.failure("修改用户失败!");
        }
    }


    /**
     * 授权页面初始化
     *
     * @return
     */
    @RequiresPermissions("system:user:authorize")
    @ApiOperation(value = "用户授权页面初始化", notes = "用户授权页面初始化")
    @RequestMapping(value = "/authorize/{userId}", method = RequestMethod.GET)
    public String authorize(Model model, @PathVariable String userId) {
        SysUserInfo sysUserInfo = sysUserInfoService.getOneById(userId);
        model.addAttribute("sysUserInfo", sysUserInfo);
        return "system/sysuserinfo/sysuserinfo_auth";
    }


    @RequiresPermissions("system:user:authorize")
    @ApiOperation(value = "根据用户id查询用户角色", notes = "根据用户id查询用户角色")
    @RequestMapping(value = "/listRoleForSelect/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public String listRoleForSelect(@PathVariable String userId) {
        return sysRoleInfoService.listXmSelectPojo(userId);
    }


    @RequiresPermissions("system:user:authorize")
    @ApiOperation(value = "根据用户id赋予用户角色", notes = "根据用户id赋予用户角色")
    @RequestMapping(value = "doAuthorize/{userId}/{roles}", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult doAuthorize(@PathVariable("userId") String userId,
                                  @PathVariable("roles") String roles) {
        return sysUserRoleService.doAuthorize(userId, roles);
    }


    @RequiresPermissions("system:user:downloadPdf")
    @ApiOperation(value = "导出用户为pdf", notes = "导出用户为pdf")
    @RequestMapping(value = "/downloadPdf", method = RequestMethod.GET)
    @ResponseBody
    public void downloadPdf(HttpServletResponse response) {
        if (logger.isInfoEnabled()) {
            logger.info("SysUserInfoController -- downloadPdf -- start");
        }
        Map<String, String> paraMap = new HashMap<>();
        List<SysUserInfo> accountsList = sysUserInfoService.getSysUserInfoList(paraMap);

        File file = null;
        File tempFile = null;

        try {
            // 设置中文和文字格式
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font title = new Font(bfChinese, 20, Font.NORMAL);
            Font text = new Font(bfChinese, 10, Font.NORMAL);
            Font allCell = new Font(bfChinese, 13, Font.NORMAL);

            // 创建临时文件
            tempFile = File.createTempFile("String", ".pdf");
            String c = tempFile.getParent();
            String nowTime = DateTool.getCurrentTime("yyyyMMddHHmmss");
            file = new File(c + "/" + nowTime + ".pdf");
            if (logger.isInfoEnabled()) {
                logger.info("SysUserInfoController -- downloadPdf -- path = " + c + "/" + nowTime + ".pdf");
            }
            // 获取临时文件的位置
            String pdfUrl = file.getAbsolutePath();
            logger.info("SysUserInfoController -- downloadPdf -- pdfUrl = " + pdfUrl);

            // 设置纸张大小对象
            Rectangle rectangle = new Rectangle(PageSize.A4);

            // 创建一页纸
            Document document = new Document(rectangle);

            // 创建书写器
            PdfWriter.getInstance(document, new FileOutputStream(pdfUrl));
            document.open();

            // 计算页数
            int length = accountsList.size();
            int pageNum = length / 35;
            if (length % 35 != 0) {
                pageNum++;
            }

            // 当前页码
            for (int currentPage = 1; currentPage <= pageNum; currentPage++) {
                // 标题
                Paragraph test = new Paragraph("人员明细信息表", title);
                test.setFirstLineIndent(195);

                // pdf上半部分
                PdfPTable tablePlace = new PdfPTable(4);
                tablePlace.setWidthPercentage(100);
                tablePlace.setSpacingBefore(10f);
                tablePlace.setSpacingAfter(10f);

                PdfPCell date = new PdfPCell(new Phrase("导出日期:", text));
                date.disableBorderSide(15);
                tablePlace.addCell(date);

                PdfPCell reldate = new PdfPCell(new Phrase(DateTool.getToday(), text));
                reldate.disableBorderSide(15);
                tablePlace.addCell(reldate);

                PdfPCell people = new PdfPCell(new Phrase("导出人：", text));
                people.disableBorderSide(15);
                tablePlace.addCell(people);

                PdfPCell relPeople = new PdfPCell(new Phrase(SecurityUtil.getSysUserAccount(), text));
                relPeople.disableBorderSide(15);
                tablePlace.addCell(relPeople);

                // pdf主体
                PdfPTable tablePlace2 = new PdfPTable(6);
                tablePlace2.setWidthPercentage(100);
                tablePlace2.setSpacingBefore(10f);
                tablePlace2.setSpacingAfter(10f);

                PdfPCell t1 = new PdfPCell(new Phrase("账号", allCell));
                t1.setHorizontalAlignment(Element.ALIGN_CENTER);
                t1.setColspan(2);
                tablePlace2.addCell(t1);

                PdfPCell t2 = new PdfPCell(new Phrase("姓名", allCell));
                t2.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablePlace2.addCell(t2);

                PdfPCell t3 = new PdfPCell(new Phrase("联系方式", allCell));
                t3.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablePlace2.addCell(t3);

                PdfPCell t4 = new PdfPCell(new Phrase("性别", allCell));
                t4.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablePlace2.addCell(t4);

                PdfPCell t5 = new PdfPCell(new Phrase("创建时间", allCell));
                t5.setHorizontalAlignment(Element.ALIGN_CENTER);
                tablePlace2.addCell(t5);


                if (currentPage < pageNum) {
                    // 完整页面
                    for (int i = 0; i < 35; i++) {
                        SysUserInfo sysUserInfo = accountsList.get((currentPage - 1) * 35 + i);

                        PdfPCell account = new PdfPCell(new Phrase(sysUserInfo.getAccount(), allCell));
                        account.setHorizontalAlignment(Element.ALIGN_CENTER);
                        account.setColspan(2);
                        tablePlace2.addCell(account);

                        PdfPCell name = new PdfPCell(new Phrase(sysUserInfo.getName(), allCell));
                        name.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tablePlace2.addCell(name);

                        PdfPCell phone = new PdfPCell(new Phrase(sysUserInfo.getPhone(), allCell));
                        phone.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tablePlace2.addCell(phone);

                        PdfPCell sex = new PdfPCell(new Phrase(sysUserInfo.getSex() + "", allCell));
                        sex.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tablePlace2.addCell(sex);

                        PdfPCell createTime = new PdfPCell(new Phrase(sysUserInfo.getCreateTime(), allCell));
                        createTime.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tablePlace2.addCell(createTime);
                    }
                } else {
                    //最后一个页面
                    int lastPageSize = length % 35;
                    if (lastPageSize == 0) {
                        lastPageSize = 35;
                    }

                    for (int i = 0; i < lastPageSize; i++) {
                        SysUserInfo sysUserInfo = accountsList.get((currentPage - 1) * 35 + i);

                        PdfPCell account = new PdfPCell(new Phrase(sysUserInfo.getAccount(), allCell));
                        account.setHorizontalAlignment(Element.ALIGN_CENTER);
                        account.setColspan(2);
                        tablePlace2.addCell(account);

                        PdfPCell name = new PdfPCell(new Phrase(sysUserInfo.getName(), allCell));
                        name.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tablePlace2.addCell(name);

                        PdfPCell phone = new PdfPCell(new Phrase(sysUserInfo.getPhone(), allCell));
                        phone.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tablePlace2.addCell(phone);

                        PdfPCell sex = new PdfPCell(new Phrase(sysUserInfo.getSex() + "", allCell));
                        sex.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tablePlace2.addCell(sex);

                        PdfPCell createTime = new PdfPCell(new Phrase(sysUserInfo.getCreateTime(), allCell));
                        createTime.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tablePlace2.addCell(createTime);

                    }
                }

                // 页码
                Paragraph pageCode = new Paragraph(String.valueOf(currentPage) + "/" + String.valueOf(pageNum), title);
                pageCode.setFirstLineIndent(240);

                document.add(test);
                document.add(tablePlace);
                document.add(tablePlace2);
                document.add(pageCode);

                document.newPage();
            }

            // 关闭文档
            document.close();
        } catch (Exception e) {
            logger.debug("SysUserInfoController -- downloadPdf = {e}", e);
        } finally {
            try {
                if (file != null && file.exists()) {
                    InputStream is = new FileInputStream(file);
                    outPut(is, response);
                    is.close();
                    file.delete();
                }
                // 删除生成的临时pdf
                if (tempFile != null && tempFile.exists()) {
                    tempFile.delete();
                }
            } catch (Exception e) {
                logger.info("SysUserInfoController -- downloadPdf -- 下载文件出现异常 = ", e);
                response.setStatus(404);
            }

        }
    }


    private void outPut(InputStream inputStream, HttpServletResponse response) throws Exception {
        String originalFileName = IdGenerate.uuid() + ".pdf";
        String originalFileNameEncode = URLEncoder.encode(originalFileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + originalFileNameEncode + "\"");
        response.setContentType("text/plain;charset=utf-8");
        OutputStream out = response.getOutputStream();
        int ch;
        while ((ch = inputStream.read()) != -1) {
            out.write(ch);
        }
        out.flush();
        out.close();
    }


    @RequiresPermissions("system:user:exportExcel")
    @ApiOperation(value = "导出全部用户数据到excel", notes = "导出全部用户数据到excel")
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    @ResponseBody
    public void exportExcel(HttpServletResponse response) {
        if (logger.isInfoEnabled()) {
            logger.info("SysUserInfoController -- exportExcel -- start");
        }
        List<SysUserInfo> userInfoList = sysUserInfoService.getSysUserInfoList(new HashMap<>());
        Map<String,String> userStatus = DictUtils.getDictMap("user_status");
        Map<String,String> userSex = DictUtils.getDictMap("user_sex");
        for (SysUserInfo sysUserInfo: userInfoList) {
            String sex = sysUserInfo.getSex();
            sex = userSex.get(sex);
            sysUserInfo.setSex(sex);
            String status = sysUserInfo.getStatus();
            status = userStatus.get(status);
            sysUserInfo.setStatus(status);
        }

        List<String> listName = Arrays.asList("账号", "姓名", "电话", "性别", "状态", "创建时间", "更新时间");
        List<Map<String, String>> list = new ArrayList<>();
        for (SysUserInfo sysUserInfo: userInfoList) {
            Map<String, String> map = new HashMap<>();
            map.put("账号", sysUserInfo.getAccount());
            map.put("姓名", sysUserInfo.getName());
            map.put("电话", sysUserInfo.getPhone());
            map.put("性别", sysUserInfo.getSex());
            map.put("状态", sysUserInfo.getStatus());
            map.put("创建时间", sysUserInfo.getCreateTime());
            map.put("更新时间", sysUserInfo.getUpdateTime());
            list.add(map);
        }

        ExcelPoiUtil.excelPort("系统用户信息", listName, list, null, response);
    }

}
