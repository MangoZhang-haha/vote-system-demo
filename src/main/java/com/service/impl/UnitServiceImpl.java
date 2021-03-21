package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.Unit;
import com.mapper.UnitMapper;
import com.service.UnitService;
@Service
public class UnitServiceImpl extends ServiceImpl<UnitMapper, Unit> implements UnitService{

}
