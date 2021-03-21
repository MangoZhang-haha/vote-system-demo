package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mapper.CommunityServiceMapper;
import com.domain.CommunityService;
import com.service.CommunityServiceService;
@Service
public class CommunityServiceServiceImpl extends ServiceImpl<CommunityServiceMapper, CommunityService> implements CommunityServiceService{

}
