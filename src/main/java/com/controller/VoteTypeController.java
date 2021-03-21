package com.controller;

import com.domain.Result;
import com.service.VoteTypeService;
import com.utils.ResultUtil;
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
public class VoteTypeController {

    @Autowired
    private VoteTypeService voteTypeService;

    @GetMapping
    public Result getAll() {
        return ResultUtil.success(voteTypeService.list());
    }
}
