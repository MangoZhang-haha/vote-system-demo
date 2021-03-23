package com.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.domain.*;
import com.domain.format.*;
import com.mapper.*;
import com.service.CommunityBuildingService;
import com.service.RangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mango
 * @Date: 2021/3/23 15:45:52
 */
@Service
public class RangeServiceImpl implements RangeService {

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private CommunityBuildingMapper communityBuildingMapper;

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    private UnitHouseMapper unitHouseMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private OwnerHouseMapper ownerHouseMapper;

    @Override
    public Range getRange() {
        Range range = new Range();

        // 设置小区
        List<Community> communities = communityMapper.selectList(null);
        List<CommunityInfo> communityInfos = new ArrayList<>();
        communities.forEach(community -> {
            CommunityInfo communityInfo = new CommunityInfo();
            communityInfo.setId(community.getId());
            communityInfo.setName(community.getCommunityName());
            communityInfo.setPeopleNum(communityMapper.countOwnerNumbers(community.getId()));
            communityInfos.add(communityInfo);
        });
        range.setRange(communityInfos);

        // 获取所有小区楼宇关系
        List<CommunityBuilding> communityBuildings = communityBuildingMapper.selectList(null);
        Map<Long, List<Long>> communityBuildingMap = new HashMap<>(communityBuildings.size());
        communityBuildings.forEach(communityBuilding -> {
            if (communityBuildingMap.get(communityBuilding.getCommunityId()) != null) {
                communityBuildingMap.get(communityBuilding.getCommunityId()).add(communityBuilding.getBuildingId());
            } else {
                List<Long> list = new ArrayList<>();
                list.add(communityBuilding.getBuildingId());
                communityBuildingMap.put(communityBuilding.getCommunityId(), list);
            }
        });
        // 获取所有楼宇
        List<Building> buildings = buildingMapper.selectList(null);
        Map<Long, Building> buildingMap = new HashMap<>(buildings.size());
        buildings.forEach(building -> {
            buildingMap.put(building.getId(), building);
        });
        // 获取所有楼宇单元关系
        List<Unit> units = unitMapper.selectList(null);
        Map<Long, List<Unit>> unitMap = new HashMap<>(units.size());
        units.forEach(unit -> {
            if (unitMap.get(unit.getBuildingId()) != null) {
                unitMap.get(unit.getBuildingId()).add(unit);
            } else {
                List<Unit> unitList = new ArrayList<>();
                unitList.add(unit);
                unitMap.put(unit.getBuildingId(), unitList);
            }
        });
        // 获取所有单元房屋关系
        List<UnitHouse> unitHouses = unitHouseMapper.selectList(null);
        Map<Long, List<Long>> unitHouseMap = new HashMap<>(unitHouses.size());
        unitHouses.forEach(unitHouse -> {
            if (unitHouseMap.get(unitHouse.getUnitTableId()) != null) {
                unitHouseMap.get(unitHouse.getUnitTableId()).add(unitHouse.getHouseId());
            } else {
                List<Long> list = new ArrayList<>();
                list.add(unitHouse.getHouseId());
                unitHouseMap.put(unitHouse.getUnitTableId(), list);
            }
        });
        //获取所有房屋
        List<House> houses = houseMapper.selectList(null);
        Map<Long, House> houseMap = new HashMap<>(houses.size());
        houses.forEach(house -> {
            houseMap.put(house.getId(), house);
        });
        //获取房屋业主关系
        List<OwnerHouse> ownerHouses = ownerHouseMapper.selectList(null);
        Map<Long, Long> ownerHouseMap = new HashMap<>(ownerHouses.size());
        ownerHouses.forEach(ownerHouse -> {
            ownerHouseMap.put(ownerHouse.getHouseId(), ownerHouse.getOwnerId());
        });

        // 设置小区下面的楼宇
        communityInfos.forEach(communityInfo -> {
            List<Long> buildingIDs = communityBuildingMap.get(communityInfo.getId());
            List<BuildingInfo> buildingInfos = new ArrayList<>();
            buildingIDs.forEach(buildingID -> {
                BuildingInfo buildingInfo = new BuildingInfo();
                Building building = buildingMap.get(buildingID);
                buildingInfo.setId(building.getId());
                buildingInfo.setName(building.getBuildingName());
                buildingInfo.setPeopleNum(buildingMapper.countOwnerNumbers(buildingID));
                if (unitMap.get(building.getId()) != null) {
                    List<Unit> unitList = unitMap.get(building.getId());
                    List<UnitInfo> unitInfos = new ArrayList<>();
                    unitList.forEach(unit -> {
                        UnitInfo unitInfo = new UnitInfo();
                        unitInfo.setName(unit.getUnitName());
                        unitInfo.setUnitTableID(unit.getId());
                        unitInfo.setPeopleNum(unitMapper.countOwnerNumbers(unit.getId()));
                        if (unitHouseMap.get(unit.getId()) != null) {
                            List<Long> unitHouseList = unitHouseMap.get(unit.getId());
                            List<HouseInfo> houseInfos = new ArrayList<>();
                            unitHouseList.forEach(houseID -> {
                                if (houseMap.get(houseID) != null && ownerHouseMap.get(houseID) != null) {
                                    HouseInfo houseInfo = new HouseInfo();
                                    houseInfo.setId(houseID);
                                    houseInfo.setName(houseMap.get(houseID).getHouseNumber());
                                    houseInfo.setOwnerID(ownerHouseMap.get(houseID));
                                    houseInfos.add(houseInfo);
                                }
                            });
                            unitInfo.setChildren(houseInfos);
                        }
                        unitInfos.add(unitInfo);
                    });
                    buildingInfo.setChildren(unitInfos);
                }
                buildingInfos.add(buildingInfo);
            });
            communityInfo.setChildren(buildingInfos);
        });
        return range;
    }
}
