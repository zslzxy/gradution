package com.zsl.management.mapper;

import com.zsl.common.entity.ShopCar;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopCarMapper {

    /**
     * 根据userID查询对应的购物车的数据
     * @param userId
     * @return
     */
    List<ShopCar> selectShopCarByUserId(String userId);
}
