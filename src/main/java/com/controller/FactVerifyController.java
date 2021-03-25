package com.controller;

import com.domain.Result;
import com.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mango
 * @Date: 2021/3/25 22:38:38
 */
@RestController
@RequestMapping("/face")
@Api(tags = "人脸识别控制器")
public class FactVerifyController {

    @Value("${face-verify.is-show}")
    private Boolean isShow;

    @ApiOperation("人脸识别模块是否展示")
    @GetMapping("/isShow")
    public Result faceModuleIsShow() {
        return ResultUtil.success(isShow);
    }
}
