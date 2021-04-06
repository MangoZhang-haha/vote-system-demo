package com.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.domain.Owner;
import com.domain.Result;
import com.domain.XUser;
import com.service.OwnerService;
import com.service.XRoleService;
import com.service.XUserRoleService;
import com.service.XUserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mango
 * @Date: 2021/3/20 14:33:00
 */
@RestController
@Api(tags = "登陆控制器")
public class LoginController {

    /**
     * 业主委员会 标识
     */
    private String OWNERS_COMMITTEE = "owners_committee";

    @Value("${alibaba.face.default-db-name}")
    private String defaultDbName;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private FaceUtil faceUtil;

    @Autowired
    private OSSClientUtil ossClientUtil;

    @Autowired
    private XUserService xUserService;

    @Autowired
    private XUserRoleService xUserRoleService;

    @Autowired
    private XRoleService xRoleService;

    @ApiOperation("用户登陆")
    @PostMapping("/login")
    public Result login(@RequestParam("loginAccount") @ApiParam("登陆账号（手机号|身份证号）") String loginAccount,
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
            return ResultUtil.error("登陆账号只能为手机号和身份证号");
        }
        Owner owner = null;
        XUser xUser = null;
        switch (type) {
            case 1:
                owner = ownerService.getOne(
                        Wrappers.lambdaQuery(Owner.class)
                                .eq(Owner::getMobilephone, loginAccount)
                );
                xUser = xUserService.getOne(
                        Wrappers.lambdaQuery(XUser.class)
                                .eq(XUser::getPhone, loginAccount)
                );
                break;
            case 2:
                owner = ownerService.getOne(
                        Wrappers.lambdaQuery(Owner.class)
                                .eq(Owner::getIdNumber, loginAccount)
                );
                xUser = xUserService.getOne(
                        Wrappers.lambdaQuery(XUser.class)
                                .eq(XUser::getIdNumber, loginAccount)
                );
                break;
            default:
                break;
        }

        if (owner != null || xUser != null) {
            String url = FileUtils.uploadTmp(multipartFile);
            System.out.println("url = " + url);
            url = "https://www.hah-mango.com/res" + url;
            String fileName = url.substring(url.lastIndexOf(File.separator) + 1);
            String ossFileUrl = ossClientUtil.uploadWebFile(url, fileName);
            System.out.println("ossFileUrl = " + ossFileUrl);
            Boolean haveFace = faceUtil.DetectFace(ossFileUrl);
            List<String> faceIDs = faceUtil.searchFace(defaultDbName, ossFileUrl);
            if (!haveFace) {
                return ResultUtil.success("照片中没找到人脸");
            }
            Map<String, Object> resultMap = new HashMap<>(2);
            if (owner != null) {
                if (faceIDs.contains(owner.getFaceId())) {
                    Long times = owner.getLoginTimes();
                    times++;
                    ownerService.update(
                            Wrappers.lambdaUpdate(Owner.class)
                                    .set(Owner::getLastestLoginTime, new Date())
                                    .set(Owner::getLoginTimes, times)
                                    .eq(Owner::getId, owner.getId())
                    );
                    resultMap.put("info", owner);
                    resultMap.put("isOwner", true);
                    return ResultUtil.success(resultMap);
                } else {
                    return ResultUtil.error("人脸不匹配");
                }
            } else {
                if (faceIDs.contains(xUser.getFaceId())) {
                    Long times = xUser.getLoginTimes();
                    times++;
                    xUserService.update(
                            Wrappers.lambdaUpdate(XUser.class)
                                    .set(XUser::getLastestLoginTime, new Date())
                                    .set(XUser::getLoginTimes, times)
                                    .eq(XUser::getId, owner.getId())
                    );
                    resultMap.put("info", xUser);
                    resultMap.put("isOwner", false);
                    return ResultUtil.success(resultMap);
                } else {
                    return ResultUtil.error("人脸不匹配");
                }
            }
        } else {
            return ResultUtil.error("该账户不存在");
        }
    }

    @ApiOperation("检查用户账号是否存在")
    @GetMapping("/checkAccount")
    public Result checkAccount(@RequestParam("loginAccount") @ApiParam("登陆账号（手机号|身份证号）") String loginAccount) {
        Integer type = null;
        if (PhoneUtil.isMobile(loginAccount)) {
            type = 1;
        } else if (IdcardUtil.isValidCard18(loginAccount)) {
            type = 2;
        } else {
            return ResultUtil.error("登陆账号只能为手机号和身份证号");
        }

        Owner owner = null;
        XUser xUser = null;
        switch (type) {
            case 1:
                owner = ownerService.getOne(
                        Wrappers.lambdaQuery(Owner.class)
                                .eq(Owner::getMobilephone, loginAccount)
                );
                xUser = xUserService.getOne(
                        Wrappers.lambdaQuery(XUser.class)
                                .eq(XUser::getPhone, loginAccount)
                );
                break;
            case 2:
                owner = ownerService.getOne(
                        Wrappers.lambdaQuery(Owner.class)
                                .eq(Owner::getIdNumber, loginAccount)
                );
                xUser = xUserService.getOne(
                        Wrappers.lambdaQuery(XUser.class)
                                .eq(XUser::getIdNumber, loginAccount)
                );
                break;
            default:
                break;
        }

        if (owner != null || xUser != null) {
            return ResultUtil.success("该用户存在");
        } else {
            return ResultUtil.error("该账户不存在");
        }
    }
}
