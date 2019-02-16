package com.zsl.management.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zsl.common.entity.KillTime;
import com.zsl.common.entity.myenum.KillTimeState;
import com.zsl.common.interfaces.kill.KillTimeService;
import com.zsl.management.mapper.KillTimeMapper;
import com.zsl.management.utils.SpringUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ${张世林}
 * @date 2019/02/02
 * 作用：
 */
@Service(group = "management",version = "1.0.0",timeout = 8000)
@Component("killTimeService")
public class KillTimeServiceImpl implements KillTimeService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private KillTimeMapper killTimeMapper;

    @Override
    public Integer addKillTime(KillTime killTime) {
        Integer integer = 0;
        try {
            integer = killTimeMapper.insertOne(killTime);
            //amqpTemplate.convertAndSend("kill.direct", "kill.killTime", "yes");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return integer;
    }

    /**
     * 自动生成后面七天的时间
     * @return
     */
//    @Scheduled(cron = "0 0 1 ? * MON")
//    @Override
//    public Integer addKillTimeAuto() throws ParseException {
//        int i = 0;
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar c = Calendar.getInstance();
//        for (i = 1; i <= 7; i++) {
//            KillTime killTime1 = new KillTime();
//            KillTime killTime2 = new KillTime();
//            KillTime killTime3 = new KillTime();
//            KillTime killTime4 = new KillTime();
//            c.setTime(new Date());
//            c.add(Calendar.DATE, +i);
//            Date date = c.getTime();
//            String format1 = format.format(date);
//            String s = format1.split(" ")[0];
//            String s1 = s + " 08:00:00";
//            String s2 = s + " 12:00:00";
//            String s3 = s + " 18:00:00";
//            String s4 = s + " 22:00:00";
//            killTime1.setKillTime(format.parse(s1));
//            killTime2.setKillTime(format.parse(s2));
//            killTime3.setKillTime(format.parse(s3));
//            killTime4.setKillTime(format.parse(s4));
//            killTime1.setState(KillTimeState.WK);
//            killTime2.setState(KillTimeState.WK);
//            killTime3.setState(KillTimeState.WK);
//            killTime4.setState(KillTimeState.WK);
//            try {
//                killTimeMapper.insertOne(killTime1);
//                killTimeMapper.insertOne(killTime2);
//                killTimeMapper.insertOne(killTime3);
//                killTimeMapper.insertOne(killTime4);
//            } catch (Exception ex) {
//                System.out.println("日期重复"+ex.getMessage());
//            }
//        }
//        return i;
//    }


    @Override
    public Integer addKillTimeFromHtml(KillTime killTime) {
       // amqpTemplate.convertAndSend("kill.direct", "kill.killTime", "yes");
        //amqpTemplate.convertAndSend("kill.direct", "kill.task", "yes");
        return killTimeMapper.insertOne(killTime);
    }

    @Override
    public Integer deleteKillTime(Integer id) {
        //amqpTemplate.convertAndSend("kill.direct", "kill.killTime", "yes");
        return killTimeMapper.deleteOne(id);
    }

    @Override
    public List<KillTime> selectKillTime() {
        System.out.println("秒杀时间");
        return killTimeMapper.selectKillTimeByState(KillTimeState.WK);
    }

    @Override
    public KillTime selectKillTimeByKillTime(Date killTime) {
        return killTimeMapper.selectKillTimeByKillTime(killTime);
    }

    @Override
    public List<KillTime> selectKillTimeByState(KillTimeState wk) {
        return killTimeMapper.selectKillTimeByState(wk);
    }

    @Override
    public Integer updateKillTime(KillTime killTime) {
        try {
            killTimeMapper.updateOne(killTime);
            try {
                TimeUnit.SECONDS.sleep(2);
                //amqpTemplate.convertAndSend("kill.direct", "kill.killTime", "yes");
            } catch (InterruptedException ex) {

            }
        } catch (RuntimeException ex) {
            return 0;
        }
        return 1;
    }

    /**
     * 查询对应的商品的秒杀记录的数量
     * @param wk
     * @return
     */
    @Override public Integer selectKillTimeByStateCount(KillTimeState wk) {
        Integer integer = killTimeMapper.selectKillTimeByStateCount(wk);
        return integer;
    }

    @Override public KillTime selectKillTimeById(Integer id) {
        return killTimeMapper.selectKillTimeById(id);
    }
}
