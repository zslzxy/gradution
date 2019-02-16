package com.zsl.management.mapper;

import com.zsl.common.entity.UserTrans;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ${张世林}
 * @date 2019/01/19
 * 作用：商品订单mapper
 */
@Repository
public interface OrderMapper {

    /**
     * 创建订单数据
     * @param userTrans
     * @return
     */
    Integer insertOne(UserTrans userTrans);

    /**
     * 根据用户的id获取到用户的所有订单信息
     * @param userId
     * @return
     */
    List<UserTrans> selectOrderByUserId(@Param("trans_user_id") String userId);
}
