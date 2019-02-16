package com.zsl.management.mapper;

import com.zsl.common.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ${张世林}
 * @date 2019/01/12
 * 作用：用户收货地址的mapper接口
 */
@Repository
public interface AddressMapper {

    /**
     * 添加一个用户收货地址
     * @param address : 收货信息
     * @return ： 存入的id的值
     */
    Integer insertOne(Address address);

    /**
     * 根据用户的id查询对应的收货地址
     * @param userId
     * @return
     */
    List<Address> selectByUserId(@Param("address_user_id") String userId);

    /**
     * 有选择性的根据id来进行更新地址
     * @param address
     * @return
     */
    Integer updateByPrimaryKeySelective(Address address);

    /**
     * 根据id，批量删除地址
     * @param addressIds
     * @return
     */
    Integer deleteBatch(@Param("addressIds") String[] addressIds);

    /**
     * 根据地址的唯一索引查询到对应的地址的信息
     * @param addressId
     * @return
     */
    Address selectAddressByAddressId(String addressId);
}
