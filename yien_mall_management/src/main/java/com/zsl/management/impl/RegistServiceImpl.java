package com.zsl.management.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zsl.common.entity.User;
import com.zsl.common.interfaces.management.RegistService;
import com.zsl.common.utils.Msg;
import com.zsl.management.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ${张世林}
 * @date 2019/01/06
 * 作用：
 */
@Component
@Service(group = "management",version = "1.0.0",timeout = 8000)
public class RegistServiceImpl implements RegistService {

    @Autowired
    private UserMapper userMapper;

    @Transactional()
    @Override
    public Msg insertOne(User user) {
        try {
            userMapper.insertOne(user);
        } catch (DataAccessException ex) {
            User user1 = userMapper.selectOneByUserId(user.getUserId());
            return Msg.fail().addInfo("用户id已经存在，请重新输入").addMap("user",user1);
        }

        return Msg.success().addInfo("数据插入成功").addMap("user",user);
    }
}
