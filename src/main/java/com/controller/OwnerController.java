package com.controller;

import cn.hutool.core.util.CharUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.domain.Owner;
import com.domain.Result;
import com.domain.Vote;
import com.service.OwnerService;
import com.service.VoteService;
import com.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Mango
 * @Date: 2021/3/20 19:41:59
 */
@RestController
@RequestMapping("/owner")
@Api(tags = "住户控制器")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private VoteService voteService;

    @ApiOperation("获取当前用户的详情信息")
    @GetMapping("/{ownerID}")
    public Result getOwnerInfo(@PathVariable("ownerID") @ApiParam("当前用户ID") Long ownerID) {
        Owner owner = ownerService.getById(ownerID);
        if (owner == null) {
            return ResultUtil.error("该住户不存在");
        }
        return ResultUtil.success(ownerService.getOwnerInfoByOwnerID(ownerID));
    }

    @ApiOperation("获取业主的所有亲属")
    @GetMapping("/getRelatives/{ownerID}")
    public Result getRelatives(@PathVariable("ownerID") Long ownerID) {
        List<Owner> relatives = ownerService.list(
                Wrappers.lambdaQuery(Owner.class)
                        .eq(Owner::getOid, ownerID)
        );
        return ResultUtil.success(relatives);
    }

    @ApiOperation("委托亲友代替投票")
    @PutMapping("/{voteID}/{relativeID}")
    public Result replace(@PathVariable("voteID") @ApiParam("投票项目ID") Long voteID,
                          @PathVariable("relativeID") @ApiParam("亲友ID") Long relativeID) {
        Vote vote = voteService.getById(voteID);
        String ownerNoticedIds = vote.getOwnerNoticedIds();
        StringBuffer append = new StringBuffer(ownerNoticedIds).append(CharUtil.COMMA).append(relativeID);
        voteService.update(
                Wrappers.lambdaUpdate(Vote.class)
                        .set(Vote::getOwnerNoticedIds, append.toString())
                        .eq(Vote::getId, voteID)
        );
        return ResultUtil.success();
    }
}
