package com.controller;

import cn.hutool.core.util.CharUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.constant.CommonConstant;
import com.constant.VoteConstant;
import com.constant.VoteEvConstant;
import com.constant.VoteLimitConstant;
import com.domain.*;
import com.pojo.VoteInfo;
import com.pojo.VoteSituation;
import com.service.*;
import com.utils.ResultUtil;
import com.utils.TimeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Mango
 * @Date: 2021/3/21 11:54:49
 */
@RestController
@RequestMapping("/vote")
@Api(tags = "投票控制器")
public class VoteController {

    @Value("${file.tmp}")
    private String tmpPath;

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteCandidateService voteCandidateService;

    @Autowired
    private VoteRecordsService voteRecordsService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private VoteEvService voteEvService;

    @Autowired
    private VoteLimitService voteLimitService;

    @Autowired
    private VoteTypeService voteTypeService;

    @ApiOperation("创建投票")
    @PostMapping
    public Result create(@RequestBody Map<String,Object> map) {
        File tmpFiles = new File(tmpPath);
        String[] fileNames = tmpFiles.list();
        List<String> names = Arrays.asList(fileNames);

        List<Map<String,Object>> candidateList = (ArrayList<Map<String,Object>>)map.get("candidate");
        List<VoteCandidate> voteCandidateList = new ArrayList<>();
        for (Map<String,Object> candidateMap : candidateList){
            List<String> urlList = (ArrayList<String>)candidateMap.get("url");
            List<String> checkedTmpNames = new ArrayList<>();
            urlList.forEach(s -> {
                checkedTmpNames.add(s.substring(s.lastIndexOf(File.separator) + 1));
            });
            if (urlList.size() != 0) {
                if (!names.containsAll(checkedTmpNames)) {
                    return ResultUtil.error("请重新上传文件");
                }
            }

            VoteCandidate voteCandidate = VoteCandidate.builder()
                    .candidateName(candidateMap.get("canName").toString())
                    .introduce(candidateMap.get("canInfo").toString())
                    .picUrls(StringUtils.join(urlList, CommonConstant.PIC_URLS_SPLIT_CHARS))
                    .build();
            voteCandidateList.add(voteCandidate);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Map<String, JSONArray> jsonArrayMap = new HashMap<>();
            jsonArrayMap.put("range", JSONArray.parseArray(JSONObject.toJSONString(map.get("range"))));
            String noticedIDs = voteService.getNoticedIDs(JSONObject.toJSONString(jsonArrayMap));
            if (!StringUtils.isNotEmpty(noticedIDs)){
                return ResultUtil.error("投票范围不能为空");
            }
            Vote vote = Vote.builder()
                    .creatorId(Long.parseLong(map.get("id").toString()))
                    .title(map.get("title").toString())
                    .introduce(map.get("detail").toString())
                    .voteTypeId(Long.parseLong(map.get("typeId").toString()))
                    .voteLimitId(Long.parseLong(map.get("limit").toString()))
                    .startTime(simpleDateFormat.parse(map.get("startTime").toString()))
                    .endTime(simpleDateFormat.parse(map.get("endTime").toString()))
                    .whetherAnonym(Boolean.parseBoolean(map.get("anonym").toString()))
                    .whetherReplaceByRelatives(Boolean.parseBoolean(map.get("relatives").toString()))
                    .whetherDraft(Boolean.parseBoolean(map.get("draft").toString()))
                    .ownerNoticedIds(noticedIDs)
                    .build();

            Integer result = voteService.createVote(vote,voteCandidateList);
            if (result > 0){
                return ResultUtil.success(vote);
            }

        }catch (Exception e){
            e.printStackTrace();
            ResultUtil.error("参数有误");
        }
        return ResultUtil.error("创建失败");
    }

    @ApiOperation("获取投票项目列表")
    @GetMapping("/getVoteList/{userID}")
    public Result getVoteList(@PathVariable("userID") Long userID) {
        Owner currentUser = ownerService.getById(userID);
        Boolean isOwner = true;
        List<Long> userIDs = new ArrayList<>();
        if (currentUser.getOid() != 0) {
            isOwner = false;
        }
        List<Owner> relatives = ownerService.list(
                Wrappers.lambdaQuery(Owner.class)
                        .eq(Owner::getOid, isOwner ? userID : currentUser.getOid())
        );
        relatives.forEach(relative -> userIDs.add(relative.getId()));
        userIDs.add(isOwner ? userID : currentUser.getOid());

        List<VoteEv> voteEvs = voteEvService.list(
                Wrappers.lambdaQuery(VoteEv.class)
                        .select(VoteEv::getVoteId)
                        .eq(VoteEv::getApplyStatus, VoteEvConstant.PASS_STATUS)
        );
        List<Long> votePassIDs = new ArrayList<>();
        voteEvs.forEach(voteEv -> {
            votePassIDs.add(voteEv.getVoteId());
        });
        if (votePassIDs.size() == 0) {
            return ResultUtil.success(null);
        }

        List<Vote> list = voteService.list(
                Wrappers.lambdaQuery(Vote.class)
                        .select(
                                Vote::getId, Vote::getTitle, Vote::getWhetherDraft,
                                Vote::getVisitNum, Vote::getStartTime, Vote::getEndTime,
                                Vote::getVoteLimitId
                        )
                        .in(Vote::getId, votePassIDs)
                        .eq(Vote::getVoteStatus, VoteConstant.VOTE_NORMAL_STATUS)
                        .like(
                                Vote::getOwnerNoticedIds,
                                new StringBuffer(userID.toString())
                                        .insert(0, CharUtil.COMMA)
                                        .append(CharUtil.COMMA)
                                        .toString()
                        )
                        .orderByDesc(Vote::getGmtCreate)
        );
        if (list.size() == 0) {
            return ResultUtil.success(null);
        }
        Date now = new Date();
        list.forEach(vote -> {
            Integer count = voteRecordsService.count(
                    Wrappers.lambdaQuery(VoteRecords.class)
                            .eq(VoteRecords::getVoteId, vote.getId())
            );
            vote.setVoteNumbers(count);

            vote.setEndOrNot(vote.getEndTime().getTime() < now.getTime());

            VoteRecords voteRecords = voteRecordsService.getOne(
                    Wrappers.lambdaQuery(VoteRecords.class)
                            .eq(VoteRecords::getVoteId, vote.getId())
                            .in(VoteRecords::getOwnerId, userIDs)
                            .like(vote.getVoteLimitId().equals(VoteLimitConstant.VOTE_ONCE_PER_DAY), VoteRecords::getGmtCreate, TimeUtil.formatTime("yyyy-MM-dd"))
            );
            if (ObjectUtils.isNotEmpty(voteRecords)) {
                vote.setHaveVoted(true);
            } else {
                vote.setToBeVoted(true);
            }
        });
        return ResultUtil.success(list);
    }

    @ApiOperation("获取单个投票项目的详情")
    @GetMapping("/{voteID}")
    public Result getVoteInfo(@PathVariable("voteID") @ApiParam("投票项目ID") Long voteID) {
        Vote vote = voteService.getById(voteID);
        VoteInfo voteInfo = VoteInfo.builder()
                .voteID(voteID)
                .title(vote.getTitle())
                .introduce(vote.getIntroduce())
                .startTime(vote.getStartTime())
                .endTime(vote.getEndTime())
                .visitNum(vote.getVisitNum())
                .build();
        Integer count = voteRecordsService.count(
                Wrappers.lambdaQuery(VoteRecords.class)
                        .eq(VoteRecords::getVoteId, voteID)
        );
        voteInfo.setTotalVoteNum(count);

        List<VoteCandidate> candidates = voteCandidateService.list(
                Wrappers.lambdaQuery(VoteCandidate.class)
                        .eq(VoteCandidate::getVoteId, voteID)
        );
        voteInfo.setCandidateNum(candidates.size());

        Map<VoteCandidate, Integer> candidateMap = new HashMap<>(candidates.size());
        candidates.forEach(voteCandidate -> {
            int recordNum = voteRecordsService.count(
                    Wrappers.lambdaQuery(VoteRecords.class)
                            .eq(VoteRecords::getVoteId, voteID)
                            .eq(VoteRecords::getVoteCandidateTableId, voteCandidate.getId())
            );
            candidateMap.put(voteCandidate, recordNum);
        });
        List<VoteInfo.Top> tops = new ArrayList<>();

        //默认显示前三个投票数量最高的
        List<Map.Entry<VoteCandidate, Integer>> list = new ArrayList<>(candidateMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<VoteCandidate, Integer>>() {
            @Override
            public int compare(Map.Entry<VoteCandidate, Integer> o1, Map.Entry<VoteCandidate, Integer> o2) {
                Integer num1 = o1.getValue();
                Integer num2 = o2.getValue();
                if (num1 - num2 > 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        int defaultTopNum = 3;
        defaultTopNum = defaultTopNum >= list.size() ? list.size() : defaultTopNum;
        for (int i = 0; i < defaultTopNum; i++) {
            VoteInfo.Top top = new VoteInfo.Top();
            Map.Entry<VoteCandidate, Integer> entry = list.get(i);
            top.setCandidateTableID(entry.getKey().getId());
            top.setCandidateName(entry.getKey().getCandidateName());
            top.setVoteNum(entry.getValue());
            tops.add(top);
        }
        voteInfo.setTops(tops);
        voteInfo.setVoteLimitID(vote.getVoteLimitId());
        voteInfo.setVoteLimitName(voteLimitService.getById(vote.getVoteLimitId()).getVoteLimitName());
        voteInfo.setVoteTypeID(vote.getVoteTypeId());
        voteInfo.setVoteTypeName(voteTypeService.getById(vote.getVoteTypeId()).getVoteTypeName());
        return ResultUtil.success(voteInfo);
    }

    @ApiOperation("删除投票项目")
    @DeleteMapping("/{voteID}")
    public Result delete(@PathVariable("voteID") @ApiParam("投票项目ID") Long voteID) {
        voteService.deleteVote(voteID);
        return ResultUtil.success();
    }

    @ApiOperation("查看投票情况")
    @GetMapping("/getVoteSituation/{voteID}")
    public Result getVoteSituation(@PathVariable("voteID") @ApiParam("投票项目ID") Long voteID,
                                   @RequestParam(value = "whetherHaveVoted", defaultValue = "true") @ApiParam("是否已投票") Boolean whetherHaveVoted,
                                   @RequestParam(value = "candidateName", defaultValue = "") @ApiParam("候选人的名称") String candidateName) {
        List<VoteCandidate> searchResults = new ArrayList<>();
        List<Long> candidateIDsOfResult = new ArrayList<>();
        if (StringUtils.isNotEmpty(candidateName)) {
            searchResults = voteCandidateService.list(
                    Wrappers.lambdaQuery(VoteCandidate.class)
                            .eq(VoteCandidate::getVoteId, voteID)
                            .like(VoteCandidate::getCandidateName, candidateName)
            );
        }
        Boolean haveSearchResult = false;
        if (searchResults != null && searchResults.size() > 0) {
            haveSearchResult = true;
            searchResults.forEach(voteCandidate -> {
                candidateIDsOfResult.add(voteCandidate.getId());
            });
        }
        List<VoteRecords> voteRecords = voteRecordsService.list(
                Wrappers.lambdaQuery(VoteRecords.class)
                        .eq(VoteRecords::getVoteId, voteID)
                        .in(haveSearchResult && whetherHaveVoted, VoteRecords::getVoteCandidateTableId, candidateIDsOfResult)
        );
        List<Long> ownerIDs = new ArrayList<>();
        voteRecords.forEach(voteRecord -> {
            ownerIDs.add(voteRecord.getOwnerId());
        });

        List<VoteSituation> voteSituations = new ArrayList<>();
        if (whetherHaveVoted) {
            List<VoteCandidate> voteCandidates = voteCandidateService.list(
                    Wrappers.lambdaQuery(VoteCandidate.class)
                            .eq(VoteCandidate::getVoteId, voteID)
            );
            Map<Long, String> voteCandidateMap = new HashMap<>(voteCandidates.size());
            voteCandidates.forEach(
                    voteCandidate -> voteCandidateMap.put(voteCandidate.getId(), voteCandidate.getCandidateName())
            );

            List<Owner> owners = ownerService.listByIds(ownerIDs);
            Map<Long, Owner> ownerMap = new HashMap<>(owners.size());
            owners.forEach(owner -> {
                ownerMap.put(owner.getId(), owner);
            });

            voteRecords.forEach(voteRecord -> {
                VoteSituation voteSituation = new VoteSituation();
                voteSituation.setCandidateName(voteCandidateMap.get(voteRecord.getVoteCandidateTableId()));
                voteSituation.setOwnerID(voteRecord.getOwnerId());
                voteSituation.setVoteTime(voteRecord.getGmtCreate());
                voteSituation.setOwnerName(ownerMap.get(voteRecord.getOwnerId()).getOwnerName());
                voteSituation.setAvatarUrl(ownerMap.get(voteRecord.getOwnerId()).getAvatarUrl());
                voteSituations.add(voteSituation);
            });
        } else {
            Vote vote = voteService.getById(voteID);
            String ownerNoticedIdsStr = vote.getOwnerNoticedIds();
            List<Long> ownerNoticedIds = Arrays.stream(ownerNoticedIdsStr.split(",")).map(Long::valueOf).collect(Collectors.toList());
            List<Long> notVotedIDs = ownerNoticedIds.stream().filter(item -> !ownerIDs.contains(item)).collect(Collectors.toList());
            List<Owner> owners = ownerService.listByIds(notVotedIDs);
            owners.forEach(owner -> {
            VoteSituation voteSituation = new VoteSituation();
            voteSituation.setOwnerID(owner.getId());
            voteSituation.setOwnerName(owner.getOwnerName());
            voteSituation.setAvatarUrl(owner.getAvatarUrl());
            voteSituations.add(voteSituation);
        });
    }
        return ResultUtil.success(voteSituations);
    }

    @ApiOperation("草稿提交申请")
    @PostMapping("/{voteID}")
    public Result toEv(@PathVariable("voteID") @ApiParam("投票项目id") Long voteID) {
        voteService.toEv(voteID);
        return ResultUtil.success("提交成功");
    }

    @ApiOperation("草稿更新")
    @PostMapping("/update/{voteID}")
    public Result updateVote(@PathVariable("voteID") @ApiParam("投票项目id") Long voteID,
                             @RequestBody @ApiParam("和创建投票json一样") Map<String, Object> map) {
        List<Map<String, Object>> candidateList = JSONObject.parseObject(JSONObject.toJSONString(map.get("candidate")),ArrayList.class);
        List<VoteCandidate> voteCandidateList = new ArrayList<>();
        for (Map<String, Object> candidateMap : candidateList){
            List<String> urlList = JSONObject.parseObject(JSONObject.toJSONString(candidateMap.get("url")),ArrayList.class);
            VoteCandidate voteCandidate = VoteCandidate.builder()
                    .id(candidateMap.get("canID") != null ? Long.parseLong(candidateMap.get("canID").toString()) : null)
                    .candidateName(candidateMap.get("canName").toString())
                    .introduce(candidateMap.get("canInfo").toString())
                    .picUrls(StringUtils.join(urlList, CommonConstant.PIC_URLS_SPLIT_CHARS))
                    .build();
            voteCandidateList.add(voteCandidate);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            Map<String, JSONArray> jsonArrayMap = new HashMap<>();
            jsonArrayMap.put("range", JSONArray.parseArray(JSONObject.toJSONString(map.get("range"))));
            String noticedIDs = voteService.getNoticedIDs(JSONObject.toJSONString(jsonArrayMap));
            if (!StringUtils.isNotEmpty(noticedIDs)){
                return ResultUtil.error("投票范围不能为空");
            }
            Vote vote = Vote.builder()
                    .id(voteID)
                    .creatorId(Long.parseLong(map.get("id").toString()))
                    .title(map.get("title").toString())
                    .introduce(map.get("detail").toString())
                    .voteTypeId(Long.parseLong(map.get("typeId").toString()))
                    .voteLimitId(Long.parseLong(map.get("limit").toString()))
                    .startTime(simpleDateFormat.parse(map.get("startTime").toString()))
                    .endTime(simpleDateFormat.parse(map.get("endTime").toString()))
                    .whetherAnonym(Boolean.parseBoolean(map.get("anonym").toString()))
                    .whetherReplaceByRelatives(Boolean.parseBoolean(map.get("relatives").toString()))
                    .whetherDraft(Boolean.parseBoolean(map.get("draft").toString()))
                    .ownerNoticedIds(noticedIDs)
                    .build();

            Integer result = voteService.updateVote(vote,voteCandidateList);
            if (result > 0){
                return ResultUtil.success(vote);
            } else if (result == -1) {
                return ResultUtil.error("请重新上传图片");
            }

        }catch (Exception e){
            e.printStackTrace();
            ResultUtil.error("参数有误");
        }
        return ResultUtil.error("修改失败");
    }

    @ApiOperation("获取是否可以亲人代替投票")
    @GetMapping("/checkRelatives/{voteID}")
    public Result whetherReplaceByRelatives(@PathVariable("voteID") @ApiParam("投票项目id") Long voteID) {
        Vote vote = voteService.getById(voteID);
        return ResultUtil.success(vote.getWhetherReplaceByRelatives());
    }

    @ApiOperation("获取所有已经结束的项目")
    @GetMapping("/getAllFinished")
    public Result getAllFinished() {
        List<VoteEv> voteEvs = voteEvService.list(
                Wrappers.lambdaQuery(VoteEv.class)
                        .select(VoteEv::getVoteId)
                        .eq(VoteEv::getApplyStatus, VoteEvConstant.PASS_STATUS)
        );
        List<Long> votePassIDs = new ArrayList<>();
        voteEvs.forEach(voteEv -> {
            votePassIDs.add(voteEv.getVoteId());
        });
        if (votePassIDs.size() == 0) {
            return ResultUtil.success(null);
        }

        Date now = new Date();
        List<Vote> list = voteService.list(
                Wrappers.lambdaQuery(Vote.class)
                        .in(Vote::getId, votePassIDs)
                        .eq(Vote::getVoteStatus, VoteConstant.VOTE_NORMAL_STATUS)
                        .lt(Vote::getEndTime, now)
                        .orderByDesc(Vote::getGmtCreate)
        );
        return ResultUtil.success(list);
    }
}
