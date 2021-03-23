package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.VoteCandidate;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface VoteCandidateMapper extends BaseMapper<VoteCandidate> {
    
    Integer insertList(@Param("list")List<VoteCandidate> list , @Param("now")Date now);

}