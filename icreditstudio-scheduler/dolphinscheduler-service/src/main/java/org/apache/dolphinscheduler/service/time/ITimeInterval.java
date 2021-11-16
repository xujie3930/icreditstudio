package org.apache.dolphinscheduler.service.time;

import org.apache.dolphinscheduler.service.PartitionTypeEnum;

import java.util.Date;

/**
 * @author Peng
 */
public interface ITimeInterval {

    /**
     * 指定时间的前n个时间
     *
     * @param date
     * @param n
     * @return
     */
    Date beforeTime(Date date, int n);

    /**
     * 取得指定时间所在的起始时间
     *
     * @param date
     * @return
     */
    Date getBeginTime(Date date);

    /**
     * 取得指定时间所在的结束时间
     *
     * @param date
     * @return
     */
    Date getEndTime(Date date);

    /**
     * 简写
     *
     * @param date
     * @return
     */
    String getFormat(Date date);

    /**
     * 标识
     *
     * @return
     */
    PartitionTypeEnum getKey();
}
