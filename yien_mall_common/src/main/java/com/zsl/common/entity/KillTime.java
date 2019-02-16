package com.zsl.common.entity;

import com.zsl.common.entity.myenum.KillTimeState;
import com.zsl.common.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ${张世林}
 * @date 2019/01/27
 * 作用：秒杀时间对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KillTime implements Serializable,Comparable<KillTime> {

    private Integer id;

    /**
     * 秒杀开始时间
     */
    private Date killTime;

    /**
     * 秒杀预热时间
     */
    private Date preTime;

    /**
     * 秒杀结束时间
     */
    private Date endTime;

    private String KillDetail;

    private KillTimeState state;

    public int compareTo(KillTime o) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String thisFormat = simpleDateFormat.format(this.killTime);
        String oFormat = simpleDateFormat.format(o.killTime);
        int i = DateUtils.compare_date(thisFormat, oFormat);
        if (i == 1) {
            return 1;
        } else if (i == -1) {
            return -1;
        } else {
            return 0;
        }
    }
}
