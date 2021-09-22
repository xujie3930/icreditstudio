package com.jinninghui.datasphere.icreditstudio.datasync.service.time;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.jinninghui.datasphere.icreditstudio.datasync.enums.PartitionTypeEnum;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Peng
 */
@Component
public class MonthITimeInterval extends AbstractITimeInterval {
    @Override
    public Date beforeTime(Date date, int n) {
        Calendar calendar = DateUtil.date(date).toCalendar();
        calendar.add(Calendar.MONTH, -n);
        return calendar.getTime();
    }

    @Override
    public String getFormat(Date date) {
        return DateUtil.format(date, DatePattern.NORM_MONTH_PATTERN);
    }

    @Override
    public PartitionTypeEnum getKey() {
        return PartitionTypeEnum.MONTH;
    }

    @Override
    public Date getBeginTime(Date date) {
        return DateUtil.beginOfMonth(date);
    }

    @Override
    public Date getEndTime(Date date) {
        return DateUtil.endOfMonth(date);
    }
}
