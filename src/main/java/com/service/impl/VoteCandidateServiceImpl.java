package com.service.impl;

import com.domain.VoteRecords;
import com.mapper.VoteRecordsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mapper.VoteCandidateMapper;
import com.domain.VoteCandidate;
import com.service.VoteCandidateService;
@Service
public class VoteCandidateServiceImpl extends ServiceImpl<VoteCandidateMapper, VoteCandidate> implements VoteCandidateService{


}
