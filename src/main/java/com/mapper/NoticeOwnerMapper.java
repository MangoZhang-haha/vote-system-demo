package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.NoticeOwner;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface NoticeOwnerMapper extends BaseMapper<NoticeOwner> {
    
    Integer addList(@Param("list") List<NoticeOwner> list, @Param("now") Date now);
}