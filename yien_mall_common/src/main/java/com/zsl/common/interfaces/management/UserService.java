package com.zsl.common.interfaces.management;

import com.github.pagehelper.PageInfo;
import com.zsl.common.entity.Address;
import com.zsl.common.entity.Bank;
import com.zsl.common.entity.User;
import com.zsl.common.utils.Msg;
import org.elasticsearch.cluster.metadata.AliasAction;

import java.util.List;

public interface UserService {

    PageInfo<User> selectUserByPage(Integer page);

    /**
     * 根据传入的对象进行数据的修改操作
     * @param user ： 需要修改的对象的数据
     * @return ： 是否删除成功
     */
    Msg updateOne(User user);

    /**
     * 批量删除的操作，可以删除单条或者多条数据
     * @param userIds ： 用户的userID数组
     * @return ： 是否删除成功
     */
    Msg deleteBatch(String[] userIds);

    /**
     * 根据userId查询数据库红是否已经存在了对应的id
     * @param userId ：用户输入的userID
     * @return ： 是否存在啊对应的userID
     */
    Msg selectUserIdCount(String userId);

    /**
     * 查询对应的userID的数据
     * @param userId
     * @return
     */
    User selectOneByUserId(String userId);

    /**
     * 新增收货地址
     * @param address
     * @return
     */
    Msg addAddress(Address address);

    /**
     * 根据数组删除收货地址
     * @param addressIds
     * @return
     */
    Msg deleteAddressBatch(String[] addressIds);

    /**
     * 根据对象更新收货地址
     * @param address
     * @return
     */
    Msg updateAddress(Address address);

    /**
     * 根据userID查询对应的地址
     * @param userId
     * @return
     */
    List<Address> selectAddressByUserId(String userId);

    /**
     * 根据地址的唯一索引查询对应的地址的信息
     * @param addressId
     * @return
     */
    Address selectAddressByAddressId(String addressId);

    Bank selectBankByBAnkId(String bankId);

    Msg addBank(Bank bank);

    Msg updateBank(Bank bank);

    Msg deleteBankBatch(String[] strings);

    List<Bank> selectBankByUserId(String userId);
}
