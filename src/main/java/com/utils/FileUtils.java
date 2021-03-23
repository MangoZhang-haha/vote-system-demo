package com.utils;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Mango
 * @Date: 2021/3/22 15:10:44
 */
@Configuration
public class FileUtils {

    public static String TMP_PATH;
    @Value("${file.tmp}")
    public void setTmpPath(String tmpPath) {
        TMP_PATH = tmpPath;
    }

    public static String APPLICATION_NAME;
    @Value("${spring.application.name}")
    public void setApplicationName(String name) {
        APPLICATION_NAME = name;
    }

    public static String uploadTmp(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String realName = UUID.randomUUID() + suffix;
        File tmp = new File(TMP_PATH, realName);
        org.apache.commons.io.FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), tmp);
        String path = tmp.getAbsolutePath();
        path = path.substring(path.indexOf(APPLICATION_NAME) - File.separator.length());
        return path;
    }

    public static void deleteTmp(String path) {
        String fileName = path.substring(path.lastIndexOf(File.separator) + 1);
        File tmp = new File(TMP_PATH, fileName);
        if (tmp.exists()) {
            tmp.delete();
        }
    }
}
