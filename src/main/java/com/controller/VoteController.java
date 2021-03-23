package com.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.domain.Result;
import com.domain.Vote;
import com.domain.VoteCandidate;
import com.service.VoteService;
import com.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Mango
 * @Date: 2021/3/21 11:54:49
 */
@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping()
    public Result create(@RequestBody Map<String,Object> map) {
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

            List<Map<String,Object>> candidateList = (ArrayList<Map<String,Object>>)map.get("candidate");
            List<VoteCandidate> voteCandidateList = new ArrayList<>();
            for (Map<String,Object> candidateMap : candidateList){
                List<String> urlList = (ArrayList<String>)candidateMap.get("url");

                VoteCandidate voteCandidate = VoteCandidate.builder()
                        .candidateName(candidateMap.get("canName").toString())
                        .introduce(candidateMap.get("canInfo").toString())
                        .picUrls(StringUtils.join(urlList,"$$"))
                        .build();
                voteCandidateList.add(voteCandidate);
            }

            Integer result = voteService.createVote(vote,voteCandidateList);
            if (result > 0){
                return ResultUtil.success("创建成功");
            }

        }catch (Exception e){
            e.printStackTrace();
            ResultUtil.error("参数有误");
        }
        return ResultUtil.error("创建失败");
    }
}
