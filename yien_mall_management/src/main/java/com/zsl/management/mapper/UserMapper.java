package com.zsl.management.mapper;

import com.zsl.common.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface UserMapper {
	/**
	 * 根据电话号码查询对应的用户的数据
	 * @param mobileNo
	 * @return
	 */
	User selectByMobileNo(BigInteger mobileNo);

	/**
	 * 插入一条数据
	 * @param user
	 * @return
	 */
	Integer insertOne(User user);

	/**
	 * 根据用户的id（唯一索引进行查询）
	 * @param userId
	 * @return
	 */
	User selectOneByUserId(String userId);

	/**
	 * 根据传入的这个user对象进行修改对象的数据,where的条件是id
	 * @param user
	 * @return
	 */
	Integer updateOne(User user);

	/**
	 * 判断数据库中是否存在对应的userid，避免唯一索引重复
	 * @param userId
	 * @return
	 */
	Integer selectUserIdCount(String userId);

	/**
	 * 根据userID进行批量删除的操作，单条删除，也能够封装成一个数组，进行删除
	 * @param userIds
	 * @return
	 */
	Integer deleteBatchByUserIds(@Param("userIds")String[] userIds);

	Integer updateByPrimaryKeySelective(User user);

	@Select("select * from yien_user where id = #{id}")
	User selectById(Integer id);

    List<User> selectAll();
}