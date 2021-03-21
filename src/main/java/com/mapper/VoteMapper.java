package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Vote;

import java.util.List;

public interface VoteMapper extends BaseMapper<Vote> {

    List<Vote> getInfo();
}