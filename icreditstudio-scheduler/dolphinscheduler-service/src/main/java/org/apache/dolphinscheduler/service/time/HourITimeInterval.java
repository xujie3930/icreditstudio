package org.apache.dolphinscheduler.service.time;

import cn.hutool.core.date.DateUtil;
import org.apache.dolphinscheduler.service.PartitionTypeEnum;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Peng
 */
@Component
public class HourITimeInterval extends AbstractITimeInterval {

    @Override
    public Date beforeTime(Date date, int n) {
        Calendar calendar = DateUtil.date(date).toCalendar();
        calendar.add(Calendar.HOUR_OF_DAY, -n);
        return calendar.getTime();
    }

    @Override
    public String getFormat(Date date) {
        return DateUtil.format(date, "yyyy-MM-dd_HH");
    }

    @Override
    public PartitionTypeEnum getKey() {
        return PartitionTypeEnum.HOUR;
    }

    @Override
    public Date getBeginTime(Date date) {
        return DateUtil.beginOfHour(date);
    }

    @Override
    public Date getEndTime(Date date) {
        return DateUtil.endOfHour(date);
    }
}
