package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.NoticeOwner;
import com.mapper.NoticeOwnerMapper;
import com.service.NoticeOwnerService;
import org.springframework.stereotype.Service;

@Service
public class NoticeOwnerServiceImpl extends ServiceImpl<NoticeOwnerMapper, NoticeOwner> implements NoticeOwnerService {

}
