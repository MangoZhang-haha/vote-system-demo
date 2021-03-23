package com.service.impl;

import com.domain.VoteCandidate;
import com.mapper.VoteCandidateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.Vote;
import com.mapper.VoteMapper;
import com.service.VoteService;

@Service
public class VoteServiceImpl extends ServiceImpl<VoteMapper, Vote> implements VoteService {

    @Autowired
    private VoteMapper voteMapper;
    @Autowired
    private VoteCandidateMapper voteCandidateMapper;

    @Override
    public List<Vote> getInfo() {
        return voteMapper.getInfo();
    }

    @Override
    public Integer createVote(Vote vote, List<VoteCandidate> candidateList) {
        voteMapper.insert(vote);
        for (VoteCandidate candidate : candidateList){
            candidate.setVoteId(vote.getId());
        }
        return voteCandidateMapper.insertList(candidateList,new Date());
    }
}

