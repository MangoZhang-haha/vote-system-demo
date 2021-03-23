package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Building;
import org.apache.ibatis.annotations.Param;

public interface BuildingMapper extends BaseMapper<Building> {

    Integer countOwnerNumbers(@Param("buildingID") Long buildingID);
}