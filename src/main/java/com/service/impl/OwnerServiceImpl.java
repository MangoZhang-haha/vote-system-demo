package com.service.impl;

import com.pojo.OwnerInfo;
import com.pojo.mapper.OwnerInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.Owner;
import com.mapper.OwnerMapper;
import com.service.OwnerService;

@Service
public class OwnerServiceImpl extends ServiceImpl<OwnerMapper, Owner> implements OwnerService {

    @Autowired
    private OwnerInfoMapper ownerInfoMapper;

    @Autowired
    private OwnerMapper ownerMapper;

    @Override
    public OwnerInfo getOwnerInfoByOwnerID(Long ownerID) {
        OwnerInfo ownerInfo = ownerInfoMapper.queryOwnerInfoByOwnerID(ownerID);
        ownerInfo.setAddrDetail(
                ownerInfo.getCommunityName() + ownerInfo.getBuildingName() + ownerInfo.getUnitName() + ownerInfo.getHouseNumber()
        );

        ownerInfo.setWhetherAuth(ownerInfo.getFaceID() == null ? false : true);
        ownerInfo.setFaceID(null);
        return ownerInfo;
    }
}



