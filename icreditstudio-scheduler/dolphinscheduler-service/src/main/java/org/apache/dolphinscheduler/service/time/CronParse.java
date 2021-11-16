package org.apache.dolphinscheduler.service.time;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.dolphinscheduler.service.PartitionTypeEnum;
import org.apache.logging.log4j.core.util.CronExpression;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Peng
 */
public class CronParse {

    private static PartitionTypeEnum match(int index) {
        PartitionTypeEnum result = null;
        if (0 == index) {
            result = PartitionTypeEnum.YEAR;
        } else if (1 == index) {
            result = PartitionTypeEnum.MONTH;
        } else if (2 == index) {
            result = PartitionTypeEnum.DAY;
        } else if (3 == index) {
            result = PartitionTypeEnum.HOUR;
        } else if (4 == index) {
            result = PartitionTypeEnum.MINUTE;
        }
        return result;
    }

    /**
     * 根据cron表达式取得分区类型，支持前5位的识别
     *
     * @param cron
     * @return
     */
    public static PartitionTypeEnum getPartition(String cron) {

        PartitionTypeEnum partitionType = null;
        if (CronExpression.isValidExpression(cron)) {
            List<String> split = StrUtil.split(cron, " ");
            List<String> collect = split.stream()
                    .filter(StringUtils::isNotBlank)
                    .limit(5)
                    .collect(Collectors.toList());
            Collections.reverse(collect);
            for (int i = 0; i < collect.size(); i++) {
                if (StringUtils.isNumeric(collect.get(i))) {
                    partitionType = match(i);
                    break;
                }
            }
        } else {
            throw new RuntimeException("60000047");
        }
        return partitionType;
    }

    public static void main(String[] args) {
        PartitionTypeEnum partition = getPartition("0 * * * * ? ");
        if (partition != null) {
            System.out.println(partition.getName());
        } else {
            throw new RuntimeException("不分区");
        }
    }
}
