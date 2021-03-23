package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Unit;
import org.apache.ibatis.annotations.Param;

public interface UnitMapper extends BaseMapper<Unit> {

    Integer countOwnerNumbers(@Param("unitTableID") Long unitTableID);
}