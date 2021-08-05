package com.liuxingyu.meco.common.utils;

import com.liuxingyu.meco.common.utils.lang.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * @author liuxingyu01
 * @date 2020-07-22-19:20
 **/
public class FileUtils {

    /**
     * @param base64Str
     * @param imgFilePath
     * @return
     */
    private File Base64ToImage(String base64Str, String imgFilePath, String fileName) {
        try {
            File resultFile = new File(imgFilePath, fileName);
            // 文件不存在则新建
            if (!resultFile.exists()) {
                resultFile.getParentFile().mkdirs();
                resultFile.createNewFile();
            }
            if (StringUtils.isEmpty(base64Str)) {
                return null;
            }

            // BASE64 解码
            byte[] b = Base64.getDecoder().decode(base64Str);
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(resultFile);
            out.write(b);
            out.flush();
            out.close();
            return resultFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 判断文件是否为图片
     *
     * @fileSuffix 文件后缀
     */
    public static boolean isPicture(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        String fileSuffix = getFileSuffix(fileName);
        String[] arr = {"bmp", "dib", "gif", "jfif", "jpe", "jpeg", "jpg", "png", "tif", "tiff", "ico"};
        for (String item : arr) {
            if (item.equals(fileSuffix)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取文件名
     *
     * @param originalFilename
     * @return 例如：test.jpg  返回：  test
     */
    public static String getFilePrefix(String originalFilename) {
        if (originalFilename.contains(".")) {
            int splitIndex = originalFilename.lastIndexOf(".");
            return originalFilename.substring(0, splitIndex);
        }
        return originalFilename;
    }


    /**
     * 获取文件后缀
     *
     * @param originalFilename
     * @return 例如：test.jpg  返回：  jpg
     */
    public static String getFileSuffix(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }
        String[] arr = originalFilename.split("\\.");
        if (arr.length == 1) {
            return "";
        }
        return arr[arr.length - 1];
    }



    /**
     * 获取文件扩展名(返回小写)
     *
     * @return 例如：test.jpg  返回：  jpg
     * @parampathname 文件名
     */
    public static String getFileExtension(String fileName) {
        if ((fileName == null) || (fileName.lastIndexOf(".") == -1)
                || (fileName.lastIndexOf(".") == fileName.length() - 1)) {
            return null;
        }
        return StringUtils.lowerCase(fileName.substring(fileName.lastIndexOf(".") + 1));
    }


    /**
     * 根据图片Base64获取文件扩展名
     *
     * @param imageBase64 图片Base64
     * @return
     */
    public static String getFileExtensionByImageBase64(String imageBase64) {
        String extension = null;
        String type = StringUtils.substringBetween(imageBase64, "data:", ";base64,");
        if (StringUtils.inStringIgnoreCase(type, "image/jpeg")) {
            extension = "jpg";
        } else if (StringUtils.inStringIgnoreCase(type, "image/gif")) {
            extension = "gif";
        } else {
            extension = "png";
        }
        return extension;
    }



    public static void main(String[] args) {
        System.out.println(getFilePrefix("dksdjasasdasdasdas.png"));
        System.out.println(getFileSuffix("dksdjasasdasdasdas.png"));
        System.out.println(getFileExtension("dksdjasasdasdasdas.png"));
    }

}
