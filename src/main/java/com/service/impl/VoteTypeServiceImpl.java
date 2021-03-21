package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.VoteType;
import com.mapper.VoteTypeMapper;
import com.service.VoteTypeService;
@Service
public class VoteTypeServiceImpl extends ServiceImpl<VoteTypeMapper, VoteType> implements VoteTypeService{

}
