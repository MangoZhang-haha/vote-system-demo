package com.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.domain.Notice;
import com.domain.NoticeType;
import com.domain.NoticeTypeRelation;
import com.domain.Result;
import com.service.NoticeService;
import com.service.NoticeTypeRelationService;
import com.service.NoticeTypeService;
import com.utils.ResultUtil;
import com.utils.WrapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
     * 添加公告
     * @param notice
     * @return
     */
    @PostMapping
    public Result createNotice(@ModelAttribute Notice notice){
        if (notice.getTitle() == null){
            return ResultUtil.error("标题不能为空");
        }
        if (notice.getContent() == null){
            return ResultUtil.error("内容不能为空");
        }
        if (notice.getTypeID() == null){
            return ResultUtil.error("类型ID不能为空");
        }
        int result = noticeService.createNotice(notice);
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
