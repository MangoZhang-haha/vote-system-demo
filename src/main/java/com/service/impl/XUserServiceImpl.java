package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.XUser;
import com.mapper.XUserMapper;
import com.service.XUserService;

@Service
public class XUserServiceImpl extends ServiceImpl<XUserMapper, XUser> implements XUserService {

}

