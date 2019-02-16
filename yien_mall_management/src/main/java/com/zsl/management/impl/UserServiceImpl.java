package com.zsl.management.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.zsl.common.entity.Address;
import com.zsl.common.entity.Bank;
import com.zsl.common.entity.User;
import com.zsl.common.interfaces.management.UserService;
import com.zsl.common.utils.Msg;
import com.zsl.management.mapper.AddressMapper;
import com.zsl.management.mapper.BankMapper;
import com.zsl.management.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author ${张世林}
 * @date 2019/01/06
 * 作用：
 */
@Component(value = "userService")
@Service(group = "management",version = "1.0.0",timeout = 8000)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;

    @Autowired
    @Qualifier("addressMapper")
    private AddressMapper addressMapper;

    @Autowired
    @Qualifier("bankMapper")
    private BankMapper bankMapper;

    @Override
    public PageInfo<User> selectUserByPage(Integer page) {
        PageHelper.startPage(page, 5);
        List<User> list = userMapper.selectAll();
        PageInfo<User> pageinfo = new PageInfo<>(list,5);
        return pageinfo;
    }

    /**
     * 更新数据的操作。
     *    事务：创建新的事务，当前事务挂起
     *          只能够读取到其他事务已经提交了的数据
     *      更新事务执行之前，需要对这一条数据进行上悲观锁的操作
     *      执行事务，更新数据
     * @param user ： 需要修改的对象的数据
     * @return ： 如果返回的数据为1 ，则表示数据修改成功，否则表示的是数据修改失败
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,
                   isolation = Isolation.READ_COMMITTED)
    @Override
    public Msg updateOne(User user) {
        System.out.println(user);
        User user1 = null;
        Integer integer = 0;
        try {
            user1 = userMapper.selectOneByUserId(user.getUserId());
            integer = userMapper.updateByPrimaryKeySelective(user);
            if (integer == 1) {
                return Msg.success().addInfo("数据修改成功").addMap("user",user);
            }
        } catch (RuntimeException ex) {
            logger.info("数据修改失败"+ex);
            ex.printStackTrace();
        }
        return Msg.fail().addInfo("数据修改失败").addMap("user",user1);
    }

    @Transactional()
    @Override
    public Msg deleteBatch(String[] userIds) {
        Integer integer = 0;
        try {
            integer = userMapper.deleteBatchByUserIds(userIds);
        } catch (RuntimeException ex) {
            logger.info("删除数据失败"+ex);
        }
        if (integer == 1) {
            return Msg.success().addInfo("删除数据成功");
        } else if (integer > 1) {
            return Msg.success().addInfo("批量删除成功");
        }
        return Msg.fail().addInfo("删除数据失败");
    }

    @Override
    public Msg selectUserIdCount(String userId) {
        Integer integer = userMapper.selectUserIdCount(userId);
        if (integer > 0) {
            return Msg.fail().addInfo("数据库中已经存在对应的userID");
        }
        return Msg.success().addInfo("数据库中无重复");
    }

    /**
     * 根据userID查询对应的用户的数据
     * @param userId
     * @return
     */
    @Override
    public User selectOneByUserId(String userId) {
        return userMapper.selectOneByUserId(userId);
    }

    /**
     * 新增地址
     * @param address
     * @return
     */
    @Override
    public Msg addAddress(Address address) {
        address.setAddressId(String.valueOf(System.nanoTime()));
        address.setRawAddTime(new Date());
        System.out.println(address);
        Integer integer = null;
        try {
            integer = addressMapper.insertOne(address);
        } catch (DataAccessException ex) {
            return Msg.fail().addInfo("地址新增失败").addMap("address",address);
        }
        if (integer > 0) {
            return Msg.success().addInfo("地址新增成功").addMap("address",address);
        }
        return Msg.fail().addInfo("地址新增失败").addMap("address",address);
    }

    /**
     * 根据数组删除地址
     * @param addressIds
     * @return
     */
    @Override
    public Msg deleteAddressBatch(String[] addressIds) {
        Integer integer = addressMapper.deleteBatch(addressIds);
        if (integer > 0) {
            return Msg.success().addInfo("地址删除成功");
        }
        return Msg.fail().addInfo("地址删除失败");
    }

    /**
     * 根据地址更新地址
     * @param address
     * @return
     */
    @Override
    public Msg updateAddress(Address address) {
        address.setRawUpdateTime(new Date());
        Integer integer = null;
        System.out.println(address);
        try {
            List<Address> addresses = addressMapper.selectByUserId(address.getAddressId());
            integer = addressMapper.updateByPrimaryKeySelective(address);
        } catch (DataAccessException ex) {
            return Msg.success().addInfo("地址更新失败");
        }
        if (integer > 0) {
            return Msg.success().addInfo("地址更新成功");
        }
        return Msg.success().addInfo("地址更新失败");
    }

    /**
     * 根据userID查询对应的收货地址
     * @param userId
     * @return
     */
    @Override
    public List<Address> selectAddressByUserId(String userId) {
        return addressMapper.selectByUserId(userId);
    }

    /**
     * 根据收货地址的唯一索引，查询对应的地址
     * @param addressId
     * @return
     */
    @Override
    public Address selectAddressByAddressId(String addressId) {
        return addressMapper.selectAddressByAddressId(addressId);
    }

    /**
     * 根据银行卡的唯一索引，查询对应的银行卡的数据
     * @param bankId
     * @return
     */
    @Override
    public Bank selectBankByBAnkId(String bankId) {
        Bank bank = bankMapper.seletcBankByBankId(bankId);
        return bank;
    }

    /**
     * 根据用户的唯一索引，查询对应的银行卡的数据
     * @return
     */
    @Override
    public List<Bank> selectBankByUserId(String userId) {
        List<Bank> banks = bankMapper.seletcBankByUserId(userId);
        return banks;
    }

    /**
     * 新增银行卡
     * @param bank
     * @return
     */
    @Override
    public Msg addBank(Bank bank) {
        System.out.println(bank);
        bank.setBankId(String.valueOf(System.nanoTime()));
        bank.setRawAddTime(new Date());
        Integer id = null;
        try {
            id = bankMapper.insertOne(bank);
        } catch (DataAccessException ex) {
            System.out.println(ex);
            return Msg.fail().addInfo("新增银行卡失败").addMap("bank",bank);
        }
        if (id > 0) {
            return Msg.success().addInfo("新增银行卡成功").addMap("bank",bank);
        }
        return Msg.fail().addInfo("新增银行卡失败").addMap("bank",bank);
    }

    /**
     * 更新银行卡
     * @param bank
     * @return
     */
    @Transactional
    @Override
    public Msg updateBank(Bank bank) {
        Bank bank1 = null;
        Integer integer = null;
        try {
            bank1 = bankMapper.seletcBankByBankId(bank.getBankId());
            integer = bankMapper.updateByPrimaryKeySelective(bank);
        } catch (DataAccessException ex) {
            return Msg.fail().addInfo("更新银行卡失败").addMap("bank",bank1);
        }
        if (integer > 0) {
            return Msg.success().addInfo("更新银行卡成功").addMap("bank",bank);
        }
        return Msg.fail().addInfo("更新银行卡失败").addMap("bank",bank);
    }

    /**
     * 根据bankid数组删除数据
     * @param strings
     * @return
     */
    @Override
    public Msg deleteBankBatch(String[] strings) {
        Integer integer = bankMapper.deleteBankBatch(strings);
        if (integer > 0) {
            return Msg.success().addInfo("删除银行卡成功");
        }
        return Msg.fail().addInfo("删除银行卡失败");
    }


}
