package com.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.domain.Owner;
import com.domain.Result;
import com.service.OwnerService;
import com.utils.FileUtils;
import com.utils.ResultUtil;
import com.utils.alibaba.face.FaceUtil;
import com.utils.alibaba.face.OSSClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Mango
 * @Date: 2021/3/20 14:33:00
 */
@RestController
@Api(tags = "登陆控制器")
public class LoginController {

    @Value("${alibaba.face.default-db-name}")
    private String defaultDbName;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private FaceUtil faceUtil;

    @Autowired
    private OSSClientUtil ossClientUtil;

    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public Result login(@RequestParam("loginAccount") @ApiParam("登陆账号（手机号|身份证号|用户姓名）") String loginAccount,
                        @RequestParam(value = "facePicFile", required = false) @ApiParam("人脸图片文件") MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            return ResultUtil.error("请选择文件上传");
        }
        Integer type = null;
        if (PhoneUtil.isMobile(loginAccount)) {
            type = 1;
        } else if (IdcardUtil.isValidCard18(loginAccount)) {
            type = 2;
        } else {
            type = 3;
        }

        Owner owner = null;
        switch (type) {
            case 1:
                 owner = ownerService.getOne(
                         Wrappers.lambdaQuery(Owner.class)
                                 .eq(Owner::getMobilephone, loginAccount)
                 );
                break;
            case 2:
                owner = ownerService.getOne(
                        Wrappers.lambdaQuery(Owner.class)
                                .eq(Owner::getIdNumber, loginAccount)
                );
                break;
            case 3:
                owner = ownerService.getOne(
                    Wrappers.lambdaQuery(Owner.class)
                            .eq(Owner::getOwnerName, loginAccount)
                );
                break;
            default:
                break;
        }
        if (owner != null) {
            String url = FileUtils.uploadTmp(multipartFile);
            System.out.println("url = " + url);
            url = "http://82.156.199.7/res" + url;
            String fileName = url.substring(url.lastIndexOf(File.separator) + 1);
            String ossFileUrl = ossClientUtil.uploadWebFile(url, fileName);
            Boolean haveFace = faceUtil.DetectFace(ossFileUrl);
            if (!haveFace) {
                return ResultUtil.success("照片中没找到人脸");
            }
            List<String> faceIDs = faceUtil.searchFace(defaultDbName, ossFileUrl);
            if (faceIDs.contains(owner.getFaceId())) {
                Long times = owner.getLoginTimes();
                times++;
                ownerService.update(
                        Wrappers.lambdaUpdate(Owner.class)
                                .set(Owner::getLastestLoginTime, new Date())
                                .set(Owner::getLoginTimes, times)
                                .eq(Owner::getId, owner.getId())
                );
                return ResultUtil.success(owner);
            } else {
                return ResultUtil.error("人脸不匹配");
            }
        } else {
            return ResultUtil.error("该账户不存在");
        }
    }
}
