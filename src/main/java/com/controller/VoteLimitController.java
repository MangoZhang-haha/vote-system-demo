package com.controller;

import com.domain.Result;
import com.service.VoteLimitService;
import com.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mango
 * @Date: 2021/4/1 14:26:17
 */
@RestController
@RequestMapping("/limit")
@Api(tags = "投票限制类型控制器")
public class VoteLimitController {

    @Autowired
    private VoteLimitService voteLimitService;

    @ApiOperation("获取所有的投票限制类型(0 停用 1 启用)")
    @GetMapping
    public Result getAll() {
        return ResultUtil.success(voteLimitService.list());
    }
}
