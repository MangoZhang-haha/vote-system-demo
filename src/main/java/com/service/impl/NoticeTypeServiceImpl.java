package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.NoticeType;
import com.mapper.NoticeTypeMapper;
import com.service.NoticeTypeService;
import org.springframework.stereotype.Service;

@Service
public class NoticeTypeServiceImpl extends ServiceImpl<NoticeTypeMapper, NoticeType> implements NoticeTypeService {

}
