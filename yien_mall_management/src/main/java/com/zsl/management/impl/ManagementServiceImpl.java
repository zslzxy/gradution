package com.zsl.management.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zsl.common.interfaces.management.ManagmentService;
import org.springframework.stereotype.Component;

/**
 * @author ${张世林}
 * @date 2019/01/01
 * 作用：
 */
@Component
@Service(group = "yien_mall_management",timeout = 8000)
public class ManagementServiceImpl implements ManagmentService {

    @Override
    public String test() {
        return "TestManagementServiceImpl";
    }

}
