package org.apache.dolphinscheduler.service.handler;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.dolphinscheduler.common.model.Configuration;
import org.apache.dolphinscheduler.service.increment.IncrementUtil;

import java.util.Objects;

/**
 * @author Peng
 */
public abstract class AbstractProcessDefinitionJsonHandler implements ProcessDefinitionJsonHandler {

    private final static String TASK_PARAM_JSON = "tasks[0].params.json";
    private static final String PATH = "content[0].writer.parameter.path";

    public AbstractProcessDefinitionJsonHandler() {
        register();
    }

    @Override
    public void register() {
        ProcessDefinitionJsonHandlerContainer.getInstance().put(getDialect(), this);
    }

    public Object getValue(String json, String path) {
        Configuration from = Configuration.from(json);
        Object js = from.get(TASK_PARAM_JSON);
        Configuration content = Configuration.from(JSONUtil.toJsonStr(js));
        return content.get(path);
    }

    public Configuration setValue(String json, String path, String newValue) {
        Configuration from = Configuration.from(json);
        Object js = from.get(TASK_PARAM_JSON);
        Configuration content = Configuration.from(JSONUtil.toJsonStr(js));
        content.set(path, newValue);

        from.set(TASK_PARAM_JSON, content.toJSON());
        return from;
    }

    public String getPartitionPath(String processDefinitionJson, String timeFormat){
        Object value = getValue(processDefinitionJson, PATH);
//        log.info("路径:" + JSONObject.toJSONString(value));
        if (Objects.nonNull(value)) {
            return IncrementUtil.getDataxHdfsPath(value.toString(), timeFormat);
        }
        return null;
    }
}
