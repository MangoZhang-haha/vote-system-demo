package com.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.XUserRole;
import com.mapper.XUserRoleMapper;
import com.service.XUserRoleService;
@Service
public class XUserRoleServiceImpl extends ServiceImpl<XUserRoleMapper, XUserRole> implements XUserRoleService{

}
