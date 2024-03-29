package com.bluewind.boot.common.utils;

import com.bluewind.boot.common.utils.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;

/**
 * @author liuxingyu01
 * @date 2020-07-22-19:20
 **/
public class FileUtils {

    /**
     * base64转文件
     * @param base64Str
     * @param imgFilePath
     * @return
     */
    private File base64ToFile(String base64Str, String imgFilePath, String fileName) {
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
     * 文件转base64
     * @param filePath 文件地址
     * @return
     */
    public static String fileToBase64(String filePath) {
        if(filePath == null || "".equals(filePath)) {
            return null;
        }
        String base64 = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(filePath);
            byte[] buff = new byte[fin.available()];
            fin.read(buff);
            base64 = Base64.getEncoder().encodeToString(buff);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(StringUtils.isNotBlank(base64) && !base64.contains("data:image")){
            base64 = "data:image/png;base64," + base64;
        }
        return base64;
    }


    /**
     * 文件转base64
     * @param file 文件对象
     * @return
     */
    public static String fileToBase64(File file) {
        if(file == null) {
            return null;
        }
        String base64 = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] buff = new byte[fin.available()];
            fin.read(buff);
            base64 = Base64.getEncoder().encodeToString(buff);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(StringUtils.isNotBlank(base64) && !base64.contains("data:image")){
            base64 = "data:image/png;base64," + base64;
        }
        return base64;
    }


    /**
     * 判断文件是否为图片
     *
     * @fileSuffix 文件后缀
     */
    public static boolean isImage(String fileName) {
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
    public static String getFileExtensionByBase64(String imageBase64) {
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


    /**
     * 判断文件大小是否合法，超过指定大小返回false
     *
     * @param file MultipartFile 文件类
     * @param size 限制大小
     * @param unit 限制单位（B,K,M,G）
     */
    public static boolean fileSizeCheck(MultipartFile file, int size, String unit) {
        // 获取文件实际大小
        long len = file.getSize();
        double fileSize = 0;
        if ("B".equalsIgnoreCase(unit)) {
            fileSize = (double) len;
        } else if ("K".equalsIgnoreCase(unit)) {
            fileSize = (double) len / 1024;
        } else if ("M".equalsIgnoreCase(unit)) {
            fileSize = (double) len / 1048576;
        } else if ("G".equalsIgnoreCase(unit)) {
            fileSize = (double) len / 1073741824;
        }
        return !(fileSize > size);
    }


    /**
     * 文件流转byte字节数组
     * @param in 输入流
     * @return byte[]
     * @throws IOException
     */
    public static byte[] toByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }


    /**
     * byte数组转 InputStream 文件流
     * @param bytes byte数组
     * @return java.io.InputStream
     */
    public static InputStream byte2InputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }


    public static void main(String[] args) {
        System.out.println(getFilePrefix("dksdjasasdasdasdas.png"));
        System.out.println(getFileSuffix("dksdjasasdasdasdas.png"));
        System.out.println(getFileExtension("dksdjasasdasdasdas.png"));
    }

}
