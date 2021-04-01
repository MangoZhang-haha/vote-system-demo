package com.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.constant.CommonConstant;
import com.domain.Result;
import com.domain.Vote;
import com.domain.VoteCandidate;
import com.domain.VoteRecords;
import com.pojo.CandidateInfo;
import com.pojo.VoteTotalInfo;
import com.service.PublicService;
import com.service.VoteCandidateService;
import com.service.VoteRecordsService;
import com.service.VoteService;
import com.utils.ResultUtil;
import com.utils.TimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Mango
 * @Date: 2021/3/24 15:51:16
 */
@RestController
@RequestMapping("/candidate")
@Api(tags = "候选人控制器")
public class CandidateController {

    /**
     * 只能投票一次
     */
    private static final Long ONLY_VOTE_ONCE = 1L;

    /**
     * 每天可投一次
     */
    private static final Long VOTE_ONCE_PER_DAY = 2L;

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteCandidateService voteCandidateService;

    @Autowired
    private PublicService publicService;

    @Autowired
    private VoteRecordsService voteRecordsService;

    @ApiOperation("查看候选人的具体信息")
    @GetMapping("/getCandidateDetail/{candidateTableID}")
    public Result getCandidateInfo(@PathVariable("candidateTableID") @ApiParam Long candidateTableID) {
        VoteCandidate candidate = voteCandidateService.getOne(
                Wrappers.lambdaQuery(VoteCandidate.class)
                        .eq(VoteCandidate::getId, candidateTableID)
        );
        CandidateInfo candidateInfo = new CandidateInfo();
        candidateInfo.setCandidateTableID(candidateTableID);
        candidateInfo.setCandidateName(candidate.getCandidateName());
        candidateInfo.setIntroduce(candidate.getIntroduce());
        if (StringUtils.isNotEmpty(candidate.getPicUrls())) {
            String[] urls = candidate.getPicUrls().split(CommonConstant.PIC_URLS_SPLIT_CHARS);
            for (int i = 0; i < urls.length; i++) {
                urls[i] = publicService.getStaticResPrefixUrl() + urls[i];
            }
            candidateInfo.setPicUrls(Arrays.asList(urls));
        }
        int count = voteRecordsService.count(
                Wrappers.lambdaQuery(VoteRecords.class)
                        .eq(VoteRecords::getVoteCandidateTableId, candidateTableID)
        );
        candidateInfo.setVoteNum(count);
        return ResultUtil.success(candidateInfo);
    }

    @ApiOperation("获取投票项目信息和所有的候选人信息")
    @GetMapping("/{voteID}")
    public Result getAllCandidates(@PathVariable("voteID") @ApiParam("投票项目ID") Long voteID) {
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
        return ResultUtil.success(voteTotalInfo);
    }

    @ApiOperation("给候选人投票")
    @PostMapping("/voteForCandidate")
    public Result voteForCandidate(@RequestParam("userID") @ApiParam("当前操作的用户的ID") Long userID,
                                   @RequestParam("candidateTableID") @ApiParam("候选人的ID") Long candidateTableID) {
        VoteCandidate voteCandidate = voteCandidateService.getById(candidateTableID);
        Vote vote = voteService.getById(voteCandidate.getVoteId());
        if (vote.getVoteLimitId().equals(ONLY_VOTE_ONCE)) {
            int count = voteRecordsService.count(
                    Wrappers.lambdaQuery(VoteRecords.class)
                            .eq(VoteRecords::getVoteId, voteCandidate.getVoteId())
                            .eq(VoteRecords::getOwnerId, userID)
            );
            if (count > 0) {
                return ResultUtil.error("您已经投过票了,请勿重复投票");
            }
        } else if (vote.getVoteLimitId().equals(VOTE_ONCE_PER_DAY)){
            int count = voteRecordsService.count(
                    Wrappers.lambdaQuery(VoteRecords.class)
                            .eq(VoteRecords::getVoteId, voteCandidate.getVoteId())
                            .eq(VoteRecords::getOwnerId, userID)
                            .like(VoteRecords::getGmtCreate, TimeUtil.formatTime("yyyy-MM-dd"))
            );
            if (count > 0) {
                return ResultUtil.error("您今天已经投过票了,请勿重复投票");
            }
        } else {
            return ResultUtil.error("未知投票类型");
        }
        voteRecordsService.voteForCandidate(userID, candidateTableID);
        return ResultUtil.success();
    }
}
