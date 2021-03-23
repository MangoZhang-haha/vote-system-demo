package com.controller;

import com.domain.Result;
import com.service.RangeService;
import com.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mango
 * @Date: 2021/3/23 15:02:03
 */
@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private RangeService rangeService;

    @GetMapping("/getRange")
    public Result getRange() {
        return ResultUtil.success(rangeService.getRange());
    }
}
