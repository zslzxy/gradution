package com.zsl.common.interfaces.management;

import com.zsl.common.entity.ShopCar;

import java.util.List;

public interface ShopCarService {

    List<ShopCar> selectShopCarByUserId(String userId);

}
