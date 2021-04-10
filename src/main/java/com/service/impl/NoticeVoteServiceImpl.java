package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.NoticeVote;
import com.mapper.NoticeVoteMapper;
import com.service.NoticeVoteService;
@Service
public class NoticeVoteServiceImpl extends ServiceImpl<NoticeVoteMapper, NoticeVote> implements NoticeVoteService{

}
