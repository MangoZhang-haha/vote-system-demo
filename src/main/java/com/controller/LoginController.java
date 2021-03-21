package com.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.domain.Owner;
import com.domain.Result;
import com.service.OwnerService;
import com.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Mango
 * @Date: 2021/3/20 14:33:00
 */
@RestController
public class LoginController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/login")
    public Result login(@RequestParam("loginAccount") String loginAccount,
                        @RequestParam("faceID") String faceID) {
        Integer type = null;
        if (PhoneUtil.isMobile(loginAccount)) {
            type = 1;
        } else if (IdcardUtil.isValidCard18(loginAccount)) {
            type = 2;
        } else {
            type = 3;
        }

        Owner owner = null;
        switch (type) {
            case 1:
                 owner = ownerService.getOne(
                         Wrappers.lambdaQuery(Owner.class)
                                 .eq(Owner::getMobilephone, loginAccount)
                 );
                break;
            case 2:
                owner = ownerService.getOne(
                        Wrappers.lambdaQuery(Owner.class)
                                .eq(Owner::getIdNumber, loginAccount)
                );
                break;
            case 3:
                owner = ownerService.getOne(
                    Wrappers.lambdaQuery(Owner.class)
                            .eq(Owner::getOwnerName, loginAccount)
                );
                break;
            default:
                break;
        }
        if (owner != null) {
            if (faceID.equals(owner.getFaceId())) {
                Long times = owner.getLoginTimes();
                times++;
                ownerService.update(
                        Wrappers.lambdaUpdate(Owner.class)
                                .set(Owner::getLastestLoginTime, new Date())
                                .set(Owner::getLoginTimes, times)
                                .eq(Owner::getId, owner.getId())
                );
                return ResultUtil.success(owner);
            } else {
                return ResultUtil.error("人脸不匹配");
            }
        } else {
            return ResultUtil.error("该账户不存在");
        }
    }
}
