package com.bluewind.boot.module.common.fileupload.controller;

import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.fileupload.api.StorageService;
import com.bluewind.boot.common.utils.fileupload.model.StorageFile;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.common.annotation.LogAround;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author liuxingyu01
 * @date 2021-03-27-15:35
 **/
@Controller
@RequestMapping("/fileupload")
public class FileUploadController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private StorageService storageService;

    /**
     * 七牛云文件上传
     *
     * @return
     */
    @LogAround("文件上传")
    @RequestMapping(value = "/uploadFileAjax", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult uploadFileAjax(@RequestParam("files") MultipartFile file,
                                     @RequestParam(required = false, defaultValue = "", value = "files_name") String filesName) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("FileUploadController -- uploadFileAjax -- filesName = {}", filesName);
        }
        if (StringUtils.isEmpty(filesName)) {
            filesName = IdGenerate.uuid() + ".jpg";
        }

        StorageFile storageFile = storageService.store(file.getInputStream(), filesName);
        if (logger.isInfoEnabled()) {
            logger.info("FileUploadController -- uploadFileAjax -- storageFile = {}", storageFile);
        }

        if (storageFile != null && storageFile.getFileId() != null) {
            // 获取回显url
            String url = storageService.getExpiryUrlById(storageFile.getFileId());
            return BaseResult.success("上传成功!", url);
        } else {
            return BaseResult.failure("上传失败!");
        }
    }

}
