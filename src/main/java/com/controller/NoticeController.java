package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.constant.CommonConstant;
import com.domain.*;
import com.pojo.VoteTotalInfo;
import com.service.*;
import com.utils.ResultUtil;
import com.utils.WrapperUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 公告控制器
 * @author ZQL
 */
@RequestMapping("/notice")
@RestController
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeTypeRelationService noticeTypeRelationService;
    @Autowired
    private NoticeTypeService noticeTypeService;
    @Autowired
    private NoticeVoteService noticeVoteService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private VoteCandidateService voteCandidateService;
    @Autowired
    private PublicService publicService;
    @Autowired
    private VoteRecordsService voteRecordsService;

    @GetMapping
    public Result getNotice(@RequestParam(value = "typeID",required = false)Integer typeID,
                            @RequestParam(value = "page",defaultValue = "1")Integer page,
                            @RequestParam(value = "size",defaultValue = "10")Integer size,
                            @ModelAttribute Notice notice,
                            String sorts){
        if (typeID != null){
            IPage<NoticeTypeRelation> iPage = noticeTypeRelationService.page(new Page<>(page,size),
                    Wrappers.<NoticeTypeRelation>lambdaQuery()
                            .eq(NoticeTypeRelation::getNoticeTypeId,typeID)
            );
            List<NoticeTypeRelation> records = iPage.getRecords();
            List<Long> noticeIDList = new ArrayList<>();
            for (NoticeTypeRelation relation : records){
                noticeIDList.add(relation.getNoticeId());
            }
            IPage<Notice> result = new Page<>();
            result.setCurrent(iPage.getCurrent());
            result.setPages(iPage.getPages());
            result.setSize(iPage.getSize());
            result.setTotal(iPage.getTotal());
            if (noticeIDList.size() > 0){
                QueryWrapper<Notice> wrapper = new QueryWrapper<>();
                WrapperUtil.like(wrapper,notice,new String[]{"ano","title","content"});
                wrapper.in("id",noticeIDList);
                List<Notice> noticeList = noticeService.list(wrapper);
                for (Notice notice1 : noticeList){
                    NoticeTypeRelation relation = noticeTypeRelationService.getOne(
                            Wrappers.<NoticeTypeRelation>lambdaQuery()
                                    .eq(NoticeTypeRelation::getNoticeId, notice1.getId())
                    );
                    NoticeType noticeType = noticeTypeService.getById(relation.getNoticeTypeId());
                    notice1.setTypeID(noticeType.getId());
                    notice1.setTypeName(noticeType.getTypeName());
                }
                result.setRecords(noticeList);
                return ResultUtil.success(result);
            }
            result.setRecords(new ArrayList<>());
            return ResultUtil.success(result);
        }
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        WrapperUtil.like(wrapper, notice, new String[]{"ano", "title","content"});
        Page<Notice> iPage = noticeService.page(new Page<>(page, size), wrapper, sorts);
        List<Notice> noticeList = iPage.getRecords();
        for (Notice notice1 : noticeList){
            NoticeTypeRelation relation = noticeTypeRelationService.getOne(
                    Wrappers.<NoticeTypeRelation>lambdaQuery()
                            .eq(NoticeTypeRelation::getNoticeId, notice1.getId())
            );
            NoticeType noticeType = noticeTypeService.getById(relation.getNoticeTypeId());
            notice1.setTypeID(noticeType.getId());
            notice1.setTypeName(noticeType.getTypeName());
        }
        return ResultUtil.success(iPage);
    }

    /**
     * 通过id获取公告详情
     * @param noticeID
     * @return
     */
    @GetMapping("/detail/{noticeID}")
    public Result getNoticeDetail(@PathVariable Long noticeID){
        Notice notice = noticeService.getById(noticeID);
        NoticeVote noticeVote = noticeVoteService.getOne(
                Wrappers.<NoticeVote>lambdaQuery()
                        .eq(NoticeVote::getNoticeId, notice.getId())
        );
        NoticeTypeRelation relation = noticeTypeRelationService.getOne(
                Wrappers.<NoticeTypeRelation>lambdaQuery()
                        .eq(NoticeTypeRelation::getNoticeId, notice.getId())
        );
        NoticeType noticeType = noticeTypeService.getById(relation.getNoticeTypeId());
        notice.setTypeID(noticeType.getId());
        notice.setTypeName(noticeType.getTypeName());
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("notice",notice);
        //如果与投票相关联，则返回投票详情
        if (noticeVote != null){
            Long voteID = noticeVote.getVoteId();
            Vote vote = voteService.getById(voteID);
            Date now = new Date();
            vote.setEndOrNot(vote.getEndTime().getTime() < now.getTime());
            VoteTotalInfo voteTotalInfo = new VoteTotalInfo();
            voteTotalInfo.setVote(vote);

            List<VoteCandidate> list = voteCandidateService.list(
                    Wrappers.lambdaQuery(VoteCandidate.class)
                            .eq(VoteCandidate::getVoteId, voteID)
            );

            List<VoteTotalInfo.CandidateInfo> candidateInfos = new ArrayList<>();
            list.forEach(voteCandidate -> {
                VoteTotalInfo.CandidateInfo candidateInfo = new VoteTotalInfo.CandidateInfo();
                candidateInfo.setCandidateTableID(voteCandidate.getId());
                candidateInfo.setCandidateName(voteCandidate.getCandidateName());
                if (StringUtils.isNotEmpty(voteCandidate.getPicUrls())) {
                    String[] split = voteCandidate.getPicUrls().split(CommonConstant.PIC_URLS_SPLIT_CHARS);
                    String picUrl = publicService.getStaticResPrefixUrl() + split[0];
                    candidateInfo.setPicUrl(picUrl);
                }
                candidateInfo.setVoteNum(voteRecordsService.count(
                        Wrappers.lambdaQuery(VoteRecords.class)
                                .eq(VoteRecords::getVoteCandidateTableId, voteCandidate.getId())
                ));
                candidateInfos.add(candidateInfo);
            });
            voteTotalInfo.setCandidateInfos(candidateInfos);
            resultMap.put("vote",voteTotalInfo);
            return ResultUtil.success(resultMap);
        }
        resultMap.put("vote",null);
        return ResultUtil.success(resultMap);
    }

    /**
     * 添加公告
     * @param notice
     * @return
     */
    @PostMapping
    public Result createNotice(@ModelAttribute Notice notice,
                               @RequestParam(value = "voteID",required = false)Long voteID){
        if (notice.getTitle() == null){
            return ResultUtil.error("标题不能为空");
        }
        if (notice.getContent() == null){
            return ResultUtil.error("内容不能为空");
        }
        if (notice.getTypeID() == null){
            return ResultUtil.error("类型ID不能为空");
        }
        int result = noticeService.createNotice(notice,voteID);
        if (result > 0){
            return ResultUtil.success("添加成功");
        }
        return ResultUtil.error("添加失败");
    }


    /**
     * 修改公告
     * @param notice
     * @return
     */
    @PutMapping
    public Result editNotice(@ModelAttribute Notice notice){
        if (notice.getId() == null) {
            return ResultUtil.error("公告id不能为空");
        }
        if (notice.getAno() != null){
            notice.setAno(null);
        }

        int result = noticeService.editNotice(notice);
        if (result > 0){
            return ResultUtil.success("修改成功");
        }
        return ResultUtil.error("修改失败");
    }


    /**
     * 删除一条公告
     * @param noticeID
     * @return
     */
    @DeleteMapping("{noticeID}")
    public Result deleteNotice(@PathVariable Long noticeID){
        int result = noticeService.deleteNotice(noticeID);
        if (result > 0){
            return ResultUtil.success("删除成功");
        }
        return ResultUtil.error("删除失败");
    }

    /**
     * 批量删除
     * @param idsStr
     * @return
     */
    @DeleteMapping("/deleteIds")
    public Result deleteAllById(@RequestParam String idsStr) {
        if (this.noticeService.removeByIds(Arrays
                .stream(idsStr.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toSet())
        )) return ResultUtil.success("批量删除成功");
        return ResultUtil.error("批量删除失败");
    }


    /**
     * 获取所有投票类型
     * @return
     */
    @GetMapping("/noticeType")
    public Result getNoticeType(){
        return ResultUtil.success(noticeTypeService.list());
    }

}
