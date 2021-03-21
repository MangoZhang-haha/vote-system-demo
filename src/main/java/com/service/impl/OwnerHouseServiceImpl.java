package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mapper.OwnerHouseMapper;
import com.domain.OwnerHouse;
import com.service.OwnerHouseService;
@Service
public class OwnerHouseServiceImpl extends ServiceImpl<OwnerHouseMapper, OwnerHouse> implements OwnerHouseService{

}
