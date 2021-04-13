package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.domain.*;
import com.enums.ResultEnum;
import com.exception.CustomException;
import com.mapper.*;
import com.service.NoticeService;
import com.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl extends BaseServiceImpl<NoticeMapper, Notice> implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private NoticeTypeMapper noticeTypeMapper;
    @Autowired
    private NoticeTypeRelationMapper noticeTypeRelationMapper;
    @Autowired
    private NoticeOwnerMapper noticeOwnerMapper;
    @Autowired
    private OwnerMapper ownerMapper;
    @Autowired
    private NoticeVoteMapper noticeVoteMapper;

    @Override
    public int createNotice(Notice notice, Long voteID) {
        notice.setAno("");
        notice.setApproved(0);
        noticeMapper.insert(notice);
        noticeMapper.update(
                null,
                Wrappers.<Notice>lambdaUpdate()
                        .set(Notice::getAno, generateAno(notice.getId()))
                        .eq(Notice::getId, notice.getId())
        );
        NoticeTypeRelation noticeTypeRelation = NoticeTypeRelation.builder()
                .noticeTypeId(notice.getTypeID())
                .noticeId(notice.getId())
                .build();
        noticeTypeRelationMapper.insert(noticeTypeRelation);

        if (voteID != null) {
            NoticeVote noticeVote = NoticeVote.builder()
                    .noticeId(notice.getId())
                    .voteId(voteID)
                    .build();
            noticeVoteMapper.insert(noticeVote);
        }
        if (notice.getPicUrl() != null){
            notice.setPicUrl(FileUtils.moveToDocument(notice.getPicUrl()));
        }
        return 1;
    }

    @Override
    public int editNotice(Notice notice) {
        if (notice.getApproved() != null) {
            notice.setApproved(null);
        }
        if (notice.getPublishTime() != null){
            notice.setPublishTime(null);
        }
        if (notice.getOperateTime() != null){
            notice.setOperateTime(null);
        }
        if (notice.getOperatorId() != null){
            notice.setOperatorId(null);
        }
        noticeMapper.updateById(notice);
        if (notice.getPicUrl() != null) {
            notice.setPicUrl(FileUtils.moveToDocument(notice.getPicUrl()));
        }
        if (notice.getId() != null) {
            noticeTypeRelationMapper.update(
                    null,
                    Wrappers.<NoticeTypeRelation>lambdaUpdate()
                            .set(NoticeTypeRelation::getNoticeTypeId, notice.getTypeID())
                            .eq(NoticeTypeRelation::getNoticeId, notice.getId())
            );
        }
        return 1;
    }

    @Override
    public int deleteNotice(Long noticeID) {
        noticeTypeRelationMapper.delete(
                Wrappers.<NoticeTypeRelation>lambdaQuery()
                        .eq(NoticeTypeRelation::getNoticeId, noticeID)
        );
        noticeOwnerMapper.delete(
                Wrappers.<NoticeOwner>lambdaQuery()
                        .eq(NoticeOwner::getNoticeId, noticeID)
        );
        return noticeMapper.deleteById(noticeID);
    }

    @Override
    public int approveNotice(Notice notice) {
        //如果审核通过
        if (notice.getApproved() == 1){
            notice.setPublishTime(new Date());
            List<Owner> ownerList = ownerMapper.selectList(new QueryWrapper<>());
            List<NoticeOwner> noticeOwnerList = new ArrayList<>();
            for (Owner owner : ownerList) {
                NoticeOwner noticeOwner = NoticeOwner.builder()
                        .noticeId(notice.getId())
                        .ownerId(owner.getId())
                        .status(0)
                        .build();
                noticeOwnerList.add(noticeOwner);
            }
            if (noticeOwnerList.size() > 0) {
                noticeOwnerMapper.addList(noticeOwnerList, new Date());
            }
        }
        return noticeMapper.updateById(notice);
    }

    public String generateAno(Long ID) {
        return "A" + String.format("%08d", ID);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        noticeTypeRelationMapper.delete(
                Wrappers.<NoticeTypeRelation>lambdaQuery()
                        .in(NoticeTypeRelation::getNoticeId, idList)
        );
        noticeOwnerMapper.delete(
                Wrappers.<NoticeOwner>lambdaQuery()
                        .in(NoticeOwner::getNoticeId, idList)
        );
        return check(baseMapper.deleteBatchIds(idList), ResultEnum.SERVICE_DELETE_ERROR);
    }

    private boolean check(int i, ResultEnum resultEnum) {
        if (!retBool(i)) {
            throw new CustomException(resultEnum);
        }
        return true;
    }
}






