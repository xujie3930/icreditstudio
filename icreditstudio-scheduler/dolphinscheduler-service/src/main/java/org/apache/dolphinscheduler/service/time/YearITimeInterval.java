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
public class YearITimeInterval extends AbstractITimeInterval {
    @Override
    public Date beforeTime(Date date, int n) {
        Calendar calendar = DateUtil.date(date).toCalendar();
        calendar.add(Calendar.YEAR, -n);
        return calendar.getTime();
    }

    @Override
    public String getFormat(Date date) {
        return DateUtil.format(date, "yyyy");
    }

    @Override
    public PartitionTypeEnum getKey() {
        return PartitionTypeEnum.YEAR;
    }

    @Override
    public Date getBeginTime(Date date) {
        return DateUtil.beginOfYear(date);
    }

    @Override
    public Date getEndTime(Date date) {
        return DateUtil.endOfYear(date);
    }
}
