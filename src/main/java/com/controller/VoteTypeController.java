package com.controller;

import com.domain.Result;
import com.service.VoteTypeService;
import com.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mango
 * @Date: 2021/3/20 21:33:06
 */
@RestController
@RequestMapping("/voteType")
@Api(tags = "投票类型控制器")
public class VoteTypeController {

    @Autowired
    private VoteTypeService voteTypeService;

    @ApiOperation("获取所欲的投票类型")
    @GetMapping
    public Result getAll() {
        return ResultUtil.success(voteTypeService.list());
    }
}
