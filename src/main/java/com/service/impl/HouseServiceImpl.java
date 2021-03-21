package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.House;
import com.mapper.HouseMapper;
import com.service.HouseService;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

}

