package com.zsl.management.mapper;

import com.zsl.common.entity.Bank;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ${张世林}
 * @date 2019/01/12
 * 作用：银行卡的增删改查mapper
 */
@Repository
public interface BankMapper {
    Integer insertOne(Bank bank);

    Bank seletcBankByBankId(String bankId);

    Integer updateByPrimaryKeySelective(Bank bank);

    Integer deleteBankBatch(@Param("bankIds") String[] strings);

    List<Bank> seletcBankByUserId(String userId);
}
