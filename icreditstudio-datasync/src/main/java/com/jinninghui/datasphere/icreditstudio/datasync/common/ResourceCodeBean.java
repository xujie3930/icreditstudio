package com.jinninghui.datasphere.icreditstudio.datasync.common;

import com.jinninghui.datasphere.icreditstudio.framework.systemcode.SystemCode;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liyanhui
 */
@SystemCode
public class ResourceCodeBean {

    public enum ResourceCode {
        RESOURCE_CODE_10000000("10000000", "非法请求参数"),
        RESOURCE_CODE_10000001("10000001", "请求参数错误"),
        RESOURCE_CODE_10000002("10000002", "接口返回异常"),

        RESOURCE_CODE_60000000("60000000", "工作空间ID为空"),
        RESOURCE_CODE_60000001("60000001", "目标库名称为空"),
        RESOURCE_CODE_60000002("60000002", "宽表名称为空"),
        RESOURCE_CODE_60000003("60000003", "数据源ID为空"),
        RESOURCE_CODE_60000004("60000004", "数据源方言为空"),
        RESOURCE_CODE_60000005("60000005", "数据源表为空"),
        RESOURCE_CODE_60000006("60000006", "未获取数据源连接"),
        RESOURCE_CODE_60000007("60000007", "任务状态只能是0、1、2、3"),
        RESOURCE_CODE_60000008("60000008", "执行状态只能是0、1、2、3"),
        RESOURCE_CODE_60000009("60000009", "任务名称为空"),
        RESOURCE_CODE_60000010("60000010", "任务启用状态为空"),
        RESOURCE_CODE_60000011("60000011", "任务创建方式为空"),
        RESOURCE_CODE_60000012("60000012", "不能大于255个字符"),
        RESOURCE_CODE_60000013("60000013", "数据源分类为空"),
        RESOURCE_CODE_60000014("60000014", "宽表字段为空"),
        RESOURCE_CODE_60000015("60000015", "启用停用状态只能是0、1"),
        RESOURCE_CODE_60000016("60000016", "任务ID为空"),
        RESOURCE_CODE_60000017("60000017", "数据源分类只能是0、1、2"),
        RESOURCE_CODE_60000018("60000018", "连接表为空"),
        RESOURCE_CODE_60000019("60000019", "宽表ID为空"),
        RESOURCE_CODE_60000020("60000020", "识别宽表失败"),
        RESOURCE_CODE_60000021("60000021", "调用步骤不能为空"),
        RESOURCE_CODE_60000022("60000022", "任务名称不大于50个字符"),
        RESOURCE_CODE_60000023("60000023", "任务描述不大于255个字符"),
        RESOURCE_CODE_60000024("60000024", "生成宽表sql为空"),
        RESOURCE_CODE_60000025("60000025", "未匹配到合适的类型处理器"),
        RESOURCE_CODE_60000026("60000026", "未找到合适的格式化器"),
        RESOURCE_CODE_60000027("60000027", "sql语法有误,请检查"),
        RESOURCE_CODE_60000029("60000029", "获取字典列表失败"),
        RESOURCE_CODE_60000030("60000030", "未找到款表信息"),
        RESOURCE_CODE_60000031("60000031", "hive配置信息获取失败"),
        RESOURCE_CODE_60000032("60000032", "未获取到宽表信息"),
        RESOURCE_CODE_60000033("60000033", "通过sql未识别数据库信息"),
        RESOURCE_CODE_60000034("60000034", "该任务非【执行中】状态，不能进行【停止执行】"),
        RESOURCE_CODE_60000035("60000035", "该任务正在【执行中】，不能删除"),
        RESOURCE_CODE_60000036("60000036", "该任务已经在【执行中】"),
        RESOURCE_CODE_60000037("60000037", "任务创建失败"),
        RESOURCE_CODE_60000038("60000038", "获取用户信息失败"),
        RESOURCE_CODE_60000039("60000039", "获取任务信息失败"),
        RESOURCE_CODE_60000040("60000040", "获取流程定义失败"),
        RESOURCE_CODE_60000041("60000041", "任务还没【启用】，不能进行【停用】"),
        RESOURCE_CODE_60000042("60000042", "任务已经【启用】，不能进行【删除】"),
        RESOURCE_CODE_60000043("60000043", "任务不是【停用】状态，不能进行【启用】"),
        RESOURCE_CODE_60000044("60000044", "任务还没【启用】，不能进行【执行|立即执行】"),
        RESOURCE_CODE_60000045("60000045", "任务还没【启用】，不能进行【停止执行】"),
        RESOURCE_CODE_60000046("60000046", "用户ID为空"),
        RESOURCE_CODE_60000047("60000047", "cron表达式不规范"),
        RESOURCE_CODE_60000048("60000048", "未找到合适的同步sql解析器"),
        RESOURCE_CODE_60000049("60000049", "cron表达式参数为空"),
        RESOURCE_CODE_60000050("60000050", "数据表不存在,请检查"),
        RESOURCE_CODE_60000051("60000051", "请检查识别条件"),
        RESOURCE_CODE_60000052("60000052", "任务执行失败"),
        RESOURCE_CODE_60000053("60000053", "该数据源为禁用状态，禁止启用"),
        RESOURCE_CODE_60000054("60000054", "调度相关参数缺失"),
        RESOURCE_CODE_60000055("60000055", "未找到任务宽表信息"),
        RESOURCE_CODE_60000056("60000056", "任务增量信息缺失"),
        RESOURCE_CODE_60000057("60000057", "周期执行任务实例为空，请检查"),
        RESOURCE_CODE_60000058("60000058", "任务名称重复"),
        RESOURCE_CODE_60000059("60000059", "可视化表信息列表为空"),
        RESOURCE_CODE_60000060("60000060", "可视化关系列表为空"),
        RESOURCE_CODE_60000061("60000061", "识别宽表方言参数为空"),
        RESOURCE_CODE_60000062("60000062", "资源类型不能为空"),
        RESOURCE_CODE_60000063("60000063", "查询语句为空"),
        RESOURCE_CODE_60000064("60000064", "未找到数据源，请检查sql语句"),
        RESOURCE_CODE_60000065("60000065", "根据sql匹配的数据源,缺少方言信息"),
        RESOURCE_CODE_60000066("60000066", "根据sql匹配的数据源,缺少数据库名信息"),
        RESOURCE_CODE_60000067("60000067", "根据sql匹配的数据源,缺少host信息"),
        RESOURCE_CODE_60000068("60000068", "根据sql匹配的数据源,缺少数据源ID信息"),
        RESOURCE_CODE_60000069("60000069", "根据sql匹配数据源时,数据源ID信息为空"),
        RESOURCE_CODE_60000070("60000070", "最大并发数为空"),
        RESOURCE_CODE_60000071("60000071", "限流类型为空"),
        RESOURCE_CODE_60000072("60000072", "调度类型为空"),
        RESOURCE_CODE_60000073("60000073", "周期执行调度时间为空"),
        RESOURCE_CODE_60000074("60000074", "限流大小不能为空"),
        RESOURCE_CODE_60000075("60000075", "taskId有误，不能找到对应的数据同步任务"),
        RESOURCE_CODE_60000076("60000076", "流程定义返回为空"),
        RESOURCE_CODE_60000077("60000077", "字典列的key不能为空"),
        RESOURCE_CODE_60000078("60000078", "字典列的key不能重复"),
        RESOURCE_CODE_60000080("60000080", "字典表的ID不能为空"),
        RESOURCE_CODE_60000081("60000081", "字典表英文名只能包含 字母和下划线，并且长度在50以内"),
        RESOURCE_CODE_60000082("60000082", "字典表中文名只能包含 中文，并且长度在50以内"),
        RESOURCE_CODE_60000083("60000083", "字典表描述的长度在250以内"),
        RESOURCE_CODE_60000084("60000084", "字典表列中的key只能包含中文、字母、数字，并且长度在40以内"),
        RESOURCE_CODE_60000085("60000085", "字典表列中的value只能包含中文、字母、数字，并且长度在40以内"),
        RESOURCE_CODE_60000086("60000086", "字典表列中的备注的长度在200以内"),
        ;

        public final String code;
        public final String message;

        ResourceCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public static ResourceCode find(String code) {
            if (StringUtils.isNotBlank(code)) {
                for (ResourceCode value : ResourceCode.values()) {
                    if (code.equals(value.getCode())) {
                        return value;
                    }
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
