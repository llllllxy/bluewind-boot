package com.bluewind.boot.module.common.fileupload.controller;

import com.bluewind.boot.common.annotation.LogAround;
import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.common.utils.storage.api.StorageService;
import com.bluewind.boot.common.utils.storage.model.StorageFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxingyu01
 * @date 2021-03-27-15:35
 * @description 文件操作公共控制器
 **/
@Api(value = "文件操作公共控制器", tags = "文件操作公共控制器")
@Controller
@RequestMapping("/fileupload")
public class FileUploadController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private StorageService storageService;


    @LogAround("上传文件")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    @RequestMapping(value = "/put", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult put(@RequestParam("files") MultipartFile file,
                          @RequestParam(required = false, defaultValue = "", value = "fileName") String fileName) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("FileUploadController -- put -- fileName = {}", fileName);
        }
        if (StringUtils.isEmpty(fileName)) {
            fileName = IdGenerate.uuid() + ".jpg";
        }

        StorageFile storageFile = storageService.store(file.getInputStream(), fileName);
        if (logger.isInfoEnabled()) {
            logger.info("FileUploadController -- put -- storageFile = {}", storageFile);
        }
        // 上传成功后，获取回显的url
        if (storageFile != null && storageFile.getFileId() != null) {
            // 获取回显previewUrl
            String previewUrl = storageService.getExpiryUrlById(storageFile.getFileId());

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("previewUrl", previewUrl);
            resultMap.put("fileId", storageFile.getFileId());

            return BaseResult.success("文件上传成功!", resultMap);
        } else {
            return BaseResult.failure("文件上传失败!");
        }
    }

}
