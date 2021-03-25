package com.service.impl;

import com.domain.VoteCandidate;
import com.mapper.VoteCandidateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mapper.VoteRecordsMapper;
import com.domain.VoteRecords;
import com.service.VoteRecordsService;
@Service
public class VoteRecordsServiceImpl extends ServiceImpl<VoteRecordsMapper, VoteRecords> implements VoteRecordsService{

    @Autowired
    private VoteCandidateMapper voteCandidateMapper;

    @Autowired
    private VoteRecordsMapper voteRecordsMapper;

    @Override
    public Integer voteForCandidate(Long userID, Long candidateTableID) {
        VoteCandidate voteCandidate = voteCandidateMapper.selectById(candidateTableID);
        VoteRecords voteRecords = VoteRecords.builder()
                .voteId(voteCandidate.getVoteId())
                .voteCandidateTableId(candidateTableID)
                .ownerId(userID)
                .build();
        voteRecordsMapper.insert(voteRecords);
        return 1;
    }
}
