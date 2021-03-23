package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Community;
import org.apache.ibatis.annotations.Param;

public interface CommunityMapper extends BaseMapper<Community> {

    Integer countOwnerNumbers(@Param("communityID") Long communityID);
}