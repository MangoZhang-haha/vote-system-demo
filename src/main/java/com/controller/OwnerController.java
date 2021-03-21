package com.controller;

import com.domain.Owner;
import com.domain.Result;
import com.service.OwnerService;
import com.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mango
 * @Date: 2021/3/20 19:41:59
 */
@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/{ownerID}")
    public Result getOwnerInfo(@PathVariable("ownerID") Long ownerID) {
        Owner owner = ownerService.getById(ownerID);
        if (owner == null) {
            return ResultUtil.error("该住户不存在");
        }
        return ResultUtil.success(ownerService.getOwnerInfoByOwnerID(ownerID));
    }
}
