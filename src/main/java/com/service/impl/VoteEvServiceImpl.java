package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.VoteEv;
import com.mapper.VoteEvMapper;
import com.service.VoteEvService;

@Service
public class VoteEvServiceImpl extends ServiceImpl<VoteEvMapper, VoteEv> implements VoteEvService {

}

