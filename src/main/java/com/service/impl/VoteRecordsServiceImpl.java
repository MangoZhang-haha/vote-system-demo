package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mapper.VoteRecordsMapper;
import com.domain.VoteRecords;
import com.service.VoteRecordsService;
@Service
public class VoteRecordsServiceImpl extends ServiceImpl<VoteRecordsMapper, VoteRecords> implements VoteRecordsService{

}
