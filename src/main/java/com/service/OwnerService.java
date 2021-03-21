package com.service;

import com.domain.Owner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pojo.OwnerInfo;

public interface OwnerService extends IService<Owner> {

    OwnerInfo getOwnerInfoByOwnerID(Long ownerID);
}



