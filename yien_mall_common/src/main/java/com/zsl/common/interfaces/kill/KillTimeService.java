package com.zsl.common.interfaces.kill;

import com.zsl.common.entity.KillTime;
import com.zsl.common.entity.myenum.KillTimeState;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 *
 * @Filename KillTimeService.java
 *
 * @Description 秒杀时间管理的service
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
public interface KillTimeService {

    Integer addKillTimeFromHtml(KillTime killTime);

//    Integer addKillTimeAuto() throws ParseException;

    /**
     * 新增秒杀时间
     * @param killTime
     * @return
     */
    Integer addKillTime(KillTime killTime);

    /**
     * 删除秒杀时间
     * @param id
     * @return
     */
    Integer deleteKillTime(Integer id);

    /**
     * 获取所有秒杀时间
     * @return
     */
    List<KillTime> selectKillTime();

    /**
     * 根据秒杀时间多去对应的秒杀数据
     * @param killTime
     * @return
     */
    KillTime selectKillTimeByKillTime(Date killTime);


    /**
     * 根据状态查询对应的秒杀时间
     * @param wk
     * @return
     */
    List<KillTime> selectKillTimeByState(KillTimeState wk);

    Integer updateKillTime(KillTime killTime);

    /**
     * 根据id获取对应的秒杀的时间信息
     * @param id
     * @return
     */
	KillTime selectKillTimeById(Integer id);

    /**
     * 查询对应状态的秒杀记录的数量
     * @param wk
     * @return
     */
    Integer selectKillTimeByStateCount(KillTimeState wk);
}
