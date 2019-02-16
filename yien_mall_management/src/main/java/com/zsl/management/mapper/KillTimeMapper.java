package com.zsl.management.mapper;

import com.zsl.common.entity.KillTime;
import com.zsl.common.entity.myenum.KillTimeState;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 * @Filename KillTimeMapper.java
 *
 * @Description 秒杀时间管理
 *
 * @Version 1.0
 *
 * @Author 10838
 *
 * @Email 10838@yiji.com
 *
 * @History
 *<li>Author: 10838</li>
 *<li>Date: 2019年02月02日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@Repository
public interface KillTimeMapper {

    @Insert("insert into yien_kill_time(id, kill_time, kill_detail, state, pre_time, end_time) values(null, #{killTime}, #{killDetail}, #{state}, #{preTime}, #{endTime})")
    Integer insertOne(KillTime killTime);

    @Delete("delete from yien_kill_time where id = #{id}")
    Integer deleteOne(Integer id);

    @Update("update yien_kill_time set kill_time=#{killTime} , kill_detail = #{killDetail} , state=#{state} , pre_time=#{preTime},end_time=#{endTime} where id = #{id}")
    Integer updateOne(KillTime killTime);

    @Select("select * from yien_kill_time")
    List<KillTime> selectAll();

    @Select("select * from yien_kill_time where state = #{state}")
    List<KillTime> selectKillTimeByState(KillTimeState state);

    @Select("select * from yien_kill_time where kill_time between #{killTime} and #{killTime} limit 1")
    KillTime selectKillTimeByKillTime(Date killTime);

    @Select("select * from yien_kill_time where id = #{id}")
	KillTime selectKillTimeById(Integer id);

    @Select("select count(*) from yien_kill_time where state= #{state}")
    Integer selectKillTimeByStateCount(KillTimeState wk);
}
