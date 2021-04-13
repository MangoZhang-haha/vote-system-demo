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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mango
 * @Date: 2021/3/25 16:06:49
 */
@Slf4j
@Component
public class OSSClientUtil {

    @Value("${alibaba.face.access-key-id}")
    public String accessKeyID;

    @Value("${alibaba.face.access-key-secret}")
    public String accessKeySecret;

    @Value("${alibaba.face.region-id}")
    public String regionID;

    @Value("${alibaba.face.bucket-name}")
    public String bucketName;

    @Value("${alibaba.face.access-url}")
    public String accessUrl;

    @Value("${alibaba.face.endpoint}")
    public String endpoint;

    /**
     * 设置bucket公共读
     */
    public void setBucketPublic() {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyID, accessKeySecret);
        ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    /**
     * 将网络图片放到OSS下存储
     * @param fileUrl
     * @param fileName
     * @return
     */
    public String uploadWebFile(String fileUrl, String fileName) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyID, accessKeySecret);
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

    /**
     * 罗列出oss中单个bucket中的文件（默认是100个）
     */
    public List<String> listFiles(String bucketName) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyID, accessKeySecret);
        List<String> result = new ArrayList<>();
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        List<OSSObjectSummary> objectSummaries = objectListing.getObjectSummaries();
        objectSummaries.forEach(ossObjectSummary -> {
            result.add(ossObjectSummary.getKey());
        });
        return result;
    }

    /**
     * 批量删除oss中单个bucket中的文件
     * @param bucketName
     * @param keys
     * @return
     */
    public List<String> deleteFiles(String bucketName, List<String> keys) {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyID, accessKeySecret);
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();

        //关闭ossClient
        ossClient.shutdown();
        return deletedObjects;
    }
}
