package com.zsl.management.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zsl.common.entity.ShopCar;
import com.zsl.common.interfaces.management.ShopCarService;
import com.zsl.management.mapper.ShopCarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ${张世林}
 * @date 2019/01/13
 * 作用：
 */
@Component
@Service(group = "management",version = "1.0.0",timeout = 8000)
public class ShopCarServiceImpl implements ShopCarService {

    @Autowired
    private ShopCarMapper shopCarMapper;

    /**
     * 根据userID查询对应的用户的购物车数据
     * @param userId
     * @return
     */
    @Override
    public List<ShopCar> selectShopCarByUserId(String userId) {
        return shopCarMapper.selectShopCarByUserId(userId);
    }
}
