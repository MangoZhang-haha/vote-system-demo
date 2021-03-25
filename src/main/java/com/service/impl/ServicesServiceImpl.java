package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.Services;
import com.mapper.ServicesMapper;
import com.service.ServicesService;

@Service
public class ServicesServiceImpl extends ServiceImpl<ServicesMapper, Services> implements ServicesService {

}

