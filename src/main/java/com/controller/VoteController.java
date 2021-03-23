package com.controller;

import com.domain.Result;
import com.domain.Vote;
import com.utils.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mango
 * @Date: 2021/3/21 11:54:49
 */
@RestController
@RequestMapping("/vote")
public class VoteController {

    @PostMapping
    public Result create(@RequestBody Vote vote) {

        return ResultUtil.success();
    }
}
