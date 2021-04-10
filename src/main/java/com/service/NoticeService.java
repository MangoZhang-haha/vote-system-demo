package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.domain.Notice;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

public interface NoticeService extends IService<Notice>, BaseService<Notice> {

    @Transactional(rollbackFor = Exception.class)
    int createNotice(Notice notice);

    @Transactional(rollbackFor = Exception.class)
    int editNotice(Notice notice);

    @Transactional(rollbackFor = Exception.class)
    int deleteNotice(Long noticeID);

    @Transactional(rollbackFor = Exception.class)
    boolean removeByIds(Collection<? extends Serializable> idList);
}




