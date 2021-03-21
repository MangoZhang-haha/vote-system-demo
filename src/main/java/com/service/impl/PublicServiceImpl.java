package com.service.impl;

import com.service.PublicService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Mango
 * @Date: 2021/3/20 21:39:13
 */
@Service
public class PublicServiceImpl implements PublicService {

    @Value("${static-res.port}")
    private String port;

    @Value("${server-ip}")
    private String serverIp;

    @Override
    public String getStaticResPrefixUrl() {
        return "http://" + serverIp + ":" + port;
    }
}
