package com.zsl.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ${张世林}
 * @date 2019/02/05
 * 作用：
 */
public class DateUtils {

    /**
     * 当前日期基础之上，增加对应的日期
     * @param date : 日期
     * @param addDay ： 需要增加的日期
     * @return
     */
    public static Date getAddDay(Date date, Integer addDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //当前日期的基础之上，添加一天
        calendar.add(Calendar.DAY_OF_MONTH, + addDay);
        date = calendar.getTime();
        return date;
    }

    /**
     * 当前日期基础之上，增加对应的日期
     * @param date : 日期
     * @param deleteMinute ： 需要减少的分钟数
     * @return
     */
    public static Date getDeleteDay(Date date, Integer deleteMinute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //当前日期的基础之上，添加一天
        calendar.add(Calendar.MINUTE, - deleteMinute);
        date = calendar.getTime();
        return date;
    }

    /**
     * 比较两个日期
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            int i = dt1.compareTo(dt2);
            if (i == 1) {
                return 1;
            } else if (i == -1) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }


}
