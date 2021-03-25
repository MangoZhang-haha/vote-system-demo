package com.service;

import com.domain.Vote;
import com.baomidou.mybatisplus.extension.service.IService;
import com.domain.VoteCandidate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface VoteService extends IService<Vote> {

    String getNoticedIDs(String json);

    @Transactional(rollbackFor = Exception.class)
    Integer createVote(Vote vote, List<VoteCandidate> candidateList);

    @Transactional(rollbackFor = Exception.class)
    Integer deleteVote(Long voteID);
}


