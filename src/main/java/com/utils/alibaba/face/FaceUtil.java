package com.utils.alibaba.face;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.facebody.model.v20191230.*;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mango
 * @Date: 2021/3/26 0:11:40
 */
@Component
public class FaceUtil {

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

    private IAcsClient client;

    @PostConstruct
    public void init() {
        DefaultProfile profile = DefaultProfile.getProfile(regionID, accessKeyID, accessKeySecret);
        client = new DefaultAcsClient(profile);
    }

    /**
     * 创建人脸数据库
     * @param dbName
     */
    public void createFaceDb(String dbName) {
        CreateFaceDbRequest request = new CreateFaceDbRequest();
        request.setRegionId(regionID);
        request.setName(dbName);
        try {
            CreateFaceDbResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    /**
     * 添加人脸样本
     * @param dbName
     * @param label
     * @return
     */
    public String addFaceEntity(String dbName, String label) {
        AddFaceEntityRequest request = new AddFaceEntityRequest();
        request.setRegionId(regionID);
        request.setDbName(dbName);
        request.setEntityId(UUID.randomUUID().toString().replaceAll("-", ""));
        request.setLabels(label);
        AddFaceEntityResponse response = null;
        try {
            response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return request.getEntityId();
    }

    /**
     * 添加人脸数据
     *
     * @param dbName
     * @param imageUrl
     * @param entityId
     * @param extraData
     */
    public String addFace(String dbName, String imageUrl, String entityId, String extraData) {
        AddFaceRequest request = new AddFaceRequest();
        request.setRegionId(regionID);
        request.setDbName(dbName);
        request.setImageUrl(imageUrl);
        request.setEntityId(entityId);
        request.setExtraData(extraData);
        String result = null;
        try {
            AddFaceResponse response = client.getAcsResponse(request);
            result = JSONObject.toJSONString(response);
            System.out.println(JSONObject.toJSONString(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject data = JSONObject.parseObject(JSONObject.toJSONString(jsonObject.get("data")));
        return data.getString("faceId");
    }

    /**
     * 活体检测  检测是否是直接拍摄的人脸图片  还是翻拍的图片
     *
     * @param imageUrl
     */
    public void detectLiving(String imageUrl) {
        DetectLivingFaceRequest request = new DetectLivingFaceRequest();
        request.setRegionId(regionID);
        try {
            List<DetectLivingFaceRequest.Tasks> tasksList = new ArrayList<DetectLivingFaceRequest.Tasks>();
            DetectLivingFaceRequest.Tasks tasks1 = new DetectLivingFaceRequest.Tasks();
            tasks1.setImageURL(imageUrl);
            tasksList.add(tasks1);
            request.setTaskss(tasksList);
            DetectLivingFaceResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    /**
     * 人脸检测   检测是否是人脸图片
     *
     * @param imageUrl
     */
    public Boolean DetectFace(String imageUrl) {
        Boolean flag = true;
        DetectFaceRequest request = new DetectFaceRequest();
        request.setRegionId(regionID);
        request.setImageURL(imageUrl);
        try {
            DetectFaceResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            flag = false;
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return flag;
    }

    /**
     * 人脸搜索
     * 比较人脸数据库中的人脸数据，返回相近的数据
     *
     * @param dbName
     * @param imageUrl
     */
    public List<String> searchFace(String dbName, String imageUrl) {
        SearchFaceRequest request = new SearchFaceRequest();
        request.setRegionId(regionID);
        request.setDbName(dbName);
        request.setImageUrl(imageUrl);
        //返回最高匹配度的一个值
        request.setLimit(1);
        List<String> faceIDs = new ArrayList<>();
        try {
            SearchFaceResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(response));
            JSONArray matchList = jsonObject.getJSONObject("data").getJSONArray("matchList");

            JSONArray faceItems = JSONObject.parseObject(matchList.get(0).toString()).getJSONArray("faceItems");
            faceItems.forEach(item -> {
                JSONObject object = JSONObject.parseObject(JSONObject.toJSONString(item));
                faceIDs.add(object.getString("faceId"));
            });

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return faceIDs;
    }

    /**
     * 人脸比对
     * 人脸搜索是1：N，而人脸比对是1：1
     *
     * @param ImageURLA
     * @param ImageURLB
     */
    public void faceThan(String ImageURLA, String ImageURLB) {
        CompareFaceRequest request = new CompareFaceRequest();
        request.setRegionId(regionID);
        request.setImageURLA(ImageURLA);
        request.setImageURLB(ImageURLB);
        try {
            CompareFaceResponse response = client.getAcsResponse(request);
            System.out.println(new Gson().toJson(response));
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
