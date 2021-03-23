package com.service;

import com.domain.Vote;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface VoteService extends IService<Vote> {

    List<Vote> getInfo();
}

