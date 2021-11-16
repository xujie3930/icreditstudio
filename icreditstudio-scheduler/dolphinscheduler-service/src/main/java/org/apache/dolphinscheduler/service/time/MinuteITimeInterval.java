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
public class MinuteITimeInterval extends AbstractITimeInterval {
    @Override
    public Date beforeTime(Date date, int n) {
        Calendar calendar = DateUtil.date(date).toCalendar();
        calendar.add(Calendar.MINUTE, -n);
        return calendar.getTime();
    }

    @Override
    public Date getBeginTime(Date date) {
        return DateUtil.beginOfMinute(date);
    }

    @Override
    public Date getEndTime(Date date) {
        return DateUtil.endOfMinute(date);
    }

    @Override
    public String getFormat(Date date) {
        return DateUtil.format(date, "yyyy-MM-dd_HH_mm");
    }

    @Override
    public PartitionTypeEnum getKey() {
        return PartitionTypeEnum.MINUTE;
    }
}
