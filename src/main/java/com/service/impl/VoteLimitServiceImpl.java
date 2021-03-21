package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mapper.VoteLimitMapper;
import com.domain.VoteLimit;
import com.service.VoteLimitService;
@Service
public class VoteLimitServiceImpl extends ServiceImpl<VoteLimitMapper, VoteLimit> implements VoteLimitService{

}
