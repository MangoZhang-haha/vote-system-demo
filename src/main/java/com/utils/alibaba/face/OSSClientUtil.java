package com.utils.alibaba.face;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.common.utils.IOUtils;
import com.aliyun.oss.model.*;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author Mango
 * @Date: 2021/3/25 16:06:49
 */
@Slf4j
@Component
public class OSSClientUtil {

    @Value("${alibaba.face.access-key-id}")
    private String accessKeyID;

    @Value("${alibaba.face.access-key-secret}")
    private String accessKeySecret;

    @Value("${alibaba.face.region-id}")
    private String regionID;

    @Value("${alibaba.face.bucket-name}")
    private String bucketName;

    @Value("${alibaba.face.access-url}")
    private String accessUrl;

    @Value("${alibaba.face.endpoint}")
    private String endpoint;

    private static OSSClient ossClient;

    /**
     * @desc 静态初始化ossClient
     **/
    @PostConstruct
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyID, accessKeySecret);
    }

    /**
     * 将网络图片放到OSS下存储
     * @param fileUrl
     * @param fileName
     * @return
     */
    public String uploadWebFile(String fileUrl, String fileName) {
        String key = fileName;
        InputStream is = null;
        try {
            Integer length = new URL(fileUrl).openConnection().getContentLength();
            is = new URL(fileUrl).openStream();
            ObjectMetadata objectMetadata = getObjectMetadata(length);
            ossClient.putObject(bucketName, key, is, objectMetadata);
            ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        } finally {
            IOUtils.safeClose(is);
        }
        return "https://" + accessUrl + "/" + key;
    }

    /**
     * @desc ObjectMetaData是用户对该object的描述，
     * 由一系列name-value对组成；其中ContentLength是必须设置的，以便SDK可以正确识别上传Object的大小
     **/
    private ObjectMetadata getObjectMetadata(long length) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(length);
        // 被下载时网页的缓存行为
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        return objectMetadata;
    }
}
