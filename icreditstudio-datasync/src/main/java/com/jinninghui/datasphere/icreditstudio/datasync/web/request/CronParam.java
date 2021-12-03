package com.jinninghui.datasphere.icreditstudio.datasync.web.request;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author Peng
 */
@Data
public class CronParam {

    private static final String SECOND = "second";
    private static final String MINUTE = "minute";
    private static final String HOUR = "hour";
    private static final String DAY = "day";
    private static final String MONTH = "month";

    private String type;

    /**
     * 年
     * 月：1，日：1，时：8，分：30，秒：20
     * 月
     * 日：1，时：12，分：30，秒：24
     * 日
     * 时：14，分：34，秒：45
     * 时
     * 分：34，秒：23
     */
    private List<Map<String, Integer>> moment;


    public String getCron() {
        if (CollectionUtils.isEmpty(moment)) {
            return "";
        }
        CronGenerate cronGenerate = new CronGenerate();
        for (Map<String, Integer> map : moment) {
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                String key = entry.getKey();
                if (SECOND.equals(key)) {
                    cronGenerate.setSecond(entry.getValue() + "");
                }
                if (MINUTE.equals(key)) {
                    cronGenerate.setMinute(entry.getValue() + "");
                }
                if (HOUR.equals(key)) {
                    cronGenerate.setHour(entry.getValue() + "");
                }
                if (DAY.equals(key)) {
                    cronGenerate.setDay(entry.getValue() + "");
                }
                if (MONTH.equals(key)) {
                    cronGenerate.setMonth(entry.getValue() + "");
                }
            }
        }
        return cronGenerate.getCron();
    }

    @Data
    public static class CronGenerate {
        private String second;
        private String minute;
        private String hour;
        private String day;
        private String month;

        private String getSecond() {
            if (StringUtils.isBlank(second)) {
                return "0";
            }
            return second;
        }

        private String getMinute() {
            if (StringUtils.isBlank(minute)) {
                return "*";
            }
            return minute;
        }

        private String getHour() {
            if (StringUtils.isBlank(hour)) {
                return "*";
            }
            return hour;
        }

        private String getDay() {
            if (StringUtils.isBlank(day)) {
                return "*";
            }
            return day;
        }

        private String getMonth() {
            if (StringUtils.isBlank(month)) {
                return "*";
            }
            return month;
        }

        public String getCron() {
            return new StringJoiner(" ").add(getSecond()).add(getMinute()).add(getHour()).add(getDay()).add(getMonth()).add("?").toString();
        }
    }
}
