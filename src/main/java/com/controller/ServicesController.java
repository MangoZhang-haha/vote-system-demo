package com.controller;

import com.domain.Result;
import com.domain.Services;
import com.service.PublicService;
import com.service.ServicesService;
import com.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Mango
 * @Date: 2021/3/20 21:37:49
 */
@RestController
@RequestMapping("/services")
@Api(tags = "小区服务控制器")
public class ServicesController {

    @Autowired
    private ServicesService servicesService;

    @Autowired
    private PublicService publicService;

    @ApiOperation("获取所有的小区服务")
    @GetMapping
    public Result getAll() {
        String prefix = publicService.getStaticResPrefixUrl();
        List<Services> list = servicesService.list();
        list.forEach(services -> {
            String suffix = services.getIconUrl();
            services.setIconUrl(prefix + suffix);
        });
        return ResultUtil.success(list);
    }
}
