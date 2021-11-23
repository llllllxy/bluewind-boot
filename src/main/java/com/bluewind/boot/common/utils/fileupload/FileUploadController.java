package com.bluewind.boot.common.utils.fileupload;

import com.bluewind.boot.common.base.BaseController;
import com.bluewind.boot.common.base.BaseResult;
import com.bluewind.boot.common.utils.fileupload.minio.MinioStorageUtils;
import com.bluewind.boot.common.utils.idgen.IdGenerate;
import com.bluewind.boot.common.annotation.LogAround;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    /**
     * 七牛云文件上传
     *
     * @return
     */
    @LogAround("七牛云文件上传")
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

        String returnStr = QiNiuUtil.upload(file.getInputStream(), filesName);
        if (logger.isInfoEnabled()) {
            logger.info("FileUploadController -- uploadFileAjax -- returnStr = {}", returnStr);
        }
        if (returnStr != null && returnStr.startsWith("http:")) {
            return BaseResult.success("上传成功!", returnStr);
        } else {
            return BaseResult.failure("上传失败!");
        }
    }

    @LogAround("minio文件上传")
    @RequestMapping(value = "/uploadFileWithMinio", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult uploadFileWithMinio(@RequestParam("files") MultipartFile file,
                                          @RequestParam(required = false, defaultValue = "", value = "files_name") String filesName) {
        if (logger.isInfoEnabled()) {
            logger.info("uploadFileWithMinio -- uploadFileAjax -- filesName = {}", filesName);
        }
        if (StringUtils.isEmpty(filesName)) {
            filesName = IdGenerate.uuid() + ".jpg";
        }

        return MinioStorageUtils.uploadFile(file, filesName);
    }

}
